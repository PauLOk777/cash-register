package com.paulok777.controller.command.impl.senior_cashier;

import com.paulok777.controller.command.Command;
import com.paulok777.model.dto.ReportDTO;
import com.paulok777.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;

public class MakeZReportCommand implements Command {
    private final OrderService orderService;

    public MakeZReportCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        ReportDTO reportDTO = orderService.makeZReport();
        request.setAttribute("report", reportDTO);
        return "/WEB-INF/senior_cashier/reports.jsp";
    }
}
