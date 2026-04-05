package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        Long UserId =BaseContext.getCurrentId();
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        if (list != null && list.size() > 0) {
            shoppingCart.setNumber(list.get(0).getNumber() + 1);
            shoppingCart.setId(list.get(0).getId());
            //shoppingCartMapper.update(shoppingCart);
        } else {
            shoppingCart.setUserId(UserId);
            shoppingCart.setNumber(1);
           // shoppingCartMapper.add(shoppingCart);
        }
    }

    @Override
    public List<ShoppingCart> list() {
        return null;
    }

    @Override
    public void cleanShoppingCart() {
        Long UserId =BaseContext.getCurrentId();
        shoppingCartMapper.deleteByUserId(UserId);
    }

    @Override
    public List<ShoppingCart> showShoppingCart() {
        return null;
    }

    @Override
    public void addShoppingCart(ShoppingCart shoppingCart) {

    }

    @Override
    public void subShoppingCart(ShoppingCart shoppingCart) {

    }
}
