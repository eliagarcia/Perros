package edu.ub.pis2324.perros.domain.usecases;

import edu.ub.pis2324.perros.domain.model.entities.Client;
import edu.ub.pis2324.perros.domain.model.valueobjects.ClientId;
import edu.ub.pis2324.perros.utils.error_handling.XopingError;
import io.reactivex.rxjava3.core.Observable;

public interface LogInUseCase {
  Observable<Client> execute(ClientId username, String enteredPassword);

  enum Error implements XopingError {
    USERNAME_EMPTY,
    PASSWORD_EMPTY,
    CLIENT_NOT_FOUND,
    PASSWORD_INCORRECT,
    CLIENTS_DATA_ACCESS_ERROR;
  }
}
