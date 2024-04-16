package edu.ub.pis2324.perros.data.repositories.firestore;

import edu.ub.pis2324.perros.domain.di.repositories.ClientRepository;
import edu.ub.pis2324.perros.domain.di.repositories.AnimalRepository;
import edu.ub.pis2324.perros.domain.di.repositories.AbstractRepositoryFactory;

public class FirestoreRepositoryFactory implements AbstractRepositoryFactory {
  @Override
  public ClientRepository createClientRepository() {
    return new ClientFirestoreRepository();
  }

  @Override
  public AnimalRepository createProductRepository() {
    return new AnimalFirestoreRepository();
  }
}
