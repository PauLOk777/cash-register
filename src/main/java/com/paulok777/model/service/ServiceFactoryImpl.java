package com.paulok777.model.service;

import com.paulok777.model.dao.DaoFactory;

public class ServiceFactoryImpl extends ServiceFactory{
    @Override
    public UserService createUserService() {
        return new UserService(DaoFactory.getInstance());
    }

    @Override
    public OrderService createOrderService() {
        return new OrderService(DaoFactory.getInstance(), createProductService(), createUserService());
    }

    @Override
    public ProductService createProductService() {
        return new ProductService(DaoFactory.getInstance());
    }
}
