package com.kredx.reader;

import com.kredx.bean.Review;
import com.kredx.util.Util;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sourabhjain on 18/9/16.
 */
public class RawReviewFileReader {

    private static Logger logger = Logger.getLogger(RawReviewFileReader.class);

    public static void parseLine(String line, LinkedList<Review> reviews){
        if (Util.parseData(line) != null) {
            try {
                String id = line.split(":")[0].trim();
                String val = line.split(":")[1].trim();
                if (id.equals("product/productId")) reviews.add(new Review());

                if (id.equals("product/productId")) reviews.getLast().setProductId(val);
                else if (id.equals("review/userId")) reviews.getLast().getUser().setUserId(val);
                else if (id.equals("review/profileName"))  reviews.getLast().getUser().setProfileName(val);
                else if (id.equals("review/helpfulness"))  reviews.getLast().setHelpfulness(val);
                else if (id.equals("review/score"))    reviews.getLast().setScore(Double.parseDouble(val));
                else if (id.equals("review/time")) reviews.getLast().setTime(val.replaceAll("<[^>]*>", " "));
                else if (id.equals("review/summary"))   reviews.getLast().setSummary(val.replaceAll("<[^>]*>", " "));
                else if (id.equals("review/text"))  reviews.getLast().setText(val);
                else throw new RuntimeException("Incorrect Data Feed ! : " + id);
            }catch (Exception e){
                reviews.getLast().getUser().setProfileName(reviews.getLast().getUser().getProfileName() + "\n" + line);
            }
        }
    }

    public static List<Review> readFile(String fileName) throws IOException {

        logger.info("Reading Reviews from file : " + fileName);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            LinkedList<Review> reviews = new LinkedList<Review>();
            String line;
            while ((line = br.readLine()) != null){
                parseLine(line, reviews);
            }
            logger.info("File Parsed Successfully ... ");
            return new ArrayList<Review>(reviews);
        }finally {
            if (br != null) br.close();
        }
    }
}
