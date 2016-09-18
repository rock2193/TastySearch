package com.kredx.bean;

/**
 * Created by sourabhjain on 18/9/16.
 */
public class ReviewScorePair {

    private Review review;
    private Double tokenMatchingScore;

    public ReviewScorePair(Review review, Double tokenMatchingScore) {
        this.review = review;
        this.tokenMatchingScore = tokenMatchingScore;
    }


    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Double getTokenMatchingScore() {
        return tokenMatchingScore;
    }

    public void setTokenMatchingScore(Double tokenMatchingScore) {
        this.tokenMatchingScore = tokenMatchingScore;
    }
}
