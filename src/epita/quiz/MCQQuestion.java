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
import java.util.UUID;

public class MCQQuestion extends Question {

    /**
     * Default constructor
     *
     *
     */
    public MCQQuestion() {
    }

    //care new MCQ 
    public void CreateNewMCQ(String question, Object topics, String diffulty, Object possibleblityOfAnswer, Object correctAnswer) {

        try {

            MCQQuestion mcq = new MCQQuestion();
            String uniqueID = UUID.randomUUID().toString();
            mcq.setID(uniqueID);
            mcq.setDifficulty(diffulty);
            mcq.setQuestion(question);

            //convert objcet to String Array then add to MCQquestin Object
            Gson topic_Gson = new GsonBuilder().create();
            String topic_jsonFromPojo = topic_Gson.toJson(topics);
            ArrayList< String> topic_list = new ArrayList<>();
            topic_list.add(topic_jsonFromPojo);
            String[] topic_Array = utility.GetStringArray(topic_list);
            mcq.possibleblityOfAnswer(topic_Array);

            //convert objcet to String Array then add to MCQquestin Object
            Gson gsa = new GsonBuilder().create();
            String jsonFromPojoa = gsa.toJson(topics);
            ArrayList< String> topicsLists = new ArrayList<>();
            topicsLists.add(jsonFromPojoa);
            String[] Topics = utility.GetStringArray(topicsLists);
            mcq.seTopics(Topics);

            //convert objcet to String Array then add to MCQquestin Object
            Gson possible_Gson = new GsonBuilder().create();
            String possible_jsonFromPojo = possible_Gson.toJson(possibleblityOfAnswer);
            ArrayList< String> possible_Answerlist = new ArrayList<>();
            possible_Answerlist.add(possible_jsonFromPojo);
            String[] possible_AnswerArray = utility.GetStringArray(possible_Answerlist);
            mcq.possibleblityOfAnswer(possible_AnswerArray);

            //convert objcet to String Array then add to MCQquestin Object
            Gson correct_Gson = new GsonBuilder().create();
            String correct_jsonFromPojo = correct_Gson.toJson(correctAnswer);
            ArrayList< String> correct_Answerlist = new ArrayList<>();
            correct_Answerlist.add(correct_jsonFromPojo);
            String[] correct_AnswerArray = utility.GetStringArray(correct_Answerlist);
            mcq.setCorrectAnswer(correct_AnswerArray);

            curd createMCQ = new curd();
            createMCQ.create("mcqQuestion", mcq);

        } catch (Exception e) {
            System.out.println("error on saving data to json" + e.getMessage());
        }

    }

    public ArrayList< String> readMcqQuestions() {
        curd MCQ = new curd();

        ArrayList< String> arr = MCQ.read("mcqQuestion");
        System.out.print(arr);

        return arr;
    }

    public void updateMCQ() {
        curd updateMCQ = new curd();
        updateMCQ.update("c2f597c9-8eef-4288-9aeb-e43f180b97ec", "mcqQuestion");
    }

    public void deleteMCQ() {
        curd deleteMCQ = new curd();
        deleteMCQ.delete("bece6676-734f-4449-b125-355b1c3b2075", "mcqQuestion");
    }
}
