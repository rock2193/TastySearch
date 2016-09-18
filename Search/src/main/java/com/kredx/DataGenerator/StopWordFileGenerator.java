package com.kredx.DataGenerator;

import com.kredx.bean.Review;
import com.kredx.reader.RawReviewFileReader;
import com.kredx.util.DALConstants;
import com.kredx.util.Util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by sourabhjain on 17/9/16.
 */
public class StopWordFileGenerator {


    private static TreeMap<Integer, List<String>> sortByValue(HashMap<String, Integer> map) {

        TreeMap<Integer, List<String>> treeMap = new TreeMap<Integer, List<String>>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -o1.compareTo(o2);
            }
        });
        for (Map.Entry<String , Integer> entry : map.entrySet()){
            if (!treeMap.containsKey(entry.getValue())) treeMap.put(entry.getValue(), new ArrayList<String>());
            treeMap.get(entry.getValue()).add(entry.getKey());
        }
        return treeMap;
    }

    public static HashMap<String, Integer> getTokenDocumentFrequencyMap(List<Review> reviews){

        HashMap<String, Integer> tokenDocumentFrequency = new HashMap<String, Integer>();
        for (Review review : reviews){
            HashSet<String> tokens = Util.getTokens(review);
            for (String token : tokens){
                Integer cnt = tokenDocumentFrequency.get(token);
                if (cnt == null)    cnt = 0;
                tokenDocumentFrequency.put(token, cnt + 1);
            }
        }
        return tokenDocumentFrequency;
    }
    public static void main(String[] args) throws IOException {
        List<Review> reviews = RawReviewFileReader.readFile(DALConstants.rawReviewFilePath);
        TreeMap<Integer, List<String>> sortedTokensOnFrequencyMap = sortByValue(getTokenDocumentFrequencyMap(reviews));
        PrintWriter printWriter = new PrintWriter(new FileWriter(DALConstants.topTokensFilePath));
        int cnt = 0;
        for (Map.Entry<Integer, List<String>>  entry : sortedTokensOnFrequencyMap.entrySet()){
            for (String token : entry.getValue()){
                printWriter.write(token + "\t" + entry.getKey() + "\n");
                cnt = cnt + 1;
                if (cnt > 1000) break;
            }
        }
        printWriter.close();
    }
}
