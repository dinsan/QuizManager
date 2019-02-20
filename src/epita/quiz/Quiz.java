/* 
**********************
Authors: Loord Dinsan
Company: EPITA
Date   : 12/02/2019
**********************
 */
package epita.quiz;

public class Quiz {

    /**
     * Default constructor
     */
    public Quiz() {
    }

    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public String SearchResult(String topic)
    {
       curd sarch = new curd();
       return sarch.readByTpoic(topic);
    }
    
    
    //save data to text file
    public void exportMcq() {

        curd exportToTextFile = new curd();

        exportToTextFile.ExportToTextFile("MCQ questions", exportToTextFile.read("mcqQuestion"));
    }

}
