package auth;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Авторизация пользователя
 */
public class Authorization {
   
    private FileHelper myFileHelper;
    ArrayList<User> users;
    private String userLogin;
    private String userPassword;

    /**
     * Конструктор класса, который запускается при событии "Авторизация"
     */
    public Authorization() {
        myFileHelper = new FileHelper();
  
        users = myFileHelper.getUsers();
        System.out.println("Enter login, please: ");
     
        String userLogin = readStringData();
   
        System.out.println("Enter password, please: ");
     
        String userPassword = readStringData();
     
        i
            boolean successfully = false;
          
            for (User user : users) {
               
                if (user.getLogin().equals(userLogin)) {
                    if (Generator.generateHash(userPassword, user.getSalt()).equals(user.getHash())) {

                        successfully = true;
                    }
                }
            }
          
            if (successfully) {
                System.out.println("Congratulations! Authorization succeed");
            } else {
               
                System.out.println("Sorry, login or password incorrect.");
            }
        } else {
            
            System.out.println("Sorry! No signed up users!");
        }
      
        new StartingPoint();
    }

  
    private String readStringData() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
}
