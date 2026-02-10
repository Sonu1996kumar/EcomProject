package com.app.ecom.repository;

import com.app.ecom.bo.CartItem;
import com.app.ecom.bo.Product;
import com.app.ecom.bo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

   List<CartItem> findByUser(User user);

    void deleteByUser(User user);
}