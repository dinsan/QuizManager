/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epita.quiz;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author dinsan
 */
public class QuizUI {

    String QuestionTyoe;
    int NextButtonClickCount = -1;
    String QuestionID = "";

    public GridPane createQuizManagerFormPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        // Add Column Constraints
        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }

    public void addUIControlsQuizManger(GridPane gridPane) {
        // Add Header
        Label headerLabel = new Label("Quiz Manager Form");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0, 0, 3, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        ToggleGroup group = new ToggleGroup();

        RadioButton rbMcq = new RadioButton("MCQ Question");
        rbMcq.setUserData("mcq");
        rbMcq.setToggleGroup(group);
        rbMcq.setSelected(true);

        RadioButton rbOpenQ = new RadioButton("Open Question");
        rbOpenQ.setUserData("open");
        rbOpenQ.setToggleGroup(group);

        HBox hbox = new HBox(rbMcq, rbOpenQ);
        gridPane.add(hbox, 0, 0, 2, 1);

        // Add Question 
        Label QuestionLabel = new Label("Question : ");
        gridPane.add(QuestionLabel, 0, 1);

        TextField QuestionField = new TextField();
        QuestionField.setPrefHeight(40);
        gridPane.add(QuestionField, 1, 1);

        // Add topics 
        Label topicslLabel = new Label("Topics List: ");
        gridPane.add(topicslLabel, 0, 2);

        ListView listViewTopics = new ListView();
        listViewTopics.setPrefHeight(100);
        gridPane.add(listViewTopics, 1, 2);

        // Label topicsNamelLabel = new Label("Topic");
        //gridPane.add(topicsNamelLabel, 2, 2);
        TextField TopicField = new TextField();
        Button addTopicBtn = new Button("+");
        gridPane.add(TopicField, 2, 2);
        gridPane.add(addTopicBtn, 3, 2);

        // Add possibilityanswers 
        Label possibilityAnswerLabel = new Label("Choices:");
        gridPane.add(possibilityAnswerLabel, 0, 5);

        ListView listViewChoosingItems = new ListView();
        listViewChoosingItems.setPrefHeight(100);
        gridPane.add(listViewChoosingItems, 1, 5);

        TextField choosingField = new TextField();
        Button addChoosingBtn = new Button("+");
        gridPane.add(choosingField, 2, 5);
        gridPane.add(addChoosingBtn, 3, 5);

        // Add possibilityanswers 
        Label correctAnswerLabel = new Label("Answers:");
        gridPane.add(correctAnswerLabel, 0, 7);

        ListView ListViewCorrectAnswer = new ListView();
        ListViewCorrectAnswer.setPrefHeight(100);
        gridPane.add(ListViewCorrectAnswer, 1, 7);

        ComboBox answerForTheQuestion = new ComboBox();
        answerForTheQuestion.setMaxWidth(200);
        Button answerForBtn = new Button("+");
        gridPane.add(answerForTheQuestion, 2, 7);
        gridPane.add(answerForBtn, 3, 7);

        Label QuesID = new Label();
        QuesID.setVisible(false);

        addTopicBtn.setOnAction((ActionEvent event) -> {
            if (TopicField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a topic");
                return;
            }
            listViewTopics.getItems().add(TopicField.getText());
            TopicField.clear();
        });

        addChoosingBtn.setOnAction((ActionEvent event) -> {
            if (choosingField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a choice");
                return;
            }
            listViewChoosingItems.getItems().add(choosingField.getText());
            answerForTheQuestion.getItems().add(choosingField.getText());
            choosingField.clear();
        });

        answerForBtn.setOnAction((ActionEvent event) -> {
            if (answerForTheQuestion.getSelectionModel().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a answer");
                return;
            }
            ListViewCorrectAnswer.getItems().add(answerForTheQuestion.getSelectionModel().getSelectedItem().toString());

        });

        // Add Password Label
        Label difficultyLabel = new Label("Difficulty : ");
        gridPane.add(difficultyLabel, 0, 3);

        ComboBox difficulty = new ComboBox();
        difficulty.getItems().add("High");
        difficulty.getItems().add("Medium");
        difficulty.getItems().add("Low");

        //HBox difficultybox = new HBox(difficulty);
        difficulty.setPrefHeight(40);
        gridPane.add(difficulty, 1, 3);

        // Add Submit Button
        Button carteQuestionBtn = new Button("CREATE");
        carteQuestionBtn.setPrefHeight(40);
        carteQuestionBtn.setDefaultButton(true);
        carteQuestionBtn.setPrefWidth(100);
        gridPane.add(carteQuestionBtn, 1, 8);
        GridPane.setHalignment(carteQuestionBtn, HPos.LEFT);
        GridPane.setMargin(carteQuestionBtn, new Insets(20, 0, 20, 0));

        //Search by Topic
        Button searchButton = new Button("SEARCH");
        searchButton.setPrefHeight(40);
        searchButton.setDefaultButton(true);
        searchButton.setPrefWidth(100);
        gridPane.add(searchButton, 1, 9);

        Button updateButton = new Button("UPDATE");
        updateButton.setPrefHeight(40);
        updateButton.setDefaultButton(true);
        updateButton.setPrefWidth(100);
        gridPane.add(updateButton, 2, 8);

        Button deleteButton = new Button("DELETE");
        deleteButton.setPrefHeight(40);
        deleteButton.setDefaultButton(true);
        deleteButton.setPrefWidth(100);
        gridPane.add(deleteButton, 3, 8);

        Button evaluationButton = new Button("EVlAUTION");
        evaluationButton.setPrefHeight(40);
        evaluationButton.setDefaultButton(true);
        evaluationButton.setPrefWidth(100);
        gridPane.add(evaluationButton, 2, 9);

        evaluationButton.setOnAction((ActionEvent event) -> {

            showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "Test Result", "Out of 100/80");

        });

        deleteButton.setOnAction((ActionEvent event) -> {

            if (listViewTopics.getItems().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Search and delete");

            } else {
                MCQQuestion curd = new MCQQuestion();
                curd.deleteMCQ(QuesID.getText());

                QuestionField.clear();
                choosingField.clear();
                ListViewCorrectAnswer.getItems().clear();
                listViewTopics.getItems().clear();
                listViewChoosingItems.getItems().clear();

                showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Question delete Successful!", "Question is " + QuestionField.getText());

            }

        });

        updateButton.setOnAction((ActionEvent event) -> {
            if (listViewTopics.getItems().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Search and update");

            } else {
                MCQQuestion update = new MCQQuestion();
                update.updateMCQ(QuesID.getText(),
                        QuestionField.getText(),
                        listViewTopics.getItems().toArray(),
                        difficulty.getSelectionModel().getSelectedItem().toString(),
                        listViewChoosingItems.getItems().toArray(),
                        ListViewCorrectAnswer.getItems().toArray()
                );

                QuestionField.clear();
                choosingField.clear();
                ListViewCorrectAnswer.getItems().clear();
                listViewTopics.getItems().clear();
                listViewChoosingItems.getItems().clear();
                answerForTheQuestion.getItems().clear();

                showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Question update Successful!", "Question is " + QuestionField.getText());

            }

        });

        searchButton.setOnAction((ActionEvent event) -> {

            QuestionField.clear();
            choosingField.clear();
            ListViewCorrectAnswer.getItems().clear();
            listViewTopics.getItems().clear();
            listViewChoosingItems.getItems().clear();

            if (TopicField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a topics to search");

            } else {
                Quiz search = new Quiz();
                String resutl = search.SearchResult(TopicField.getText());

                try {
                    JSONParser parser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) parser.parse(resutl);
                    QuestionField.setText((String) jsonObject.get("question"));
                    difficulty.getSelectionModel().select((String) jsonObject.get("difficulty"));
                    QuesID.setText((String) jsonObject.get("ID"));
                    //feth possible anwser
                    JSONArray possAnswerJarray = (JSONArray) jsonObject.get("possAnswer");
                    if (possAnswerJarray != null && possAnswerJarray.size() > 0) {

                        for (int i = 0; i < possAnswerJarray.size(); i++) {

                            String jsonString = possAnswerJarray.get(0).toString().replace("[", "");
                            jsonString = jsonString.replace("]", "");
                            String[] nameArray = {jsonString};

                            for (String name : nameArray) {

                                String[] StringAray = name.split(",");

                                for (String Item : StringAray) {

                                    Item = Item.replace("\"", "");
                                    listViewChoosingItems.getItems().add(Item);
                                }
                            }
                        }
                    }

                    //feth topics
                    JSONArray topicsJarray = (JSONArray) jsonObject.get("topics");
                    if (topicsJarray != null && topicsJarray.size() > 0) {

                        for (int i = 0; i < topicsJarray.size(); i++) {

                            String jsonString = topicsJarray.get(0).toString().replace("[", "");
                            jsonString = jsonString.replace("]", "");
                            String[] nameArray = {jsonString};

                            for (String name : nameArray) {

                                String[] StringAray = name.split(",");

                                for (String Item : StringAray) {

                                    Item = Item.replace("\"", "");
                                    listViewTopics.getItems().add(Item);
                                }
                            }
                        }
                    }

                    //feth topics
                    JSONArray answerJarray = (JSONArray) jsonObject.get("answer");
                    if (answerJarray != null && answerJarray.size() > 0) {

                        for (int i = 0; i < answerJarray.size(); i++) {

                            String jsonString = answerJarray.get(0).toString().replace("[", "");
                            jsonString = jsonString.replace("]", "");
                            String[] nameArray = {jsonString};

                            for (String name : nameArray) {

                                String[] StringAray = name.split(",");

                                for (String Item : StringAray) {

                                    Item = Item.replace("\"", "");
                                    ListViewCorrectAnswer.getItems().add(Item);
                                }
                            }
                        }
                    }

                } catch (ParseException ex) {
                    Logger.getLogger(QuizUI.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.print(resutl);
            }

        });

        group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (group.getSelectedToggle() != null) {
                System.out.println(group.getSelectedToggle().getUserData().toString());

                if ("open".equals(group.getSelectedToggle().getUserData().toString())) {
                    TopicField.setDisable(true);
                    listViewTopics.setDisable(true);
                    addTopicBtn.setDisable(true);
                    difficulty.setDisable(true);
                    listViewChoosingItems.setDisable(true);
                    choosingField.setDisable(true);
                    addChoosingBtn.setDisable(true);
                    QuestionTyoe = "open";
                } else {
                    TopicField.setDisable(false);
                    TopicField.setDisable(false);
                    listViewTopics.setDisable(false);
                    addTopicBtn.setDisable(false);
                    difficulty.setDisable(false);
                    listViewChoosingItems.setDisable(false);
                    choosingField.setDisable(false);
                    addChoosingBtn.setDisable(false);
                    QuestionTyoe = "mcq";
                }

            }
        });

        Button qizWinOpenButton = new Button("Open Test");
        qizWinOpenButton.setPrefHeight(40);
        qizWinOpenButton.setDefaultButton(true);
        qizWinOpenButton.setPrefWidth(100);
        gridPane.add(qizWinOpenButton, 2, 11);

        Button ExportButton = new Button("Export");
        ExportButton.setPrefHeight(40);
        ExportButton.setDefaultButton(true);
        ExportButton.setPrefWidth(100);
        gridPane.add(ExportButton, 3, 11);

        ExportButton.setOnAction((ActionEvent event) -> {
            Quiz export = new Quiz();
            export.exportMcq();
        });

        carteQuestionBtn.setOnAction((ActionEvent event) -> {
            if ("mcq".equals(QuestionTyoe) || QuestionTyoe == null) {

                if (QuestionField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter Question");
                    return;
                }
                if (listViewTopics.getItems().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter some topics");
                    return;
                }
                if (difficulty.getSelectionModel().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please select a difficulty mod");
                    return;
                }

                if (listViewChoosingItems.getItems().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter some choices");
                    return;
                }

                if (ListViewCorrectAnswer.getItems().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter correct answers for this question");
                    return;
                }

                MCQQuestion mcq = new MCQQuestion();
                mcq.CreateNewMCQ(QuestionField.getText(),
                        listViewTopics.getItems().toArray(),
                        difficulty.getSelectionModel().getSelectedItem().toString(),
                        listViewChoosingItems.getItems().toArray(),
                        ListViewCorrectAnswer.getItems().toArray()
                );

                showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Question crated Successful!", "Question is " + QuestionField.getText());

            } else {
                if (QuestionField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter Question");
                    return;
                }

                if (listViewChoosingItems.getItems().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter some answers");
                    return;
                }
                showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Question crated Successful!", "Question is " + QuestionField.getText());
            }

            QuestionField.clear();
            choosingField.clear();
            ListViewCorrectAnswer.getItems().clear();
            listViewTopics.getItems().clear();
            listViewChoosingItems.getItems().clear();
            answerForTheQuestion.getItems().clear();
            System.out.print(event);
        });

        qizWinOpenButton.setOnAction((ActionEvent event) -> {
            Stage psrimaryStage = new Stage();
            QuizUI UI = new QuizUI();
            psrimaryStage.setTitle("Quiz");
            // Create the registration form QustionAndAnswergrid pane
            GridPane gridPane1 = UI.createQuizManagerFormPane();
            // Add UI controls to the registration form QustionAndAnswergrid pane
            UI.addUIControlsQuiz(gridPane1);
            // Create a scene with registration form QustionAndAnswergrid pane as the root node
            Scene scene = new Scene(gridPane1, 800, 600);
            // Set the scene in primary stage
            psrimaryStage.setScene(scene);
            psrimaryStage.setMaximized(true);
            psrimaryStage.show();

            System.out.print(event);
        });

    }

    public void addUIControlsQuiz(GridPane gridPaneMCQquiz) {

        QuestionID = "";
        Label stID = new Label("Student ID: ");
        gridPaneMCQquiz.add(stID, 0, 0);
        TextField TextFieldStudentID = new TextField();
        TextFieldStudentID.setMaxWidth(400);
        TextFieldStudentID.setPrefHeight(40);
        gridPaneMCQquiz.add(TextFieldStudentID, 1, 0);

        Label stName = new Label("Student Name: ");
        gridPaneMCQquiz.add(stName, 0, 2);
        TextField TextFieldName = new TextField();
        TextFieldName.setMaxWidth(400);
        TextFieldName.setPrefHeight(40);
        gridPaneMCQquiz.add(TextFieldName, 1, 2);

        Button submitButton = new Button("Start the Test");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPaneMCQquiz.add(submitButton, 1, 3);
        GridPane.setHalignment(submitButton, HPos.LEFT);
        GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        // Add possibilityanswers 
        Label correctAnswerLabel = new Label("Choos a number then add:");
        gridPaneMCQquiz.add(correctAnswerLabel, 1, 4);
        correctAnswerLabel.setVisible(false);

        ComboBox studentAnswer = new ComboBox();
        studentAnswer.setVisible(false);
        studentAnswer.setMaxWidth(200);
        gridPaneMCQquiz.add(studentAnswer, 2, 4);

        Button CaddAnswerBtn = new Button("+");
        gridPaneMCQquiz.add(CaddAnswerBtn, 3, 4);
        CaddAnswerBtn.setVisible(false);

        ListView ListViewAnswer = new ListView();
        ListViewAnswer.setPrefHeight(80);
        gridPaneMCQquiz.add(ListViewAnswer, 1, 5);
        ListViewAnswer.setVisible(false);

        TextFieldStudentID.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                TextFieldStudentID.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        TextFieldName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                TextFieldName.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        CaddAnswerBtn.setOnAction((ActionEvent event) -> {
            if (studentAnswer.getSelectionModel().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gridPaneMCQquiz.getScene().getWindow(), "Form Error!", "Please selct  answer");
                return;
            }
            ListViewAnswer.getItems().add(studentAnswer.getSelectionModel().getSelectedItem().toString());

        });

        if (TextFieldName.getText() != null && TextFieldStudentID.getText() != null) {

            submitButton.setOnAction((ActionEvent event) -> {

                if ("End".equals(submitButton.getText())) {
                    return;
                }

                if (ListViewAnswer.getItems().isEmpty() && "Next".equals(submitButton.getText())) {
                    showAlert(Alert.AlertType.ERROR, gridPaneMCQquiz.getScene().getWindow(), "Form Error!", "Please select a  answer");
                    return;
                }

                if (!TextFieldName.getText().isEmpty() && !TextFieldStudentID.getText().isEmpty()) {

                    stID.setVisible(false);
                    stName.setVisible(false);
                    TextFieldStudentID.setVisible(false);
                    TextFieldName.setVisible(false);

                    correctAnswerLabel.setVisible(true);
                    studentAnswer.setVisible(true);
                    ListViewAnswer.setVisible(true);
                    CaddAnswerBtn.setVisible(true);

                    JSONParser parser = new JSONParser();
                    submitButton.setText("Next");

                    MCQQuestion mcq = new MCQQuestion();
                    ArrayList< String> arrayList = mcq.readMcqQuestions();

                    if (!arrayList.isEmpty()) {

                        NextButtonClickCount++;

                        int totalMCQquestion = arrayList.size();

                        if (totalMCQquestion > NextButtonClickCount) {
                            String MCQQestionJsonString = arrayList.get(NextButtonClickCount);

                            try {
                                GridPane QustionAndAnswergrid = new GridPane();

                                Object objecst = parser.parse(MCQQestionJsonString);
                                JSONObject jo = (JSONObject) objecst;
                                String question = (String) jo.get("question");
                                JSONArray possAnswer = (JSONArray) jo.get("possAnswer");

                                //for submt answer
                                if (NextButtonClickCount > 0) {
                                    int inex = NextButtonClickCount - 1;
                                    String QusID = arrayList.get(inex);
                                    Object Qobjecst = parser.parse(QusID);
                                    JSONObject Qjo = (JSONObject) Qobjecst;
                                    QuestionID = (String) Qjo.get("ID");
                                }
                                //add question
                                int sizOfArr = QustionAndAnswergrid.getChildren().size();

                                QustionAndAnswergrid.add(new Label(question), 0, 1);

                                if (possAnswer != null && possAnswer.size() > 0) {

                                    for (int i = 0; i < possAnswer.size(); i++) {

                                        String StringpoAnswer = possAnswer.get(0).toString().replace("[", "");
                                        StringpoAnswer = StringpoAnswer.replace("]", "");

                                        String[] nameArray = {StringpoAnswer};
                                        for (String name : nameArray) {

                                            String[] PossanAray = name.split(",");
                                            studentAnswer.getItems().clear();
                                            for (String posanswer : PossanAray) {

                                                sizOfArr++;
                                                String choos = posanswer.replace("\"", "");
                                                String NameMcq = sizOfArr + " ) " + choos;
                                                QustionAndAnswergrid.add(new Label(NameMcq), 1, sizOfArr);

                                                studentAnswer.getItems().add(choos);

                                            }
                                        }

                                    }

                                    int size = gridPaneMCQquiz.getChildren().size();

                                    if (size == 10) {
                                        gridPaneMCQquiz.getChildren().remove(9);
                                    }

                                    gridPaneMCQquiz.add(QustionAndAnswergrid, 1, 10);

                                    //save the answer here                                
                                    //JSONArray psanswer = (JSONArray) jo.get("ID");
                                    Answer answer = new Answer();
                                    answer.SvaeQuizAnswer(TextFieldStudentID.getText(), TextFieldName.getText(), ListViewAnswer.getItems().toArray(), QuestionID);

                                    ListViewAnswer.getItems().clear();
                                }

                            } catch (ParseException ex) {
                                Logger.getLogger(QuizUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {

                            correctAnswerLabel.setVisible(false);
                            studentAnswer.setVisible(false);
                            ListViewAnswer.setVisible(false);
                            CaddAnswerBtn.setVisible(false);

                            gridPaneMCQquiz.getChildren().remove(9);

                            GridPane.setHalignment(submitButton, HPos.CENTER);

                            //save last answer
                            Answer answer = new Answer();
                            answer.SvaeQuizAnswer(TextFieldStudentID.getText(), TextFieldName.getText(), ListViewAnswer.getItems().toArray(), QuestionID);

                            showAlert(Alert.AlertType.INFORMATION, gridPaneMCQquiz.getScene().getWindow(), "Info", "The test is End");
                            submitButton.setText("SUBMIT");
                            submitButton.setText("End");

                        }

                    }

                    System.out.print(event);

                } else {
                    showAlert(Alert.AlertType.ERROR, gridPaneMCQquiz.getScene().getWindow(), "Form Error!", "Please enter Stdent ID & Name to statr the Test");
                }

            });

        }
    }

    public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
