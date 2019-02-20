/* 
**********************
Authors: Loord Dinsan
Company: EPITA
Date   : 12/02/2019
**********************
 */
package epita.quiz;

import java.util.ArrayList;

/**
 *
 * @author dinsan
 */
public interface curdOperation {

    public void create(String PathName, MCQQuestion JsonObj);

    public void update(String ID, String PathName);

    public ArrayList< String> read(String PathName);

    public ArrayList< String> delete(String ID, String filePath);
}
