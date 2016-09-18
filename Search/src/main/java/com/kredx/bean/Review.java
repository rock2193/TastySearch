package com.kredx.bean;

/**
 * Created by sourabhjain on 18/9/16.
 */
public class Review {

    private String productId;
    private User user;
    private String helpfulness;
    private Double score;
    private String time;
    private String summary;
    private String text;

    public Review(){
        this.user = new User();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getHelpfulness() {
        return helpfulness;
    }

    public void setHelpfulness(String helpfulness) {
        this.helpfulness = helpfulness;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}