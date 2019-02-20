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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.simple.JSONArray;

public class Answer {

    /**
     * Default constructor
     */
    public Answer() {
    }

    Student objStudent;
    Question objQuestion;
    Quiz objQuiz;

    public void SvaeQuizAnswer(String StidentID, String StudentName, Object Answers, String QuestionID) {

        Gson topic_Gson = new GsonBuilder().create();
        String topic_jsonFromPojo = topic_Gson.toJson(Answers);
        ArrayList< String> topic_list = new ArrayList<>();
        topic_list.add(topic_jsonFromPojo);
        String[] Answers_Array = utility.GetStringArray(topic_list);

        ArrayList< String> arrayList = new ArrayList();
        arrayList.add("answer:" + Arrays.toString(Answers_Array));
        arrayList.add("stdID:" + StidentID);
        arrayList.add("stdName:" + StudentName);
        arrayList.add(QuestionID);

        if (!"".equals(QuestionID)) {
            curd Create = new curd();
            Create.SaveAnswer(arrayList, StidentID);
        }
    }

}
