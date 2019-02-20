/* 
**********************
Authors: Loord Dinsan
Company: EPITA
Date   : 12/02/2019
**********************
*/
package epita.quiz;

public class MCQChoice {

 /**
  * Default constructor
  */
 public MCQChoice() {}

 private String choice;
 private boolean  valid;

 public void setChoice(String choice) {
  this.choice = choice;
 }

 public void setValid(boolean  valid) {
  this.valid = valid;
 }

}