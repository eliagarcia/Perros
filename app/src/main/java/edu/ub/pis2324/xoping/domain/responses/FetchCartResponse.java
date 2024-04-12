package edu.ub.pis2324.xoping.domain.responses;

import java.util.List;

import edu.ub.pis2324.xoping.domain.model.entities.Product;
import edu.ub.pis2324.xoping.domain.model.valueobjects.Price;
import edu.ub.pis2324.xoping.domain.model.valueobjects.PricedLineItem;

public class FetchCartResponse {
  private final List<PricedLineItem<Product>> pricedLineItems;
  private final Price priceTotal;

  public FetchCartResponse(
      List<PricedLineItem<Product>> pricedLineItems,
      Price priceTotal
  ) {
    this.pricedLineItems = pricedLineItems;
    this.priceTotal = priceTotal;
  }

  public List<PricedLineItem<Product>> getPricedLineItems() {
    return pricedLineItems;
  }

  public Price getPriceTotal() {
    return priceTotal;
  }
}
