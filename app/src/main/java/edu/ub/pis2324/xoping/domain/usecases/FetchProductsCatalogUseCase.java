package edu.ub.pis2324.xoping.domain.usecases;

import java.util.List;

import edu.ub.pis2324.xoping.domain.model.entities.Product;
import edu.ub.pis2324.xoping.utils.error_handling.XopingError;
import io.reactivex.rxjava3.core.Observable;

public interface FetchProductsCatalogUseCase {
  Observable<List<Product>> execute();

  enum Error implements XopingError {
    PRODUCTS_DATA_ACCESS_ERROR;
  }
}
