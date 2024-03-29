package es.upm.miw.dtos;

import javax.validation.constraints.NotNull;

public class ArticleMinimumDto {

    private String code;

    @NotNull
    private String description;

    public ArticleMinimumDto() {
        this.description = "";
    }

    public ArticleMinimumDto(String code, String description) {
        super();
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ArticleMinimumDto [code=" + code + ", description=" + description + "]";
    }

}
