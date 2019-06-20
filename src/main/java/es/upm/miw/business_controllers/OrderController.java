package es.upm.miw.business_controllers;

import es.upm.miw.business_services.RestBuilder;
import es.upm.miw.business_services.RestService;
import es.upm.miw.documents.Order;
import es.upm.miw.documents.OrderLine;
import es.upm.miw.dtos.ArticleDto;
import es.upm.miw.dtos.OrderDto;
import es.upm.miw.exceptions.BadRequestException;
import es.upm.miw.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class OrderController {

    private static final String ARTICLES = "/articles";
    private static final String CODE_ID = "/{code}";

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

    public OrderDto closeOrder(OrderDto orderDto, String token) {
        String orderId = orderDto.getId();
        OrderLine[] orderLine = orderDto.getOrderLines();
        Order closeOrder = this.orderRepository.findById(orderId).orElse(null);
        if (orderLine.length > 0 && closeOrder != null) {
            closeOrder.close();
            closeOrder.setOrderLines(orderLine);
            closeOrder = this.orderRepository.save(closeOrder);
            this.updateArticleStock(closeOrder, token);
        } else {
            throw new BadRequestException("orderLine is empty");
        }

        return new OrderDto(closeOrder);
    }

    private void updateArticleStock(Order closeOrder, String token) {
        for (OrderLine orderLine : closeOrder.getOrderLines()) {
            ArticleDto articleDto = this.restService.setToken(token).restBuilder(new RestBuilder<ArticleDto>())
                    .clazz(ArticleDto.class).heroku().serverUri(articleProviderURI)
                    .path(ARTICLES).path(CODE_ID).expand(orderLine.getArticleId())
                    .body(orderLine).get().log().build();
            articleDto.setStock(articleDto.getStock()-orderLine.getFinalAmount());
            this.restService.setToken(token).restBuilder(new RestBuilder<ArticleDto>())
                    .clazz(ArticleDto.class).heroku().serverUri(articleProviderURI)
                    .path(ARTICLES).path(CODE_ID).expand(orderLine.getArticleId())
                    .body(articleDto).put().log().build();
        }
    }

}
