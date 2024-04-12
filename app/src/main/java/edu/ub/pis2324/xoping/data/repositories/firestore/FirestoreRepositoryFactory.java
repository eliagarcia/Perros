package edu.ub.pis2324.xoping.data.repositories.firestore;

import edu.ub.pis2324.xoping.domain.di.repositories.ClientRepository;
import edu.ub.pis2324.xoping.domain.di.repositories.ProductRepository;
import edu.ub.pis2324.xoping.domain.di.repositories.AbstractRepositoryFactory;

public class FirestoreRepositoryFactory implements AbstractRepositoryFactory {
  @Override
  public ClientRepository createClientRepository() {
    return new ClientFirestoreRepository();
  }

  @Override
  public ProductRepository createProductRepository() {
    return new ProductFirestoreRepository();
  }
}
