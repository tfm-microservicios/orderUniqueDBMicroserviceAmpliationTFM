package es.upm.miw.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import es.upm.miw.documents.Tax;
import es.upm.miw.dtos.validations.BigDecimalPositive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ArticleDto extends ArticleMinimumDto {

    @JsonInclude(Include.NON_NULL)
    private String reference;

    @BigDecimalPositive
    @JsonInclude(Include.NON_NULL)
    private BigDecimal retailPrice;

    private Integer stock;

    private Tax tax;

    @JsonInclude(Include.NON_NULL)
    private String provider;

    @JsonInclude(Include.NON_NULL)
    private Boolean discontinued;

    @JsonInclude(Include.NON_NULL)
    private LocalDateTime registrationDate;

    public ArticleDto() {
        // Empty for framework
    }

    public ArticleDto(String code, String description, String reference, BigDecimal retailPrice, Integer stock, Tax tax) {
        super(code, description);
        this.reference = reference;
        this.retailPrice = retailPrice;
        this.stock = stock;
        this.tax = tax;
    }

    public String getReference() {
        return reference;
    }

    public ArticleDto setReference(String reference) {
        this.reference = reference;
        return this;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public ArticleDto setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
        return this;
    }

    public Integer getStock() {
        return stock;
    }

    public ArticleDto setStock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public String getProvider() {
        return provider;
    }

    public ArticleDto setProvider(String provider) {
        this.provider = provider;
        return this;
    }

    public Boolean getDiscontinued() {
        return discontinued;
    }

    public ArticleDto setDiscontinued(Boolean discontinued) {
        this.discontinued = discontinued;
        return this;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "ArticleDto{" +
                "reference='" + reference + '\'' +
                ", retailPrice=" + retailPrice +
                ", stock=" + stock +
                ", provider='" + provider + '\'' +
                ", discontinued=" + discontinued +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
