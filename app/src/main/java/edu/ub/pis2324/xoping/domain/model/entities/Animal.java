package edu.ub.pis2324.xoping.domain.model.entities;

import edu.ub.pis2324.xoping.domain.model.valueobjects.Price;
import edu.ub.pis2324.xoping.domain.model.valueobjects.AnimalId;

/**
 * DTO for a product.
 */
public class Animal {
  /* Attributes */
  private AnimalId id;
  private String name;
  private String nameLowerCase;
  private String description;
  private String imageUrl;

  public Animal(
      AnimalId id,
      String name,
      String nameLowerCase,
      String description,
      String imageUrl
  ) {
    this.id = id;
    this.name = name;
    this.nameLowerCase = nameLowerCase;
    this.description = description;
    this.imageUrl = imageUrl;
  }

  /**
   * Empty constructor required for Firestore.
   */
  @SuppressWarnings("unused")
  public Animal() { }

  /* Getters */
  public AnimalId getId() { return id; }
  public String getName() { return name; }
  public String getNameLowerCase() { return nameLowerCase; }
  public String getDescription() { return description; }
  public String getImageUrl() { return imageUrl; }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Animal product = (Animal) obj;
    return id.equals(product.id);
  }
}
