package com.kredx.bean;

/**
 * Created by sourabhjain on 18/9/16.
 */
public class MinHeapNode {

    private Integer reviewId;
    private Integer i;  // list index
    private Integer j;  // next element index to add in min heap


    public MinHeapNode(Integer reviewId, Integer i, Integer j) {
        this.reviewId = reviewId;
        this.i = i;
        this.j = j;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public Integer getI() {
        return i;
    }

    public Integer getJ() {
        return j;
    }
}
