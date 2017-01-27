package auth;

import java.util.Scanner;

/**
 * Регистрация пользователя
 */
public class Registration {
   
    private FileHelper myFileHelper;
   
    private String userLogin;
   
    private String userPassword;

  
    public Registration() {
      
        myFileHelper = new FileHelper();
      
        System.out.println("Enter login, please: ");
      
        String userAnswer = readStringData();
      
        if (!checkLogin(userAnswer)) {
           
            userLogin = userAnswer;
           
            System.out.println("Enter password, please: ");
           
            userPassword = readStringData();
            System.out.println("Repeat password, please: ");
            
            if (userPassword.equals(readStringData())) {
               
                myFileHelper.writeUser(userLogin, userPassword);
                System.out.println("Thank you! I have successfully signed up!");
            } else {
                
                System.out.println("Ooops! Passwords do not match!");
            }
        } else {
           
            System.out.println("Sorry, this login is not available.");
        }
      
        new StartingPoint();
    }

   
    private String readStringData() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    private boolean checkLogin(String login) {
       
        for (User user : myFileHelper.getUsers()) {
           
            if (user.getLogin().equals(login)) {
                return true;
            }
        }
       
        return false;
    }
}
