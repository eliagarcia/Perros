package edu.ub.pis2324.xoping.domain.model.valueobjects;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import edu.ub.pis2324.xoping.domain.model.entities.Product;

public class Cart {
  private Map<ProductId, LineItem<ProductId>> cartLines; // <Product id, quantity>

  public Cart() {
    this.cartLines = new HashMap<>();
  }

  public Map<ProductId, LineItem<ProductId>> getCartLines() {
    return new HashMap<>(cartLines);
  }

  public void add(ProductId productId, Integer quantity) {
    if (cartLines.containsKey(productId)) {
      cartLines.put(productId, cartLines.get(productId).addQuantity(quantity));
    } else {
      cartLines.put(productId, new LineItem<>(productId, quantity));
    }
  }

  public void remove(ProductId productId) {
    if (cartLines.containsKey(productId)) {
      cartLines.remove(productId);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Cart cart = (Cart) obj;
    return cartLines.equals(cart.cartLines);
  }
}
