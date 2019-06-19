package es.upm.miw.business_controllers;

import es.upm.miw.business_services.RestService;
import es.upm.miw.documents.Order;
import es.upm.miw.dtos.OrderDto;
import es.upm.miw.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    public static final String PROVIDERS_ARTICLES_VALIDATION = "/providers/validate-presence";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestService restService;

    @Autowired
    private Environment environment;

    @Value("${article.provider.microservice}")
    private String articleProviderURI;


    public List<OrderDto> readAll() {
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orderRepository.findAll()) {
            orderDtos.add(new OrderDto(order));
        }
        return orderDtos;
    }

}
