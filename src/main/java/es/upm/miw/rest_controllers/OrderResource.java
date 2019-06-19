package es.upm.miw.rest_controllers;

import es.upm.miw.business_controllers.OrderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(OrderResource.ORDERS)

public class OrderResource {

    public static final String ORDERS = "/orders";
    public static final String ID = "/{id}";

    @Autowired
    private OrderController orderController;

}