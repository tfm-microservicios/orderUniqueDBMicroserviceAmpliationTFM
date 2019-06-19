package es.upm.miw.documents;

public class OrderLine {

    private String articleId;

    private Integer requiredAmount;

    private Integer finalAmount;

    public OrderLine() {
    }

    public OrderLine(String articleId, int requiredAmount) {
        super();
        this.articleId = articleId;
        this.requiredAmount = requiredAmount;
        this.finalAmount = requiredAmount;
    }

    public Integer getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Integer finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getArticleId() {
        return articleId;
    }

    public Integer getRequiredAmount() {
        return requiredAmount;
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "articleId=" + articleId +
                ", requiredAmount=" + requiredAmount +
                ", finalAmount=" + finalAmount +
                '}';
    }

}
