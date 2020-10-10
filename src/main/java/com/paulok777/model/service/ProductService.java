package com.paulok777.model.service;

import com.paulok777.model.dao.DaoFactory;
import com.paulok777.model.dao.ProductDao;
import com.paulok777.model.dto.ProductDTO;
import com.paulok777.model.util.Page;
import com.paulok777.model.util.Pageable;
import com.paulok777.model.entity.Product;
import com.paulok777.model.exception.cash_register_exc.order_exc.NoSuchProductException;
import com.paulok777.model.exception.cash_register_exc.product_exc.DuplicateCodeOrNameException;
import com.paulok777.model.util.ExceptionKeys;

import java.util.Optional;

public class ProductService {
    private final DaoFactory daoFactory;
    private final UserService userService;

    public ProductService(final DaoFactory daoFactory, final UserService userService) {
        this.daoFactory = daoFactory;
        this.userService = userService;
    }

    public Page<Product> getProducts(Pageable pageable) {
        try (ProductDao productDao = daoFactory.createProductDao()) {
            return productDao.findByOrderByName(pageable);
        }
    }

    public void saveNewProduct(ProductDTO productDTO) {
        Product product = new Product(productDTO);
        saveProduct(product);
    }

    public void setAmountById(Long amount, Long id) {
        try (ProductDao productDao = daoFactory.createProductDao()) {
            productDao.updateAmountById(amount, id);
        }
//        log.debug("(username: {}) Set new amount to product was done successfully.",
//                userService.getCurrentUser().getUsername());
    }

    public Product findByIdentifier(String identifier) {
        try (ProductDao productDao = daoFactory.createProductDao()) {
            return productDao.findByIdentifier(identifier).orElseThrow(
                    () -> {
//                            log.warn("(username: {}) {}.",
//                                    userService.getCurrentUser().getUsername(), ExceptionKeys.NO_SUCH_PRODUCTS);
                        throw new NoSuchProductException(ExceptionKeys.NO_SUCH_PRODUCTS);
                    }
            );
        }
    }

    public void saveProduct(Product product) {
        try (ProductDao productDao = daoFactory.createProductDao()){
            productDao.create(product);
//            log.debug("(username: {}) Product saved successfully.",
//                    userService.getCurrentUser().getUsername());
        } catch (Exception e) {
//            log.warn("(username: {}) {}.",
//                    userService.getCurrentUser().getUsername(), ExceptionKeys.DUPLICATE_CODE_OR_NAME);
            throw new DuplicateCodeOrNameException(ExceptionKeys.DUPLICATE_CODE_OR_NAME);
        }
    }

    public Optional<Product> findById(Long id) {
        try (ProductDao productDao = daoFactory.createProductDao()) {
            return productDao.findById(id);
        }
    }
}
