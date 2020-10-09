package com.paulok777.model.dao;

import com.paulok777.model.util.Page;
import com.paulok777.model.util.Pageable;
import com.paulok777.model.entity.Product;

import java.util.Optional;

public interface ProductDao extends GenericDao<Product> {
    void updateAmountById(Long amount, Long id);

    Optional<Product> findByIdentifier(String identifier);

    Page<Product> findByOrderByName(Pageable pageable);
}
