package com.paulok777.model.service;

public abstract class ServiceFactory {
    private static ServiceFactory serviceFactory;

    public abstract UserService createUserService();
    public abstract OrderService createOrderService();
    public abstract ProductService createProductService();

    public static ServiceFactory getInstance() {
        if (serviceFactory == null) {
            synchronized (ServiceFactory.class) {
                if (serviceFactory == null) {
                    serviceFactory = new ServiceFactoryImpl();
                }
            }
        }
        return serviceFactory;
    }
}
