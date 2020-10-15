package com.paulok777.controller.command.impl.commodity_expert;

import com.paulok777.controller.command.Command;
import com.paulok777.controller.util.Validator;
import com.paulok777.model.dto.ProductDTO;
import com.paulok777.model.entity.Product;
import com.paulok777.model.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CreateProductCommand implements Command {
    private final ProductService productService;
    private static final Logger logger = LogManager.getLogger(CreateProductCommand.class.getName());

    public CreateProductCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        ProductDTO productDTO = ProductDTO.builder()
                .code(request.getParameter("code"))
                .name(request.getParameter("name"))
                .price(Integer.parseInt(request.getParameter("price")))
                .measure(request.getParameter("measure"))
                .amount(Long.parseLong(request.getParameter("amount")))
                .build();

        logger.debug("ProductDTO for new product: {}", productDTO);

        Validator.validateProduct(productDTO);
        productService.saveNewProduct(productDTO);
        return "redirect:/commodity_expert/products";
    }
}
