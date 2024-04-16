package edu.ub.pis2324.perros.data.dtos.firestore;

import com.google.firebase.firestore.DocumentId;

/**
 * DTO for a product.
 */
public class AnimalFirestoreDto {
  /* Attributes */
  @DocumentId
  private String nom;
  private String raza;
  private int edat;
  private float pes;
  private float mida;
  private String imageUrl;

  /**
   * Empty constructor required for Firestore.
   */
  @SuppressWarnings("unused")
  public AnimalFirestoreDto() { }


  /* Getters */

  public String getNom() { return nom; }
  public String getRaza() { return raza; }
  public int getEdat() { return edat; }
  public float getPes() { return pes; }
  public float getMida() { return mida; }
  public String getImageUrl() { return imageUrl; }


}
