/* 
**********************
Authors: Loord Dinsan
Company: EPITA
Date   : 12/02/2019
**********************
 */
package epita.quiz;

public abstract class Question {

    /**
     * Default constructor
     */
    public Question() {
    }

    private String ID;
    private String question;
    private String[] topics;
    private String[] possAnswer;
    private String difficulty;
    private String[] answer;

    public void seTopics(String[] topics) {
        this.topics = topics;
    }

    public void possibleblityOfAnswer(String[] possAnswer) {
        this.possAnswer = possAnswer;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setID(String ID) {
        this.ID = ID;

    }

    public void setCorrectAnswer(String[] answer) {
        this.answer = answer;

    }

}
