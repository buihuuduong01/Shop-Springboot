package com.shop.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart_id", referencedColumnName = "shopping_cart_id")
    private com.shop.library.model.ShoppingCart cart;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private com.shop.library.model.Product product;
    private int quantity;
    private double unitPrice;

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", cart=" + cart.getId() +
                ", product=" + product.getName() +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" +
                '}';
    }
}
