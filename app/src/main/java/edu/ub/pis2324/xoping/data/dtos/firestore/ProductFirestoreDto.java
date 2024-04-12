package edu.ub.pis2324.xoping.data.dtos.firestore;

import com.google.firebase.firestore.DocumentId;

/**
 * DTO for a product.
 */
public class ProductFirestoreDto {
  /* Attributes */
  @DocumentId
  private String id;
  private String name;
  private String nameLowerCase;
  private String description;
  private PriceFirestoreDto price;
  private String imageUrl;
  private Boolean fragile;

  /**
   * Empty constructor required for Firestore.
   */
  @SuppressWarnings("unused")
  public ProductFirestoreDto() { }

  /* Getters */
  public String getId() { return id; }
  public String getName() { return name; }
  public String getNameLowerCase() { return nameLowerCase; }
  public String getDescription() { return description; }
  public PriceFirestoreDto getPrice() { return price; }
  public String getImageUrl() { return imageUrl; }
  public Boolean getFragile() { return fragile; }
}
