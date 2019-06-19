package es.upm.miw.business_controllers;

import es.upm.miw.business_services.RestService;
import es.upm.miw.documents.Order;
import es.upm.miw.documents.OrderLine;
import es.upm.miw.dtos.OrderDto;
import es.upm.miw.exceptions.BadRequestException;
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
    public static final String PROVIDERS_ARTICLES_UPDATE_STOCK = "/articles/stock-update/{code}/{amount}";

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
        for (Order order : this.orderRepository.findAll()) {
            orderDtos.add(new OrderDto(order));
        }
        return orderDtos;
    }

    public OrderDto closeOrder(OrderDto orderDto) {
        String orderId = orderDto.getId();
        OrderLine[] orderLine = orderDto.getOrderLines();
        Order closeOrder = this.orderRepository.findById(orderId).orElse(null);
        if (orderLine.length > 0 && closeOrder != null) {
            closeOrder.close();
            closeOrder.setOrderLines(orderLine);
            closeOrder = this.orderRepository.save(closeOrder);
        } else {
            throw new BadRequestException("orderLine is empty");
        }

        return new OrderDto(closeOrder);
    }

    public void updateArticleStock(Order closeOrder) {
        // TO DO -- send PUT requests to Article microservice to update stock amount.
    }

}
