package com.kredx.bean;

/**
 * Created by sourabhjain on 18/9/16.
 */
public class ReviewScorePair {

    private Review review;
    private Double score;

    public ReviewScorePair(Review review, Double score) {
        this.review = review;
        this.score = score;
    }


    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
