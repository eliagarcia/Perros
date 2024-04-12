package edu.ub.pis2324.xoping.domain.usecases.implementations;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.ub.pis2324.xoping.domain.di.repositories.AnimalRepository;
import edu.ub.pis2324.xoping.domain.model.valueobjects.ClientId;
import edu.ub.pis2324.xoping.domain.model.valueobjects.LineItem;
import edu.ub.pis2324.xoping.domain.model.valueobjects.PricedLineItem;
import edu.ub.pis2324.xoping.domain.model.valueobjects.AnimalId;
import edu.ub.pis2324.xoping.domain.responses.FetchCartResponse;
import edu.ub.pis2324.xoping.domain.usecases.FetchCartUseCase;
import edu.ub.pis2324.xoping.domain.usecases.FetchClientUseCase;
import edu.ub.pis2324.xoping.domain.model.valueobjects.Price;
import edu.ub.pis2324.xoping.domain.model.entities.Animal;
import edu.ub.pis2324.xoping.utils.error_handling.XopingThrowableMapper;
import io.reactivex.rxjava3.core.Observable;

public class FetchCartUseCaseImpl implements FetchCartUseCase {
  /* Attributes */
  private FetchClientUseCase fetchClientUseCase;
  private AnimalRepository productRepository;
  private final XopingThrowableMapper throwableMapper;

  /**
   * Constructor
   */
  public FetchCartUseCaseImpl(
      FetchClientUseCase fetchClientUseCase,
      AnimalRepository productRepository
  ) {
    this.fetchClientUseCase = fetchClientUseCase;
    this.productRepository = productRepository;

    throwableMapper = new XopingThrowableMapper();
    throwableMapper.add(FetchClientUseCase.Error.CLIENT_NOT_FOUND, Error.CLIENT_NOT_FOUND);
    throwableMapper.add(FetchClientUseCase.Error.CLIENTS_DATA_ACCESS_ERROR, Error.CLIENTS_DATA_ACCESS_ERROR);
    throwableMapper.add(AnimalRepository.Error.GETBYIDS_UNKNOWN_ERROR, Error.PRODUCTS_DATA_ACCESS_ERROR);
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
        Map<AnimalId, LineItem<AnimalId>> cartLines = client.getCartLines();
        List<AnimalId> productIds = new ArrayList<>(cartLines.keySet());
        return productRepository.getByIds(productIds)
          .onErrorResumeNext(throwable -> Observable.just(new ArrayList<>()))
          .concatMap(products -> buildFetchCartResponse(cartLines, products));
      })
      .onErrorResumeNext(throwable -> Observable.error(throwableMapper.map(throwable)));
  }

  @NonNull
  private Observable<FetchCartResponse> buildFetchCartResponse(
      Map<AnimalId, LineItem<AnimalId>> lineItems,
      List<Animal> products
  ) {
    List<PricedLineItem<Animal>> pricedLineItems = new ArrayList<>();
    Price totalPrice = new Price(0.0);

    for (Animal product : products) {
      LineItem<AnimalId> lineItem = lineItems.get(product.getId());
      Integer quantity = lineItem.getQuantity();
      Price subtotalPrice = product.getPrice().multiply(quantity);
      pricedLineItems.add(new PricedLineItem<>(product, quantity, subtotalPrice));
      totalPrice = totalPrice.add(subtotalPrice);
    }

    return Observable.just(new FetchCartResponse(pricedLineItems, totalPrice));
  }
}
