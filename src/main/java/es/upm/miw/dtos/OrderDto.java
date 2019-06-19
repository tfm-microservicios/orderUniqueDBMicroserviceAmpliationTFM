package es.upm.miw.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import es.upm.miw.documents.Order;
import es.upm.miw.documents.OrderLine;

import java.time.LocalDateTime;
import java.util.Arrays;

@JsonInclude(Include.NON_NULL)
public class OrderDto {

    private String id;

    private String description;

    @JsonInclude(Include.NON_NULL)
    private String providerId;

    private LocalDateTime openingDate;

    private LocalDateTime closingDate;

    @JsonInclude(Include.NON_NULL)
    private OrderLine[] orderLines;

    public OrderDto() {
        // Empty for framework
    }

    public OrderDto(Order order) {
        this.id = order.getId();
        this.openingDate = order.getOpeningDate();
        this.closingDate = order.getClosingDate();
        this.description = order.getDescription();
        this.providerId = order.getProviderId();
        this.orderLines = order.getOrderLines();
    }

    public Order prepareOrder (){
        Order order = new Order(this.description, this.providerId,this.orderLines);
        order.setOpeningDate(this.openingDate);
        order.setClosingDate(this.closingDate);
        return order;
    }

    public String getDescription() {
        return description;
    }

    public String getProviderId() {
        return providerId;
    }

    public LocalDateTime getOpeningDate() {
        return openingDate;
    }

    public LocalDateTime getClosingDate() {
        return closingDate;
    }

    public OrderLine[] getOrderLines() {
        return orderLines;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public void setOpeningDate(LocalDateTime openingDate) {
        this.openingDate = openingDate;
    }

    public void setClosingDate(LocalDateTime closingDate) {
        this.closingDate = closingDate;
    }

    public void setOrderLines(OrderLine[] orderLines) {
        this.orderLines = orderLines;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id + "\'" +
                ", description='" + description + '\'' +
                ", providerId=" + providerId +
                ", openingDate=" + openingDate +
                ", closingDate=" + closingDate +
                ", orderLines=" + Arrays.toString(orderLines) +
                '}';
    }
}
