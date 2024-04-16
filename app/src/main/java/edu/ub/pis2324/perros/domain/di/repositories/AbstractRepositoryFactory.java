package edu.ub.pis2324.perros.domain.di.repositories;

public interface AbstractRepositoryFactory {
  ClientRepository createClientRepository();
  AnimalRepository createProductRepository();
}
