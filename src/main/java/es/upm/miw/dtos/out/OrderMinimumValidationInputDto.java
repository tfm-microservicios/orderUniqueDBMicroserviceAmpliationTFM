package es.upm.miw.dtos.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import es.upm.miw.documents.OrderLine;
import es.upm.miw.dtos.OrderDto;

import java.util.ArrayList;
import java.util.List;

public class OrderMinimumValidationInputDto {

    @JsonInclude(Include.NON_NULL)
    private String providerId;

    @JsonInclude(Include.NON_NULL)
    private List<String> articleIds;

    public OrderMinimumValidationInputDto() {
    }

    public OrderMinimumValidationInputDto(OrderDto orderDto) {
        this.providerId = orderDto.getProviderId();
        this.articleIds = new ArrayList<>();
        for (OrderLine orderLine : orderDto.getOrderLines()) {
            this.articleIds.add(orderLine.getArticleId());
        }
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public List<String> getArticleIds() {
        return articleIds;
    }

    public void setArticleIds(List<String> articleIds) {
        this.articleIds = articleIds;
    }

    @Override
    public String toString() {
        return "OrderMinimumValidationInputDto{" +
                "providerId='" + providerId + '\'' +
                ", articleIds=" + articleIds +
                '}';
    }
}
