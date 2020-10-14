package com.paulok777.controller.command.impl.commodity_expert;

import com.paulok777.controller.command.Command;
import com.paulok777.model.entity.Product;
import com.paulok777.model.util.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GetProductsCommand implements Command {


    @Override
    public String execute(HttpServletRequest request) {
//        String page = request.getParameter("page");
//        String size = request.getParameter("size");
//        int currentPage = page == null ? 1 : Integer.parseInt(page);
//        int pageSize = ;
//        Page<Product> productPage = productService.getProducts(PageRequest.of(currentPage - 1, pageSize));
//        model.addAttribute("productPage", productPage);
//
//        int totalPages = productPage.getTotalPages();
//        if (totalPages > 0) {
//            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
//                    .boxed()
//                    .collect(Collectors.toList());
//            model.addAttribute("pageNumbers", pageNumbers);
//        }
//
//        model.addAttribute("currentPage", currentPage);
//        model.addAttribute("measures", Product.Measure.values());
        return "products";
    }
}
