package com.example.jwt.jpa;

import com.example.jwt.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    String FILTER_PRODUCTS_ON_NAME_QUERY = "select b from Product b where UPPER(b.name) like CONCAT('%',UPPER(?1),'%')";

    @Query(FILTER_PRODUCTS_ON_NAME_QUERY)
    Page<Product> findByNameLike(String name, Pageable pageable);
}
