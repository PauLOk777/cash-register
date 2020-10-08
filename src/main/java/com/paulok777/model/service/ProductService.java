package com.paulok777.model.service;

import com.paulok777.model.dto.ProductDTO;
import com.paulok777.model.util.Page;
import com.paulok777.model.util.Pageable;
import com.paulok777.model.entity.Product;
import com.paulok777.model.exception.cash_register_exc.order_exc.NoSuchProductException;
import com.paulok777.model.exception.cash_register_exc.product_exc.DuplicateCodeOrNameException;
//import com.paulok777.repository.ProductRepository;
import com.paulok777.controller.util.ExceptionKeys;

import java.util.Optional;

public class ProductService {
//    private final ProductRepository productRepository;
    private final UserService userService;

    public ProductService(final UserService userService) {
        this.userService = userService;
    }

    public Page<Product> getProducts(Pageable pageable) {
//        return productRepository.findByOrderByName(pageable);
        return null;
    }

    public void saveNewProduct(ProductDTO productDTO) {
        Product product = new Product(productDTO);
        saveProduct(product);
    }

    public void setAmountById(Long amount, Long id) {
//        productRepository.updateAmountById(amount, id);
//        log.debug("(username: {}) Set new amount to product was done successfully.",
//                userService.getCurrentUser().getUsername());
    }

    public Optional<Product> findByCode(String code) {
//        return productRepository.findByCode(code);
        return null;
    }

    public Optional<Product> findByName(String name) {
//        return productRepository.findByName(name);
        return null;
    }

    public Product findByIdentifier(String productIdentifier) {
        Optional<Product> product = findByCode(productIdentifier);
        return product.orElseGet(
                () -> findByName(productIdentifier).orElseThrow(
                        () -> {
//                            log.warn("(username: {}) {}.",
//                                    userService.getCurrentUser().getUsername(), ExceptionKeys.NO_SUCH_PRODUCTS);
                            throw new NoSuchProductException(ExceptionKeys.NO_SUCH_PRODUCTS);
                        }));
    }

    public void saveProduct(Product product) {
        try {
//            product = productRepository.save(product);
//            log.debug("(username: {}) Product saved successfully. Product id: {}",
//                    userService.getCurrentUser().getUsername(), product.getId());
        } catch (Exception e) {
//            log.warn("(username: {}) {}.",
//                    userService.getCurrentUser().getUsername(), ExceptionKeys.DUPLICATE_CODE_OR_NAME);
            throw new DuplicateCodeOrNameException(ExceptionKeys.DUPLICATE_CODE_OR_NAME);
        }
    }

    public Optional<Product> findById(Long id) {
//        return productRepository.findById(id);
        return null;
    }
}
