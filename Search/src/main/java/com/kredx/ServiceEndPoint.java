package com.kredx;

import com.google.gson.Gson;
import com.kredx.bean.OutputBean;
import com.kredx.bean.Strategy;
import com.kredx.util.Util;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by sourabhjain on 18/9/16.
 */
public class ServiceEndPoint extends HttpServlet {

    private static Logger logger = Logger.getLogger(ServiceEndPoint.class);
    private String query;
    private int k;
    private Strategy strategy;


    public String getParam(HttpServletRequest request, String param, String defaultValue){
        try {
            String val = request.getParameter(param);
            if (Util.parseData(val) == null)    return defaultValue;
            return val;
        }catch (Exception e){
            return defaultValue;
        }
    }

    public Strategy parseStrategy(String input){
        if (input.equals("LS")) return Strategy.LowScore;
        else if (input.equals("MR")) return Strategy.MostRecent;
        else return Strategy.HighScore;
    }
    public void getParamsFromRequest(HttpServletRequest request){
        try {
            this.query = getParam(request, "q", "");
            this.k = Integer.parseInt(getParam(request, "k" , "20"));
            this.strategy =   parseStrategy(getParam(request, "str", "HS"));
            logger.info("Input Params, query : " + query + " , k : " + k + " , Strategy : " + strategy);
        }catch (Exception e){
            throw new RuntimeException("Error in Parsing Input Params");
        }

    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        logger.info("In doGet ..");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            long start = System.currentTimeMillis();
            getParamsFromRequest(request);
            OutputBean result = RequestHandler.getInstance().getTopReviews(query, k, strategy);
            logger.info("Processing time : " + (System.currentTimeMillis() - start) + "ms");
            out.write(new Gson().toJson(result));
            logger.info("Total time , including data writing time : " + (System.currentTimeMillis()- start));
        }catch (Exception e){
            logger.error(e.getMessage() , e);
            out.write("Error !! " + e.getMessage());
        }finally {
            if (out != null)    out.close();
        }

    }

}

