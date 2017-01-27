package diffiersa;

import java.util.Scanner;


public class StartingPoint {
   
    public StartingPoint() {
       
        System.out.println("0 - Diffie-Hellman\n" + "1 - RSA");
        
        int userAnswer = readIntData();
      
        if (userAnswer == 0) {
            new DiffieHellman();
        } else if (userAnswer == 1) {
            new RSA();
        } else {
           
            System.out.println("Bad input");
        }
    }

  
    private int readIntData() {
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }

}