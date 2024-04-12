package edu.ub.pis2324.xoping.data.repositories.firestore;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.ub.pis2324.xoping.domain.di.repositories.ProductRepository;
import edu.ub.pis2324.xoping.domain.model.entities.Product;
import edu.ub.pis2324.xoping.data.dtos.firestore.ProductFirestoreDto;
import edu.ub.pis2324.xoping.data.dtos.firestore.mappers.DTOToDomainMapper;
import edu.ub.pis2324.xoping.domain.model.valueobjects.ProductId;
import edu.ub.pis2324.xoping.utils.error_handling.XopingThrowable;
import io.reactivex.rxjava3.core.Observable;

/**
 * Cloud Firestore implementation of the data store.
 */
public class ProductFirestoreRepository implements ProductRepository {
  /* Constants */
  private static final String PRODUCTS_COLLECTION_NAME = "products";
  /* Attributes */
  private final FirebaseFirestore db;
  private final DTOToDomainMapper DTOToDomainMapper;

  /**
   * Empty constructor
   */
  public ProductFirestoreRepository() {
    db = FirebaseFirestore.getInstance();
    DTOToDomainMapper = new DTOToDomainMapper();
  }

  @Override
  public Observable<Product> getById(ProductId id) {
    return Observable.create(emitter -> {
      db.collection(PRODUCTS_COLLECTION_NAME)
        .document(id.toString())
        .get()
        .addOnFailureListener(exception -> {
          emitter.onError(new XopingThrowable(Error.GETBYID_UNKNOWN_ERROR));
        })
        .addOnSuccessListener(ds -> {
          if (ds.exists()) {
            ProductFirestoreDto productFirestoreDto = ds.toObject(ProductFirestoreDto.class);
            Product product = DTOToDomainMapper.map(productFirestoreDto, Product.class);
            emitter.onNext(product);
            emitter.onComplete();
          } else {
            emitter.onError(new XopingThrowable(Error.PRODUCT_NOT_FOUND));
          }
        });
    });
  }

  public Observable<List<Product>> getByIds(List<ProductId> ids) {
    /* UUIDs not supported by Firestore, so we need to convert them to strings */
    List<String> idsStr = ids
        .stream()
        .map(ProductId::toString)
        .collect(Collectors.toList());

    return Observable.create(emitter -> {
      db.collection(PRODUCTS_COLLECTION_NAME)
          .whereIn(FieldPath.documentId(), idsStr) // idStr: converted UUIDs to Strings
          .get()
          .addOnFailureListener(exception -> {
            emitter.onError(new XopingThrowable(Error.GETBYIDS_UNKNOWN_ERROR));
          })
          .addOnSuccessListener(queryDocumentSnapshots -> {
            List<Product> products = new ArrayList<>();
            for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()) {
              if (ds.exists()) {
                ProductFirestoreDto productFirestoreDto = ds.toObject(ProductFirestoreDto.class);
                Product product = DTOToDomainMapper.map(productFirestoreDto, Product.class);
                products.add(product);
              }
            }
            emitter.onNext(products);
            emitter.onComplete();
          });
    });
  }

  /**
   * Get all products from the Firebase CloudFirestore.
   */
  public Observable<List<Product>> getAll() {
    return Observable.create(emitter -> {
      db.collection(PRODUCTS_COLLECTION_NAME)
          .get()
          .addOnFailureListener(exception -> {
            emitter.onError(new XopingThrowable(Error.GETALL_UNKNOWN_ERROR));
          })
          .addOnSuccessListener(queryDocumentSnapshots -> {
            List<Product> products = new ArrayList<>();
            for (DocumentSnapshot ds : queryDocumentSnapshots) {
              ProductFirestoreDto productFirestoreDto = ds.toObject(ProductFirestoreDto.class);
              Product product = DTOToDomainMapper.map(productFirestoreDto, Product.class);
              products.add(product);
            }
            emitter.onNext(products);
            emitter.onComplete();
          });
    });
  }

  /**
   * Get a list of products from the Firebase CloudFirestore starting with productName.
   * @param productName The case insensitive product name.
   */
  public Observable<List<Product>> getByName(String productName) {
    return Observable.create(emitter -> {
      db.collection(PRODUCTS_COLLECTION_NAME)
          .orderBy("nameLowerCase")
          .startAt(productName.toLowerCase())
          .endAt(productName.toLowerCase() + "\uf8ff")
          .get()
          .addOnFailureListener(exception -> {
            emitter.onError(new XopingThrowable(Error.GETBYNAME_UNKNOWN_ERROR));
          })
          .addOnSuccessListener(queryDocumentSnapshots -> {
            List<Product> products = new ArrayList<>();
            for (DocumentSnapshot ds : queryDocumentSnapshots) {
              ProductFirestoreDto productFirestoreDto = ds.toObject(ProductFirestoreDto.class);
              Product product = DTOToDomainMapper.map(productFirestoreDto, Product.class);
              products.add(product);
            }
            emitter.onNext(products);
            emitter.onComplete();
          });
    });
  }
}
