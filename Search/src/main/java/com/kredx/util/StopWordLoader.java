package com.kredx.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
 * Created by sourabhjain on 18/9/16.
 */
public class StopWordLoader {

    private static Logger logger = Logger.getLogger(StopWordLoader.class);
    private static HashSet<String> stopWords = new HashSet<String>();

    private static void loadStopWords() throws IOException {
        logger.info("Loading StopWords ....");
        BufferedReader br = new BufferedReader(new InputStreamReader(StopWordLoader.class.getResourceAsStream("/StopWords.txt")));
        String line;
        while ((line = br.readLine()) != null){
            stopWords.add(line);
        }
        br.close();
        logger.info("Total StopWords loaded : " + stopWords.size());
    }
    static {
        try {

            loadStopWords();
        } catch (Exception e) {
            logger.error("Error in loading stop word file , Reason :: " + e.getMessage(), e);
        }
    }

    public static boolean isStopWord(String token){
        if (stopWords.contains(token))  return true;
        return false;
    }
}
