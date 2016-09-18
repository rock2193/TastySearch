package com.kredx.DataGenerator;

import com.kredx.manager.ReviewManager;
import com.kredx.util.DALConstants;
import com.kredx.util.Util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by sourabhjain on 18/9/16.
 */
public class QueriesSampleDataGenerator {

    private static ReviewManager reviewManager = ReviewManager.getInstance();
    private static Random random = new Random();
    private static int linesToFlush = 0;

    public static List<String> selectNRandomTokens(List<String> tokens, int cnt){

        List<String> randomTokens = new ArrayList<String>();
        for (int i=0  ; i< cnt ; i++){
            if (tokens.size() == 0) break;
            int ind = random.nextInt(tokens.size());
            randomTokens.add(tokens.get(ind));
            tokens.remove(ind);
        }
        return randomTokens;
    }

    public static void writeTokensInFile(PrintWriter writer, List<String> tokens){
        for (String token : tokens){
            writer.write(token + " ");
        }
        writer.write("\n");
        if (linesToFlush == 1000){
            writer.flush(); linesToFlush = 0;
        }
        linesToFlush = linesToFlush + 1;
    }

    public static void main(String[] args) throws IOException {

        PrintWriter writer = new PrintWriter(new FileWriter(DALConstants.queriesFilePath));
        Set<Integer> ids = reviewManager.getAllIds();
        for (Integer id : ids){
            ArrayList<String> tokens = new ArrayList<String>(Util.getTokens(reviewManager.getReview(id)));
            List<String> randomTokens = selectNRandomTokens(tokens, random.nextInt(10) + 1);
            writeTokensInFile(writer, randomTokens);
        }
        writer.close();

    }
}
