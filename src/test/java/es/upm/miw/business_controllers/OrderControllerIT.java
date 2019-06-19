package es.upm.miw.business_controllers;

import es.upm.miw.TestConfig;
import es.upm.miw.business_services.RestService;
import es.upm.miw.documents.Order;
import es.upm.miw.documents.OrderLine;
import es.upm.miw.dtos.OrderDto;
import es.upm.miw.dtos.OrderSearchDto;
import es.upm.miw.exceptions.NotFoundException;
import es.upm.miw.repositories.OrderRepository;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@TestConfig
public class OrderControllerIT {

    @Autowired
    private OrderController orderController;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestService restService;

    private static String idOrder = "";

    private static final String BEARER = "Bearer ";

    @BeforeEach
    void createOrder() {
        if (this.orderRepository.findAll().size() == 0) {
            String[] articlesId = {"1", "8400000000048", "8400000000024", "8400000000031"};
            String providerId = "5cfffb93525ceb1784133a24";
            String description = "ORDER-" + String.valueOf((int) (Math.random() * 10000));
            for (int i = 0; i < 3; i++) {
                OrderLine[] orderLines = Arrays.array(new OrderLine(articlesId[i], 4), new OrderLine(articlesId[i], 5));
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
    void cleanDB (){
        orderRepository.deleteAll();
    }

}
