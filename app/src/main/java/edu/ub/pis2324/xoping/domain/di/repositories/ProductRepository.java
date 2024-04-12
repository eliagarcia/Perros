package edu.ub.pis2324.xoping.domain.di.repositories;

import java.util.List;

import edu.ub.pis2324.xoping.domain.model.entities.Product;
import edu.ub.pis2324.xoping.domain.model.valueobjects.ProductId;
import edu.ub.pis2324.xoping.utils.error_handling.XopingError;
import io.reactivex.rxjava3.core.Observable;

public interface ProductRepository {
  Observable<Product> getById(ProductId id);
  Observable<List<Product>> getByIds(List<ProductId> ids);
  Observable<List<Product>> getAll();
  Observable<List<Product>> getByName(String productName);

  enum Error implements XopingError {
    GETBYID_UNKNOWN_ERROR,
    PRODUCT_NOT_FOUND,
    GETBYIDS_UNKNOWN_ERROR,
    GETALL_UNKNOWN_ERROR,
    GETBYNAME_UNKNOWN_ERROR;
  }
}
