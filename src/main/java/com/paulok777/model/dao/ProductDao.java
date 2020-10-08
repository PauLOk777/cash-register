package com.paulok777.model.dao;

import com.paulok777.model.util.Page;
import com.paulok777.model.util.Pageable;
import com.paulok777.model.entity.Product;

import java.util.Optional;

public interface ProductDao extends GenericDao<Product> {
    void updateAmountById(Long amount, Long id);

    Optional<Product> findByCode(String code);

    Optional<Product> findByName(String name);

    Page<Product> findByOrderByName(Pageable pageable);
}
