package com.paulok777.controller.command.impl.commodity_expert;

import com.paulok777.controller.command.Command;
import com.paulok777.model.entity.Product;
import com.paulok777.model.service.ProductService;
import com.paulok777.model.util.Page;
import com.paulok777.model.util.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GetProductsCommand implements Command {
    private final ProductService productService;

    public GetProductsCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = request.getParameter("page");
        String size = request.getParameter("size");
        int currentPage = page == null ? 1 : Integer.parseInt(page);
        int pageSize = size == null ? 2 : Integer.parseInt(size);
        Page<Product> productPage = productService.getProducts(new Pageable(currentPage - 1, pageSize));
        request.setAttribute("products", productPage.getContent());

        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            request.setAttribute("pageNumbers", pageNumbers);
        }

        request.setAttribute("size", pageSize);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("measures", Product.Measure.values());
        return "/WEB-INF/commodity_expert/products.jsp";
    }
}
