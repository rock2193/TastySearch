package com.kredx.util;

import com.google.gson.Gson;
import com.kredx.bean.Review;
import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by sourabhjain on 18/9/16.
 */
public class ReviewsFileWriter {

    private static Logger logger = Logger.getLogger(ReviewsFileWriter.class);
    public static void writeReviewsInFile(List<Review> reviews, String fileName) throws IOException {

        logger.info("Writing Reviews to file : " + fileName + " , size : " + reviews.size());
        PrintWriter pw = new PrintWriter(new FileWriter(fileName));
        int linesToFlush = 0;
        for (Review review : reviews){
            pw.write(new Gson().toJson(review));
            pw.write("\n");
            if (linesToFlush++ == 100){
                pw.flush();
                linesToFlush = 0;
            }
        }
        pw.close();

    }
}
