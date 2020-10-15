package com.paulok777.controller.command.impl.commodity_expert;

import com.paulok777.controller.command.Command;
import com.paulok777.controller.util.Validator;
import com.paulok777.model.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ChangeAmountOfProductCommand implements Command {
    private final ProductService productService;
    private static final Logger logger = LogManager.getLogger(ChangeAmountOfProductCommand.class.getName());

    public ChangeAmountOfProductCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String[] subUris = request.getRequestURI().split("/");
        String id = subUris[subUris.length - 1];
        long amount = Long.parseLong(request.getParameter("amount"));
        logger.info("change amount of product (id: {}) to: {}", id, amount);
        Validator.validateAmountForCommodityExpert(amount);
        productService.setAmountById(amount, Long.valueOf(id));
        return "redirect:/commodity_expert/products";
    }
}
