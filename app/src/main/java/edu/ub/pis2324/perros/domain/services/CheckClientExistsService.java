package edu.ub.pis2324.perros.domain.services;

import edu.ub.pis2324.perros.domain.model.valueobjects.ClientId;
import edu.ub.pis2324.perros.utils.error_handling.XopingError;
import io.reactivex.rxjava3.core.Observable;

public interface CheckClientExistsService {
  Observable<Boolean> execute(ClientId clientId);

  enum Error implements XopingError {
    CLIENT_NOT_FOUND,
    CLIENTS_DATA_ACCESS_ERROR;
  }
}
