package com.kredx.DataGenerator;

import com.kredx.bean.Review;
import com.kredx.reader.RawReviewFileReader;
import com.kredx.util.DALConstants;
import com.kredx.util.ReviewsFileWriter;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by sourabhjain on 17/9/16.
 */
public class ReviewsSampleDataGenerator {


    public static void swap(int i, int j, List<Review> reviews){
        Review temp = reviews.get(i);
        reviews.set(i, reviews.get(j));
        reviews.set(j, temp);
    }

    public static List<Review> getSampleData(int size, List<Review> reviews){

        size = Math.min(size, reviews.size());
        Random random = new Random();
        int upperBound = reviews.size();
        for (int i = 0 ; i < size ; i++){
            int index = random.nextInt(upperBound);
            swap(index, upperBound-1, reviews);
            upperBound = upperBound - 1;
        }
        System.out.println(upperBound + " , " + reviews.size());
        return reviews.subList(upperBound, reviews.size());

    }
    public static void main(String[] args) throws IOException {

        List<Review> reviews = RawReviewFileReader.readFile(DALConstants.rawReviewFilePath);
        List<Review> sampleData = getSampleData(DALConstants.sampleDataSize, reviews);
        ReviewsFileWriter.writeReviewsInFile(sampleData, DALConstants.sampleReviewFilePath);

    }

}
