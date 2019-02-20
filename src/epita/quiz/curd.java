/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epita.quiz;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author dinsan
 */
public class curd implements curdOperation {

    utility _utility = new utility();

    @Override
    public void create(String FileName, MCQQuestion mcq) {

        try {

            Gson gsonBuilder = new GsonBuilder().create();
            String jsonFromPojo = gsonBuilder.toJson(mcq);

            if (!_utility.checkFileIsExting(_utility.GetPathNmae(FileName))) {
                SaveJsonToFile(_utility.GetPathNmae(FileName), "[" + jsonFromPojo + "]");

            } else {
                AddextingJsonData(_utility.GetPathNmae(FileName), mcq);
            }

            System.out.print("successfully created");
        } catch (Exception e) {
            System.out.println("error on saving data to json" + e.getMessage());
        }

    }

    @Override
    public void update(String FileName, MCQQuestion mcq) {
        try {
            JSONParser parser = new JSONParser();
            Object object = parser.parse(new FileReader(_utility.GetPathNmae(FileName)));

            ArrayList< String> list = new ArrayList<>();
            JSONArray jsonArray = (JSONArray) object;
            int len = jsonArray.size();
            if (!jsonArray.isEmpty()) {
                for (int i = 0; i < len; i++) {
                    list.add(jsonArray.get(i).toString());
                }
            }

            for (int i = 0; i < list.size(); i++) {

                String stringToParse = list.get(i);
                JSONObject jsonObject = (JSONObject) parser.parse(stringToParse);

                Gson updateBuilder = new GsonBuilder().create();
                String updateString = updateBuilder.toJson(mcq);
                JSONObject updatejsonObj = (JSONObject) parser.parse(updateString);

                boolean containsValue = jsonObject.containsValue((String) updatejsonObj.get("ID"));

                if (containsValue) {

                    JSONObject xjson = (JSONObject) parser.parse(stringToParse);
                    xjson.put("difficulty", (String) updatejsonObj.get("difficulty"));
                    xjson.put("question", (String) updatejsonObj.get("question"));
                    xjson.put("answer", (JSONArray) updatejsonObj.get("answer"));
                    xjson.put("topics", (JSONArray) updatejsonObj.get("topics"));
                    xjson.put("possAnswer", (JSONArray) updatejsonObj.get("possAnswer"));

                    Gson gsonBuilder = new GsonBuilder().create();
                    String jsonFromPojo = gsonBuilder.toJson(xjson);

                    String AfterRemove = delete((String) updatejsonObj.get("ID"), FileName).toString();

                    String Removedobj = _utility.RemoveFandLstring(AfterRemove);
                    System.out.print(Removedobj);

                    String k = "[" + jsonFromPojo + "," + Removedobj + "]";

                    SaveJsonToFile(_utility.GetPathNmae(FileName), k);

                    System.out.print("successfully updated");
                }

            }

        } catch (IOException | ParseException e) {
            System.out.print(e.getMessage());
        }
    }

    @Override
    public ArrayList< String> read(String FileName) {

        ArrayList< String> list = new ArrayList<>();
        try {

            JSONParser parser = new JSONParser();
            Object object = parser.parse(new FileReader(_utility.GetPathNmae(FileName)));

            JSONArray jsonArray = (JSONArray) object;
            int len = jsonArray.size();
            if (!jsonArray.isEmpty()) {
                for (int i = 0; i < len; i++) {
                    list.add(jsonArray.get(i).toString());
                }
            }

        } catch (IOException | ParseException e) {
            System.out.print(e.getMessage());
        }

        return list;
    }

    public String readByTpoic(String SearchTopic) {

        String stringToParse = null;
        ArrayList< String> list = new ArrayList<>();
        try {

            JSONParser parser = new JSONParser();
            Object object = parser.parse(new FileReader(_utility.GetPathNmae("mcqQuestion")));

            JSONArray jsonArray = (JSONArray) object;
            int len = jsonArray.size();
            if (!jsonArray.isEmpty()) {
                for (int i = 0; i < len; i++) {
                    list.add(jsonArray.get(i).toString());
                }
            }

            for (int i = 0; i < list.size(); i++) {

                stringToParse = list.get(i);
                JSONObject jsonObject = (JSONObject) parser.parse(stringToParse);

                JSONArray topicArray = (JSONArray) jsonObject.get("topics");

                if (topicArray != null && topicArray.size() > 0) {

                    for (int t = 0; t < topicArray.size(); t++) {

                        String StringpoAnswer = topicArray.get(0).toString().replace("[", "");
                        StringpoAnswer = StringpoAnswer.replace("]", "");

                        String[] nameArray = {StringpoAnswer};
                        for (String name : nameArray) {

                            String[] PossanAray = name.split(",");
                            for (String topicName : PossanAray) {

                                topicName = topicName.replace("\"", "");

                                if (SearchTopic.equals(topicName)) {
                                    return stringToParse;
                                }
                            }
                        }

                    }

                }

            }

        } catch (IOException | ParseException e) {
            System.out.print(e.getMessage());
        }

        return null;
    }

    public JSONArray readByQuestionID(String SearchingQuestionID) {

        String stringToParse = null;
        ArrayList< String> list = new ArrayList<>();
        try {

            JSONParser parser = new JSONParser();
            Object object = parser.parse(new FileReader(_utility.GetPathNmae("mcqQuestion")));

            JSONArray jsonArray = (JSONArray) object;
            int len = jsonArray.size();
            if (!jsonArray.isEmpty()) {
                for (int i = 0; i < len; i++) {
                    list.add(jsonArray.get(i).toString());
                }
            }

            for (int i = 0; i < list.size(); i++) {

                stringToParse = list.get(i);
                JSONObject jsonObject = (JSONObject) parser.parse(stringToParse);

                JSONArray answerArray = (JSONArray) jsonObject.get("answer");
                String QustionID = (String) jsonObject.get("ID");

                if (SearchingQuestionID.equals(QustionID)) {
                    return answerArray;
                }

            }

        } catch (IOException | ParseException e) {
            System.out.print(e.getMessage());
        }

        return null;
    }

    public JSONArray readAnswerByStudentID(String SearchingQuestionID) {

        return null;
    }

    @Override
    public ArrayList<String> delete(String ID, String FileName) {

        ArrayList< String> list = new ArrayList<>();
        try {

            JSONParser parser = new JSONParser();
            Object object = parser.parse(new FileReader(_utility.GetPathNmae(FileName)));

            JSONArray jsonArray = (JSONArray) object;
            int len = jsonArray.size();
            if (!jsonArray.isEmpty()) {
                for (int i = 0; i < len; i++) {
                    list.add(jsonArray.get(i).toString());
                }
            }

            for (int i = 0; i < list.size(); i++) {

                String stringToParse = list.get(i);
                JSONObject jsonObject = (JSONObject) parser.parse(stringToParse);

                boolean containsValue = jsonObject.containsValue(ID);

                if (containsValue) {
                    System.out.println(i + ":" + ID);

                    list.remove(i);
                    SaveJsonToFile(_utility.GetPathNmae(FileName), list.toString());
                    System.out.print("successfully deleted");
                }

            }

        } catch (IOException | ParseException e) {
            System.out.print(e.getMessage());
        }

        return list;
    }

    private void SaveJsonToFile(String filePath, String j_object) {

        try {
            FileWriter file = new FileWriter(filePath);
            file.write(j_object);
            file.flush();
        } catch (IOException e) {
            System.out.println("error on saving data to json" + e.getMessage());
        }

    }

    private void AddextingJsonData(String filePath, Object mcq) {

        try {

            //Data from file
            JSONParser parser = new JSONParser();
            Object object = parser.parse(new FileReader(filePath));
            String Removedobj = _utility.RemoveFandLstring(object.toString());

            Gson gsonBuilder = new GsonBuilder().create();
            String jsonFromPojo = gsonBuilder.toJson(mcq);

            String k = "[" + Removedobj + "," + jsonFromPojo + "]";

            System.out.println(jsonFromPojo);

            SaveJsonToFile(filePath, k);

        } catch (IOException | ParseException e) {
            System.out.print(e.getMessage());
        }
    }

    public void SaveAnswer(ArrayList answer, String StudentID) {
        try {

            Gson gsonBuilder = new GsonBuilder().create();
            String jsonFromPojo = gsonBuilder.toJson(answer);

            StudentID = StudentID + "answer";
            if (!_utility.checkFileIsExting(_utility.GetPathNmae(StudentID))) {

                SaveJsonToFile(_utility.GetPathNmae(StudentID), "[" + jsonFromPojo + "]");

            } else {
                AddextingJsonData(_utility.GetPathNmae(StudentID), answer);
            }

            System.out.print("successfully created");
        } catch (Exception e) {
            System.out.println("error on saving data to json" + e.getMessage());
        }
    }

    public void ExportToTextFile(String FileName, ArrayList<String> DataforTextFile) {
        PrintWriter writer;
        try {

            try {
                writer = new PrintWriter(_utility.GetPathNmaeTextFile(FileName), "UTF-8");
                writer.println("MCQ Question");
                for (int i = 0; i < DataforTextFile.size(); i++) {

                    String stringToParse = DataforTextFile.get(i);

                    JSONParser parser = new JSONParser();
                    try {
                        JSONObject jsonObject = (JSONObject) parser.parse(stringToParse);

                        //MCQQuestion person = new MCQQuestion();
                        // person.setQuestion((String) jsonObject.get("question"));
                        int qno = i + 1;
                        writer.println();
                        writer.println("Question) " + qno);
                        writer.println("---------------------");
                        writer.println("Question: " + (String) jsonObject.get("question"));
                        writer.println("Difficulty: " + (String) jsonObject.get("difficulty"));
                        writer.println("ID: " + (String) jsonObject.get("ID"));
                        writer.println("---------------------");

                    } catch (ParseException ex) {
                        Logger.getLogger(curd.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                writer.close();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(utility.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(utility.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
