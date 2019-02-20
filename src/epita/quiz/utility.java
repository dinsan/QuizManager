/* 
**********************
@Author: Loord Dinsan
Company: EPITA
Date   : 12/02/2019
**********************
 */
package epita.quiz;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class utility {

    public boolean checkFileIsExting(String pathName) {
        File tmpDir = new File(pathName);
        boolean exists = tmpDir.exists();
        return exists;
    }

    public String RemoveFandLstring(String loginToken) {

        return loginToken.substring(1, loginToken.length() - 1);
    }

    public String GetPathNmae(String FileName) {

        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path filePath = Paths.get(currentPath.toString(), "src\\epita\\quizmanager\\jsonData", FileName + ".json");

        return filePath.toString();
    }
    
     public String GetPathNmaeTextFile(String FileName) {

        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path filePath = Paths.get(currentPath.toString(), "src\\epita\\quizmanager\\jsonData", FileName + ".text");

        return filePath.toString();
    }

    public static String[] GetStringArray(ArrayList<String> arr) {

        // declaration and initialise String Array 
        String str[] = new String[arr.size()];

        // Convert ArrayList to object array 
        Object[] objArr = arr.toArray();

        // Iterating and converting to String 
        int i = 0;
        for (Object obj : objArr) {
            str[i++] = (String) obj;
        }

        return str;
    }

   
}
