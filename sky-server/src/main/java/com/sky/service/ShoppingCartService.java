package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    void add(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> list();

    void cleanShoppingCart();

    List<ShoppingCart> showShoppingCart();

    void addShoppingCart(ShoppingCart shoppingCart);

    void subShoppingCart(ShoppingCart shoppingCart);
}
