package com.kredx;

import com.kredx.bean.MinHeapNode;
import com.kredx.bean.OutputBean;
import com.kredx.bean.ReviewScorePair;
import com.kredx.bean.Strategy;
import com.kredx.manager.ReviewManager;
import com.kredx.manager.TokenManager;
import com.kredx.util.Util;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by sourabhjain on 18/9/16.
 */
public class RequestHandler {

    private static Logger logger = Logger.getLogger(RequestHandler.class);

    private ReviewManager reviewManager = ReviewManager.getInstance();
    private TokenManager tokenManager = TokenManager.getInstance();
    private static RequestHandler instance = new RequestHandler();

    private RequestHandler(){   }

    public static RequestHandler getInstance(){
        return instance;
    }

    private PriorityQueue<ReviewScorePair> getPriorityListForTopReviews(int size, final Strategy strategy){
        return new PriorityQueue<ReviewScorePair>(size, new Comparator<ReviewScorePair>() {
            @Override
            public int compare(ReviewScorePair o1, ReviewScorePair o2) {
                if (o1.getTokenMatchingScore().equals(o2.getTokenMatchingScore())){
                    switch (strategy){
                        case MostRecent:    return o1.getReview().getTime().compareTo(o2.getReview().getTime());
                        case HighScore:     return o1.getReview().getScore().compareTo(o2.getReview().getScore());
                        case LowScore:      return -o1.getReview().getScore().compareTo(o2.getReview().getScore());
                    }
                }
                return o1.getTokenMatchingScore().compareTo(o2.getTokenMatchingScore());
            }
        });
    }

    private List<List<Integer>> tokensToIdLists(Set<String> tokens){
        List<List<Integer>> sortedReviewIdsForAllTokens = new ArrayList<List<Integer>>();
        for (String token : tokens){
            List<Integer> reviewIds = tokenManager.getReviewIds(token);
            if (reviewIds != null && reviewIds.size() > 0)  sortedReviewIdsForAllTokens.add(reviewIds);
        }
        return sortedReviewIdsForAllTokens;
    }

    private void addFirstElementOfEachListInMinHeap(PriorityQueue<MinHeapNode> minHeap, List<List<Integer>> sortedReviewIdsForAllTokens){
        for (int i = 0 ; i < sortedReviewIdsForAllTokens.size() ; i++)
            minHeap.add(new MinHeapNode(sortedReviewIdsForAllTokens.get(i).get(0), i, 1));
    }

    private void addNextElementInMinHeap(PriorityQueue<MinHeapNode> minHeap, MinHeapNode polledNode, List<List<Integer>> sortedReviewIdsForAllTokens){
        int i = polledNode.getI();
        int j = polledNode.getJ();
        if (sortedReviewIdsForAllTokens.get(i).size() > j){
            minHeap.add(new MinHeapNode(sortedReviewIdsForAllTokens.get(i).get(j), i, j+1));
        }
    }

    private void addReviewInTopReviews(PriorityQueue<ReviewScorePair> topReviews, ReviewScorePair reviewScorePair, int k){

        topReviews.add(reviewScorePair);
        if (topReviews.size() > k)  topReviews.poll();
    }

    private PriorityQueue<ReviewScorePair> mergeSortedList(List<List<Integer>> sortedReviewIdsForAllTokens, int k, Strategy strategy){

        if (k == 0 || sortedReviewIdsForAllTokens.size() == 0) return new PriorityQueue<ReviewScorePair>();
        logger.info("merging sorted list ");
        PriorityQueue<MinHeapNode> minHeap = new PriorityQueue<MinHeapNode>(sortedReviewIdsForAllTokens.size(), new Comparator<MinHeapNode>() {
            @Override
            public int compare(MinHeapNode o1, MinHeapNode o2) {
                return o1.getReviewId().compareTo(o2.getReviewId());
            }
        });
        PriorityQueue<ReviewScorePair> topReviews = getPriorityListForTopReviews(k, strategy);
        addFirstElementOfEachListInMinHeap(minHeap, sortedReviewIdsForAllTokens);
        int prevReviewId = -1; double freq = 0;
        while (minHeap.size() > 0){
            MinHeapNode polledNode = minHeap.poll();
            addNextElementInMinHeap(minHeap, polledNode, sortedReviewIdsForAllTokens);
            if (prevReviewId == polledNode.getReviewId())   freq = freq + 1.0;
            else {
                if (prevReviewId != -1) addReviewInTopReviews(topReviews, new ReviewScorePair(reviewManager.getReview(prevReviewId), freq), k);
                prevReviewId = polledNode.getReviewId();
                freq = 1.0;
            }
        }
        if (prevReviewId != - 1) addReviewInTopReviews(topReviews, new ReviewScorePair(reviewManager.getReview(prevReviewId), freq), k);
        return topReviews;
    }

    public List<ReviewScorePair> getTopReviewsAsList(PriorityQueue<ReviewScorePair> topReviews, int size){
        List<ReviewScorePair> result = new ArrayList<ReviewScorePair>();
        while (topReviews.size() > 0){
            ReviewScorePair polledData = topReviews.poll();
            polledData.setTokenMatchingScore(polledData.getTokenMatchingScore() / (double) size);
            result.add(polledData);
        }
        Collections.reverse(result);
        return result;
    }

    public OutputBean getTopReviews(String query, int k, Strategy strategy){
        logger.info("Getting top reviews for, q : " + query + " , k : " + k + " , strategy : " +  strategy);
        HashSet<String> tokens = Util.getTokens(query);
        logger.info("Tokens from query : " + tokens);
        List<List<Integer>> sortedReviewIdsForAllTokens = tokensToIdLists(tokens);
        PriorityQueue<ReviewScorePair> topReviews = mergeSortedList(sortedReviewIdsForAllTokens, k, strategy);
        return new OutputBean(tokens, Util.removeStopWords(tokens), getTopReviewsAsList(topReviews, tokens.size()));
    }

    public OutputBean getTopReviews(String query){
        return getTopReviews(query, 20, Strategy.HighScore);
    }

    public OutputBean getTopReviews(String query, int k){
        return getTopReviews(query, k, Strategy.HighScore);
    }

}
