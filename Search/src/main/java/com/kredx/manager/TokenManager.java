package com.kredx.manager;

import com.kredx.bean.Review;
import com.kredx.util.StopWordLoader;
import com.kredx.util.Util;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by sourabhjain on 18/9/16.
 */
public class TokenManager {

    private static Logger logger = Logger.getLogger(TokenManager.class);
    private static HashMap<String, List<Integer>> tokenToReviewIdMap = new HashMap<String, List<Integer>>();
    private static ReviewManager reviewManager = ReviewManager.getInstance();
    private static TokenManager tokenManager = new TokenManager();

    private TokenManager(){ }

    private static void addTokensForReview(Integer id, Review review){
        HashSet<String> tokens = Util.getTokens(review);
        for (String token : tokens){
            if (Util.parseData(token) == null || StopWordLoader.isStopWord(token))  continue;
            if (!tokenToReviewIdMap.containsKey(token)) tokenToReviewIdMap.put(token, new ArrayList<Integer>());
            tokenToReviewIdMap.get(token).add(id);
        }
    }

    private static void sortIdsForEachToken(){
        logger.info("Sorting Review Ids for each token ...");
        for (Map.Entry<String, List<Integer>> entry : tokenToReviewIdMap.entrySet()){
            Collections.sort(entry.getValue());
        }
    }
    private static void buildTokenToReviewIdMap(){
        logger.info("Building Token to Review Map ...");
        for (Integer id : reviewManager.getAllIds()) {
            addTokensForReview(id, reviewManager.getReview(id));
        }
        sortIdsForEachToken();
    }

    static {
        buildTokenToReviewIdMap();
    }

    public static TokenManager getInstance(){
        return tokenManager;
    }

    public List<Integer> getReviewIds(String token){
        return tokenToReviewIdMap.get(token.toLowerCase().trim());
    }
}
