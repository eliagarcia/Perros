package edu.ub.pis2324.xoping.domain.usecases;

import edu.ub.pis2324.xoping.domain.model.valueobjects.ClientId;
import edu.ub.pis2324.xoping.domain.model.valueobjects.ProductId;
import edu.ub.pis2324.xoping.utils.error_handling.XopingError;
import io.reactivex.rxjava3.core.Observable;

public interface AddProductToCartUseCase {
  Observable<Boolean> execute(ClientId clientId, ProductId productId, Integer quantity);

  enum Error implements XopingError {
    CLIENTS_DATA_ACCESS_ERROR;
  }
}
