package auth;

import java.io.*;
import java.util.ArrayList;

/**
 * Класс, позволяющий записать/считать данные о пользователях в/из txt-файл(а).
 * При создании объекта данного класса, на основе имеющегося txt-файла формируется
 * список пользователей с информацией о них - users.
 */
public class FileHelper {
   
    private static final String FILENAME = "users.txt";
   
    private static final int USER_INFO_SIZE = 3;
   
    private static final int LOGIN_NUMBER = 0;
    
    private static final int SALT_NUMBER = 1;
   
    private static final int HASH_NUMBER = 2;
   
    private FileWriter myFileWriter;
    private BufferedWriter myBufferedWriter;
    private FileReader myFileReader;
    private BufferedReader myBufferedReader;
   
    private ArrayList<String> userStrings;
    
    private ArrayList<User> users;

    /**
     * Конструктор класса, который вызывается при создании объекта класса
     */
    public FileHelper() {
       
        userStrings = new ArrayList<>();
       
        userStrings = readUser();
        if (userStrings != null) {
           
            users = new ArrayList<>();
          
            User currentUser = new User();
           
            for (int i = 0; i < userStrings.size(); i++) {
               
                if ((i + USER_INFO_SIZE) % USER_INFO_SIZE == LOGIN_NUMBER) {
                    currentUser = new User();
                    currentUser.setLogin(userStrings.get(i));
                   
                } else if ((i + USER_INFO_SIZE) % USER_INFO_SIZE == SALT_NUMBER) {
                    currentUser.setSalt(userStrings.get(i));
                   
                } else if ((i + USER_INFO_SIZE) % USER_INFO_SIZE == HASH_NUMBER) {
                    currentUser.setHash(userStrings.get(i));
                    users.add(currentUser);
                }
            }
        }
    }

   
    public void writeUser(String login, String password) {
        try {
           
            myFileWriter = new FileWriter(FILENAME, true);
            myBufferedWriter = new BufferedWriter(myFileWriter);
          
            String salt = Generator.generateSalt();
          
            String hash = Generator.generateHash(password, salt);
           
            myBufferedWriter.write(login + "\n");
            myBufferedWriter.write(salt + "\n");
            myBufferedWriter.write(hash + "\n");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                myBufferedWriter.flush();
                myBufferedWriter.close();
                myFileWriter.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

   
    private ArrayList<String> readUser() {
       
        ArrayList<String> tempArray = new ArrayList<String>();
        try {
           
            myFileReader = new FileReader(FILENAME);
            myBufferedReader = new BufferedReader(myFileReader);
            while (true) {
                String line = myBufferedReader.readLine();
                if (line == null) break;
               
                String[] parts = line.split("\n");
                
                for (String str : parts) {
                    tempArray.add(str);
                }
            }
            return tempArray;
        } catch (IOException ioe) {
            ioe.printStackTrace();
           
        } finally {
            try {
                myBufferedReader.close();
                myFileReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

   
    public ArrayList<User> getUsers() {
        return users;
    }
}
