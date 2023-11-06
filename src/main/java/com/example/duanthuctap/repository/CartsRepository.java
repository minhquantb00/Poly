package com.example.duanthuctap.repository;

import com.example.duanthuctap.entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartsRepository extends JpaRepository<Carts, Integer> {

    @Query("SELECT pi.imageProduct, p.nameProduct, cil.quantity, p.price " +
            "FROM Carts c " +
            "JOIN c.cartItemList cil " +
            "JOIN cil.product p " +
            "JOIN p.productImageList pi WHERE c.cartId = :id")
    List<Object[]> showCart(@Param("id") Integer id);

}
