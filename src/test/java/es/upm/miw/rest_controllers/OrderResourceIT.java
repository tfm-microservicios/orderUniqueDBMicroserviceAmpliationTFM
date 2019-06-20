package es.upm.miw.rest_controllers;

import es.upm.miw.business_services.RestBuilder;
import es.upm.miw.business_services.RestService;
import es.upm.miw.documents.Order;
import es.upm.miw.documents.OrderLine;
import es.upm.miw.dtos.OrderDto;
import es.upm.miw.exceptions.BadRequestException;
import es.upm.miw.repositories.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ApiTestConfig
public class OrderResourceIT {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestService restService;

    @Autowired
    private Environment environment;

    private static String idOrder = "";

    @BeforeEach
    void createOrder() {
        if (this.orderRepository.findAll().size() == 0) {
            String[] articlesId = {"1", "8400000000048", "8400000000024", "8400000000031"};
            String providerId = "5cfffb93525ceb1784133a24";
            String description = "ORDER-" + String.valueOf((int) (Math.random() * 10000));
            for (int i = 0; i < 3; i++) {
                OrderLine[] orderLines = org.assertj.core.util.Arrays.array(new OrderLine(articlesId[i], 4));
                orderLines[0].setFinalAmount(1);
                if (i == 2) {
                    description = "ORDER-02019";
                }
                Order order = new Order(description, providerId, orderLines);
                this.orderRepository.save(order);
                idOrder = order.getId();
            }
        }
    }

    @AfterEach
    void cleanDB() {
        orderRepository.deleteAll();
    }

    @Test
    void testReadAll() {
        List<OrderDto> orders = Arrays.asList(this.restService.loginAdmin()
                .restBuilder(new RestBuilder<OrderDto[]>())
                .clazz(OrderDto[].class)
                .path(OrderResource.ORDERS)
                .get()
                .build());
        assertNotNull(orders);
        assertEquals(3, orders.size());
    }

    @Test
    void testCloseOrderPass() {
        Order order = orderRepository.findById(idOrder).orElse(null);
        assertNotNull(order);
        assertNull(order.getClosingDate());
        OrderDto orderDto = this.restService.loginAdmin()
                .restBuilder(new RestBuilder<OrderDto>())
                .clazz(OrderDto.class)
                .path(OrderResource.ORDERS).path(OrderResource.CLOSE).body(new OrderDto(order))
                .post()
                .build();
        assertNotNull(orderDto);
        assertNotNull(orderDto.getClosingDate());
    }

    @Test
    void testCloseOrderOrderLineEmpty() {
        Order order = orderRepository.findById(idOrder).orElse(null);
        assertNotNull(order);
        order.setOrderLines(new OrderLine[]{});
        assertThrows(HttpClientErrorException.BadRequest.class, () ->
                this.restService.loginAdmin()
                        .restBuilder(new RestBuilder<OrderDto>())
                        .clazz(OrderDto.class)
                        .path(OrderResource.ORDERS).path(OrderResource.CLOSE).body(new OrderDto(order))
                        .post()
                        .build()
        );
    }

}
