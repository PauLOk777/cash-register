package com.paulok777.controller.command.impl.cashier_commons.senior_cashier;

import com.paulok777.controller.command.Command;
import com.paulok777.model.dto.ReportDTO;
import com.paulok777.model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class MakeXReportCommand implements Command {
    private final OrderService orderService;
    private static final Logger logger = LogManager.getLogger(MakeXReportCommand.class);

    public MakeXReportCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        ReportDTO reportDTO = orderService.makeXReport();
        logger.info("(username: {}) X report, ReportDTO: {}",
                request.getSession().getAttribute("username"), reportDTO);
        request.setAttribute("report", reportDTO);
        return "/WEB-INF/senior_cashier/reports.jsp";
    }
}
