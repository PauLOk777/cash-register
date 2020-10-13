package com.paulok777.controller.command.impl.senior_cashier;

import com.paulok777.controller.command.Command;
import com.paulok777.model.dto.ReportDTO;
import com.paulok777.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;

public class MakeXReportCommand implements Command {
    private final OrderService orderService;

    public MakeXReportCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        ReportDTO reportDTO = orderService.makeXReport();
        request.setAttribute("report", reportDTO);
        return "/WEB-INF/senior_cashier/reports.jsp";
    }
}
