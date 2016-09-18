package com.kredx.util;

import com.kredx.bean.Review;

import java.util.HashSet;

/**
 * Created by sourabhjain on 18/9/16.
 */
public class Util {

    public static String parseData(String data){

        if (data == null || data.trim().length() == 0)   return null;
        return data.trim();
    }

    public static HashSet<String> getTokens(Review review){
        HashSet<String> tokens = new HashSet<String>();
        for (String sub : review.getSummary().split("[^\\w']+")) {
            if (parseData(sub) != null) tokens.add(sub.toLowerCase());
        }
        for (String sub : review.getText().split("[^\\w']+")) {
            if (parseData(sub) != null) tokens.add(sub.toLowerCase());
        }
        return tokens;
    }

    public static HashSet<String> getTokens(String query){
        HashSet<String> tokens = new HashSet<String>();
        for (String sub : query.split("[^\\w']+")) {
            if (parseData(sub) != null)
                tokens.add(sub.toLowerCase());
        }
        return tokens;
    }

    public static HashSet<String> removeStopWords(HashSet<String> tokens){
        HashSet<String> result = new HashSet<String>();
        for (String token : tokens){
            if (!StopWordLoader.isStopWord(token))   result.add(token);
        }
        return result;
    }
}
