package edu.ub.pis2324.xoping.domain.usecases.implementations;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.ub.pis2324.xoping.domain.di.repositories.ProductRepository;
import edu.ub.pis2324.xoping.domain.model.valueobjects.ClientId;
import edu.ub.pis2324.xoping.domain.model.valueobjects.LineItem;
import edu.ub.pis2324.xoping.domain.model.valueobjects.PricedLineItem;
import edu.ub.pis2324.xoping.domain.model.valueobjects.ProductId;
import edu.ub.pis2324.xoping.domain.responses.FetchCartResponse;
import edu.ub.pis2324.xoping.domain.usecases.FetchCartUseCase;
import edu.ub.pis2324.xoping.domain.usecases.FetchClientUseCase;
import edu.ub.pis2324.xoping.domain.model.valueobjects.Price;
import edu.ub.pis2324.xoping.domain.model.entities.Product;
import edu.ub.pis2324.xoping.utils.error_handling.XopingThrowableMapper;
import io.reactivex.rxjava3.core.Observable;

public class FetchCartUseCaseImpl implements FetchCartUseCase {
  /* Attributes */
  private FetchClientUseCase fetchClientUseCase;
  private ProductRepository productRepository;
  private final XopingThrowableMapper throwableMapper;

  /**
   * Constructor
   */
  public FetchCartUseCaseImpl(
      FetchClientUseCase fetchClientUseCase,
      ProductRepository productRepository
  ) {
    this.fetchClientUseCase = fetchClientUseCase;
    this.productRepository = productRepository;

    throwableMapper = new XopingThrowableMapper();
    throwableMapper.add(FetchClientUseCase.Error.CLIENT_NOT_FOUND, Error.CLIENT_NOT_FOUND);
    throwableMapper.add(FetchClientUseCase.Error.CLIENTS_DATA_ACCESS_ERROR, Error.CLIENTS_DATA_ACCESS_ERROR);
    throwableMapper.add(ProductRepository.Error.GETBYIDS_UNKNOWN_ERROR, Error.PRODUCTS_DATA_ACCESS_ERROR);
  }

  /**
   * Executes the fetch client use case.
   * @param clientId the client id
   * @return an observable with the fetch cart response
   */
  @Override
  public Observable<FetchCartResponse> execute(ClientId clientId) {
    return fetchClientUseCase.execute(clientId)
      .concatMap(client -> {
        Map<ProductId, LineItem<ProductId>> cartLines = client.getCartLines();
        List<ProductId> productIds = new ArrayList<>(cartLines.keySet());
        return productRepository.getByIds(productIds)
          .onErrorResumeNext(throwable -> Observable.just(new ArrayList<>()))
          .concatMap(products -> buildFetchCartResponse(cartLines, products));
      })
      .onErrorResumeNext(throwable -> Observable.error(throwableMapper.map(throwable)));
  }

  @NonNull
  private Observable<FetchCartResponse> buildFetchCartResponse(
      Map<ProductId, LineItem<ProductId>> lineItems,
      List<Product> products
  ) {
    List<PricedLineItem<Product>> pricedLineItems = new ArrayList<>();
    Price totalPrice = new Price(0.0);

    for (Product product : products) {
      LineItem<ProductId> lineItem = lineItems.get(product.getId());
      Integer quantity = lineItem.getQuantity();
      Price subtotalPrice = product.getPrice().multiply(quantity);
      pricedLineItems.add(new PricedLineItem<>(product, quantity, subtotalPrice));
      totalPrice = totalPrice.add(subtotalPrice);
    }

    return Observable.just(new FetchCartResponse(pricedLineItems, totalPrice));
  }
}
