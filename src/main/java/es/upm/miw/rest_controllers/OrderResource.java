package es.upm.miw.rest_controllers;

import es.upm.miw.business_controllers.OrderController;
import es.upm.miw.dtos.OrderDto;
import es.upm.miw.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(OrderResource.ORDERS)

public class OrderResource {

    public static final String ORDERS = "/orders";
    public static final String CLOSE = "/close";

    @Autowired
    private OrderController orderController;

    @GetMapping
    public List<OrderDto> readAll() {
        return this.orderController.readAll();
    }

    @PostMapping(value = CLOSE)
    public OrderDto close(@Valid @RequestBody OrderDto orderDto, @RequestHeader("Authorization") String token) {
        if(orderDto.getOrderLines() == null) {
            throw new BadRequestException("orderLine is empty");
        } else {
            return this.orderController.closeOrder(orderDto, token);
        }
    }

}