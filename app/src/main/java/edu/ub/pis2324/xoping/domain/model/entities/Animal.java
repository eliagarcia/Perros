package edu.ub.pis2324.xoping.domain.model.entities;

import edu.ub.pis2324.xoping.domain.model.valueobjects.AnimalId;


/**
 * DTO for an animal.
 */
public class Animal {
  /* Attributes */
  private AnimalId id;
  private String nom;
  private String edat;
  private String descripcio;
  private String pelatge;
  private String imageUrl;
  private float mida;
  private float pes;
  private String raza;
  private boolean gos;

  public Animal(
      AnimalId id,
      String nom,
      String edat,
      String descripcio,
      String imageUrl,
      String pelatge,
      float mida,
      float pes,
      String raza,
      boolean gos
  ) {
    this.id = id;
    this.nom = nom;
    this.edat = edat;
    this.descripcio = descripcio;
    this.imageUrl = imageUrl;
    this.pelatge = pelatge;
    this.mida = mida;
    this.pes = pes;
    this.raza = raza;
    this.gos = gos;
  }

  /**
   * Empty constructor required for Firestore.
   */
  @SuppressWarnings("unused")
  public Animal() { }

  /* Getters */
  public AnimalId getId() { return id; }
  public String getNom() { return nom; }
  public String getEdat() { return edat; }
  public String getDescripcio() { return descripcio; }
  public String getImageUrl() { return imageUrl; }
  public String getPelatge() { return pelatge; }
  public float getMida() { return mida; }
  public float getPes() { return pes; }
  public String getRaza() { return raza; }
  public boolean isGos() { return gos; }


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
