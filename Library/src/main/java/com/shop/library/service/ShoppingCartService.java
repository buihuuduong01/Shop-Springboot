package com.shop.library.service;

import com.shop.library.dto.ProductDto;
import com.shop.library.dto.ShoppingCartDto;
import com.shop.library.model.Customer;
import com.shop.library.model.Product;
import com.shop.library.model.ShoppingCart;

public interface ShoppingCartService {
//    ShoppingCart addItemToCart(Product product, int quantity, Customer customer);
//
//    ShoppingCart updateCart(Product product, int quantity, Customer customer);
//
//
//    ShoppingCart deleteItemFromCart(Product product, Customer customer);

    ShoppingCart addItemToCart(ProductDto productDto, int quantity, String username);

    ShoppingCart updateCart(ProductDto productDto, int quantity, String username);

    ShoppingCart removeItemFromCart(ProductDto productDto, String username);

    ShoppingCartDto addItemToCartSession(ShoppingCartDto cartDto, ProductDto productDto, int quantity);

    ShoppingCartDto updateCartSession(ShoppingCartDto cartDto, ProductDto productDto, int quantity);

    ShoppingCartDto removeItemFromCartSession(ShoppingCartDto cartDto, ProductDto productDto, int quantity);

    ShoppingCart combineCart(ShoppingCartDto cartDto, ShoppingCart cart);


    void deleteCartById(Long id);

    ShoppingCart getCart(String username);

}
