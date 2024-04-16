package edu.ub.pis2324.perros.domain.usecases;

import java.util.List;

import edu.ub.pis2324.perros.domain.model.entities.Animal;
import edu.ub.pis2324.perros.utils.error_handling.XopingError;
import io.reactivex.rxjava3.core.Observable;

public interface FetchProductsCatalogUseCase {
  Observable<List<Animal>> execute();

  enum Error implements XopingError {
    PRODUCTS_DATA_ACCESS_ERROR;
  }
}
