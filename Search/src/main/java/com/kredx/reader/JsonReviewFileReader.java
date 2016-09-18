package com.kredx.reader;

import com.google.gson.Gson;
import com.kredx.bean.Review;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by sourabhjain on 18/9/16.
 */
public class JsonReviewFileReader {

    private static Logger logger = Logger.getLogger(JsonReviewFileReader.class);
    public static HashMap<Integer, Review> readFile(String fileName) throws IOException {

        System.out.println("path : " + new File("abc").getAbsolutePath());

        logger.info("Reading Reviews from file : " + fileName);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            HashMap<Integer, Review> reviews = new HashMap<Integer, Review>();
            String line;
            Integer id = 0;
            while ((line = br.readLine()) != null){
                reviews.put(id, new Gson().fromJson(line, Review.class));
                id = id + 1;
            }
            return reviews;
        }finally {
            if (br != null) br.close();
        }
    }
}
