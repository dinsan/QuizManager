/* 
**********************
Authors: Loord Dinsan
Company: EPITA
Date   : 12/02/2019
**********************
 */
package epita.quiz;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MCQChoice {

    utility _utility = new utility();

    /**
     * Default constructor
     */
    public MCQChoice() {
    }

    private String choice;
    private boolean valid;

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String checkTheResult(String StudentID) {

         int correctResultCount=0;
        
        //get student answer
        String stringToParse = null;
        ArrayList< String> list = new ArrayList<>();
        try {

            JSONParser parser = new JSONParser();
            
             String filename = StudentID + "answer";
             
            Object object = parser.parse(new FileReader(_utility.GetPathNmae(filename)));

            JSONArray jsonArray = (JSONArray) object;
            int len = jsonArray.size();
            if (!jsonArray.isEmpty()) {
                for (int i = 0; i < len; i++) {
                    list.add(jsonArray.get(i).toString());
                }
            }

           
            
            for (int i = 0; i < list.size(); i++) {

                stringToParse = list.get(i);

                stringToParse = stringToParse.replace("]", "");
                stringToParse = stringToParse.replace("[", "");

                String[] AnswerAray = stringToParse.split(",");

                
              

                    //get correct answer
                    curd read = new curd();
                    JSONArray result = read.readByQuestionID(AnswerAray[3].replace("\"", ""));

                    if (!result.isEmpty()) {
                        for (int r = 0; r < result.size(); r++) {

                            String restString = result.get(r).toString();

                            restString = restString.replaceAll("(?<!\\\\)\\\\(?!\\\\)", "");
                            restString = restString.replace("[", "");
                            restString = restString.replace("]", "");
                            restString = restString.replace("\"", "");

                            //Student Answerd 
                            String slectdAnswer = AnswerAray[0].replace("\"", "");
                            slectdAnswer = slectdAnswer.replaceAll("(?<!\\\\)\\\\(?!\\\\)", "").split(":")[1].toString();
                       
                            if(slectdAnswer.endsWith(restString))
                            {
                                 correctResultCount++;
                            }
                        }
                    }
                    System.out.print(result);
                
            }

        } catch (IOException | ParseException e) {
            System.out.print(e.getMessage());
        }

        //compare the answer
        String resultOfTest = correctResultCount+" out of " + list.size();

        return resultOfTest;

    }

}
