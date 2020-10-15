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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class ProductService {
    private final DaoFactory daoFactory;
    private static final Logger logger = LogManager.getLogger(ProductService.class);

    public ProductService(final DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Page<Product> getProducts(Pageable pageable) {
        try (ProductDao productDao = daoFactory.createProductDao()) {
            return productDao.findByOrderByName(pageable);
        }
    }

    public void saveNewProduct(ProductDTO productDTO) {
        Product product = new Product(productDTO);
        try (ProductDao productDao = daoFactory.createProductDao()){
            productDao.create(product);
            logger.debug("Product saved successfully.");
        } catch (Exception e) {
            logger.error("{}", ExceptionKeys.DUPLICATE_CODE_OR_NAME);
            throw new DuplicateCodeOrNameException(ExceptionKeys.DUPLICATE_CODE_OR_NAME);
        }
    }

    public void setAmountById(Long amount, Long id) {
        try (ProductDao productDao = daoFactory.createProductDao()) {
            productDao.updateAmountById(amount, id);
        }
        logger.debug("Set new amount to product was done successfully.");
    }

    public Product findByIdentifier(String identifier) {
        try (ProductDao productDao = daoFactory.createProductDao()) {
            return productDao.findByIdentifier(identifier).orElseThrow(
                    () -> {
                        logger.error("{}", ExceptionKeys.NO_SUCH_PRODUCTS);
                        throw new NoSuchProductException(ExceptionKeys.NO_SUCH_PRODUCTS);
                    }
            );
        }
    }

    public void updateProduct(Product product) {
        try (ProductDao productDao = daoFactory.createProductDao()) {
            productDao.update(product);
        }
    }

    public Optional<Product> findById(Long id) {
        try (ProductDao productDao = daoFactory.createProductDao()) {
            return productDao.findById(id);
        }
    }
}
