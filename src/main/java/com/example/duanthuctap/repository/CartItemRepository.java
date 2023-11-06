package com.example.duanthuctap.repository;

import com.example.duanthuctap.entity.CartItem;
import com.example.duanthuctap.entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    CartItem findByProduct_ProductId(Integer id);

    CartItem findByCartsAndProduct_ProductId(Carts carts, Integer id);

}
