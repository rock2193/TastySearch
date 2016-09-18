package com.kredx.manager;

import com.kredx.bean.Review;
import com.kredx.reader.JsonReviewFileReader;
import com.kredx.util.DALConstants;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by sourabhjain on 18/9/16.
 */
public class ReviewManager {

    private static Logger logger = Logger.getLogger(ReviewManager.class);
    private static HashMap<Integer, Review> idToReviewMap = new HashMap<Integer, Review>();
    private static ReviewManager reviewManager = new ReviewManager();

    private ReviewManager(){    }
    public static ReviewManager getInstance(){
        return  reviewManager;
    }

    static {
        try {
            logger.info("Loading All Review From JSON File .... ");
            idToReviewMap = JsonReviewFileReader.readFile(DALConstants.sampleReviewFilePath);
        } catch (IOException e) {
            logger.error("Error in loading file , Reason :: " + e.getMessage(), e);
            throw new RuntimeException("ReviewFileLoadingError");
        }
    }

    public Review getReview(Integer id){
        return idToReviewMap.get(id);
    }

    public Set<Integer> getAllIds(){
        return idToReviewMap.keySet();
    }

}
