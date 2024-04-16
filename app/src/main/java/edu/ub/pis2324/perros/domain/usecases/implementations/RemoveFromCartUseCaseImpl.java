package edu.ub.pis2324.perros.domain.usecases.implementations;

import edu.ub.pis2324.perros.domain.di.repositories.ClientRepository;
import edu.ub.pis2324.perros.domain.model.valueobjects.ClientId;
import edu.ub.pis2324.perros.domain.model.valueobjects.AnimalId;
import edu.ub.pis2324.perros.domain.responses.FetchCartResponse;
import edu.ub.pis2324.perros.domain.usecases.FetchCartUseCase;
import edu.ub.pis2324.perros.domain.usecases.RemoveFromCartUseCase;
import edu.ub.pis2324.perros.utils.error_handling.XopingThrowableMapper;
import io.reactivex.rxjava3.core.Observable;

public class RemoveFromCartUseCaseImpl implements RemoveFromCartUseCase {

  private FetchCartUseCase fetchCartUseCase;
  private ClientRepository clientRepository;
  private final XopingThrowableMapper throwableMapper;

  public RemoveFromCartUseCaseImpl(
      FetchCartUseCase fetchCartUseCase,
      ClientRepository clientRepository
  ) {
    this.fetchCartUseCase = fetchCartUseCase;
    this.clientRepository = clientRepository;

    throwableMapper = new XopingThrowableMapper();
    throwableMapper.add(ClientRepository.Error.CLIENT_NOT_FOUND, Error.CLIENT_NOT_FOUND);
    throwableMapper.add(ClientRepository.Error.UPDATE_UNKNOWN_ERROR, Error.CLIENTS_DATA_ACCESS_ERROR);
    throwableMapper.add(FetchCartUseCase.Error.CLIENT_NOT_FOUND, Error.CLIENT_NOT_FOUND);
    throwableMapper.add(FetchCartUseCase.Error.CLIENTS_DATA_ACCESS_ERROR, Error.CLIENTS_DATA_ACCESS_ERROR);
    throwableMapper.add(FetchCartUseCase.Error.PRODUCTS_DATA_ACCESS_ERROR, Error.PRODUCTS_DATA_ACCESS_ERROR);
  }

  @Override
  public Observable<FetchCartResponse> execute(ClientId clientId, AnimalId productId) {
    return clientRepository.update(clientId, client -> {
      client.removeProductFromCart(productId);
    })
    .concatMap(ignored -> fetchCartUseCase.execute(clientId))
    .onErrorResumeNext(throwable -> Observable.error(throwableMapper.map(throwable)));
  }
}
