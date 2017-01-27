package auth;

import java.util.Scanner;

/**
 * Начальный интерфейс
 */
public class StartingPoint {
   
    public StartingPoint() {
       
        System.out.println("0 - Registration\n" + "1 - Authorization");
      
        int userAnswer = readIntData();
       
        if (userAnswer == 0) {
            new Registration();
        } else if (userAnswer == 1) {
            new Authorization();
        } else {
           
            System.out.println("Bad input");
        }
    }

   
    private int readIntData() {
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }

}
