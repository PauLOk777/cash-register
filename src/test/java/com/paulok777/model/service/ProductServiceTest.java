package com.paulok777.model.service;

import com.paulok777.model.dao.DaoFactory;
import com.paulok777.model.dao.ProductDao;
import com.paulok777.model.dto.ProductDTO;
import com.paulok777.model.entity.Product;
import com.paulok777.model.exception.cash_register_exc.product_exc.NoSuchProductException;
import com.paulok777.model.exception.cash_register_exc.product_exc.DuplicateCodeOrNameException;
import com.paulok777.model.util.Page;
import com.paulok777.model.util.Pageable;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

public class ProductServiceTest {
    DaoFactory daoFactory = mock(DaoFactory.class);
    ProductDao productDao = mock(ProductDao.class);
    ProductService productService = new ProductService(daoFactory);
    Pageable pageable = new Pageable(0, 1);
    private final ProductDTO productDTO = ProductDTO.builder()
            .code("1234")
            .name("Banana")
            .price(100)
            .measure("BY_WEIGHT")
            .amount(4000)
            .build();
    private final Product product = Product.builder()
            .code(productDTO.getCode())
            .name(productDTO.getName())
            .price(productDTO.getPrice())
            .measure(Product.Measure.valueOf(productDTO.getMeasure()))
            .amount(productDTO.getAmount())
            .build();

    @Test
    public void testFindByOrderByNameShouldReturnPageOfProducts() {
        when(daoFactory.createProductDao()).thenReturn(productDao);
        when(productDao.findByOrderByName(pageable)).thenReturn(new Page<>(1, List.of(product)));
        Page<Product> productPage = productService.getProducts(pageable);
        assertEquals(List.of(product), productPage.getContent());
        assertEquals(1, productPage.getTotalPages());
        verify(daoFactory, times(1)).createProductDao();
        verify(productDao, times(1)).findByOrderByName(pageable);
    }

    @Test
    public void testSaveNewProductShouldWorkWithoutExceptionsWhenDaoDontGenerateExceptions() {
        when(daoFactory.createProductDao()).thenReturn(productDao);
        productService.saveNewProduct(productDTO);
        verify(daoFactory, times(1)).createProductDao();
        verify(productDao, times(1)).create(product);
    }

    @Test(expected = DuplicateCodeOrNameException.class)
    public void testSaveNewProductShouldThrowDuplicateCodeOrNameExceptionWhenDaoThrowsException() {
        when(daoFactory.createProductDao()).thenReturn(productDao);
        doThrow(new RuntimeException()).when(productDao).create(product);
        productService.saveNewProduct(productDTO);
        verify(daoFactory, times(1)).createProductDao();
        verify(productDao, times(1)).create(product);
    }

    @Test
    public void testSetAmountByIdShouldWorkWithoutExceptionsWhenDaoDontGenerateExceptions() {
        when(daoFactory.createProductDao()).thenReturn(productDao);
        productService.setAmountById(1L, 1L);
        verify(daoFactory, times(1)).createProductDao();
        verify(productDao, times(1)).updateAmountById(1L, 1L);
    }

    @Test
    public void testFindByIdentifierShouldReturnProductWhenProductWithThisCodeExists() {
        String identifier = "1234";
        when(daoFactory.createProductDao()).thenReturn(productDao);
        when(productDao.findByIdentifier(identifier)).thenReturn(Optional.of(product));
        Product productFromMethod = productService.findByIdentifier(identifier);
        assertEquals(product, productFromMethod);
        verify(daoFactory, times(1)).createProductDao();
        verify(productDao, times(1)).findByIdentifier(identifier);
    }

    @Test
    public void testFindByIdentifierShouldReturnProductWhenProductWithThisNameExists() {
        String identifier = "Banana";
        when(daoFactory.createProductDao()).thenReturn(productDao);
        when(productDao.findByIdentifier(identifier)).thenReturn(Optional.of(product));
        Product productFromMethod = productService.findByIdentifier(identifier);
        assertEquals(product, productFromMethod);
        verify(daoFactory, times(1)).createProductDao();
        verify(productDao, times(1)).findByIdentifier(identifier);
    }

    @Test(expected = NoSuchProductException.class)
    public void testFindByIdentifierShouldThrowNoSuchProductExceptionWhenProductWithThisIdentifierAbsent() {
        String identifier = "absent";
        when(daoFactory.createProductDao()).thenReturn(productDao);
        when(productDao.findByIdentifier(identifier)).thenReturn(Optional.empty());
        productService.findByIdentifier(identifier);
        verify(daoFactory, times(1)).createProductDao();
        verify(productDao, times(1)).findByIdentifier("1");
    }

    @Test
    public void testUpdateProductShouldWorkWithoutExceptionsWhenDaoDontGenerateExceptions() {
        when(daoFactory.createProductDao()).thenReturn(productDao);
        productService.updateProduct(product);
        verify(daoFactory, times(1)).createProductDao();
        verify(productDao, times(1)).update(product);
    }

    @Test
    public void testFindByIdShouldReturnProductOptionalWhenProductWithThisIdExists() {
        when(daoFactory.createProductDao()).thenReturn(productDao);
        when(productDao.findById(1L)).thenReturn(Optional.of(product));
        Optional<Product> productOptional = productService.findById(1L);
        assertEquals(Optional.of(product), productOptional);
        verify(daoFactory, times(1)).createProductDao();
        verify(productDao, times(1)).findById(1L);
    }
}
