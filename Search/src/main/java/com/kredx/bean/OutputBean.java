package com.kredx.bean;

import java.util.HashSet;
import java.util.List;

/**
 * Created by sourabhjain on 18/9/16.
 */
public class OutputBean {

    private HashSet<String> tokens;
    private HashSet<String> tokensRemovingStopWords;
    private List<ReviewScorePair> reviewScorePairs;

    public OutputBean(HashSet<String> tokens, HashSet<String> tokensRemovingStopWords, List<ReviewScorePair> reviewScorePairs) {
        this.tokens = tokens;
        this.tokensRemovingStopWords = tokensRemovingStopWords;
        this.reviewScorePairs = reviewScorePairs;
    }
}
