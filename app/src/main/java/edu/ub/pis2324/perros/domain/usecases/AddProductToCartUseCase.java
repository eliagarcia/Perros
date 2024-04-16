package edu.ub.pis2324.perros.domain.usecases;

import edu.ub.pis2324.perros.domain.model.valueobjects.ClientId;
import edu.ub.pis2324.perros.domain.model.valueobjects.AnimalId;
import edu.ub.pis2324.perros.utils.error_handling.XopingError;
import io.reactivex.rxjava3.core.Observable;

public interface AddProductToCartUseCase {
  Observable<Boolean> execute(ClientId clientId, AnimalId productId, Integer quantity);

  enum Error implements XopingError {
    CLIENTS_DATA_ACCESS_ERROR;
  }
}
