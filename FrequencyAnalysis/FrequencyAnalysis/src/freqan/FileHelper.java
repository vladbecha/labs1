package freqan;

import java.io.*;
import java.util.ArrayList;

/**
 * Класс, позволяющий записать/считать данные в/из txt-файл(а).
 */
public class FileHelper {
    /*Объекты классов, неоходимые для записи и считывания данных*/
    private FileWriter myFileWriter;
    private BufferedWriter myBufferedWriter;
    private FileReader myFileReader;
    private BufferedReader myBufferedReader;

    /**
     * Записать информацию о новом пользователе. Для этого требуются его логин и пароль
     */
    public void writeData(ArrayList<String> stringArray, String filename) {
        try {
            /*Запись будет производиться в файл с указанным именем.
            * Второй параметр указывает на то, что данные будет записываться
            * без удаления предыдущих записей в файле.*/
            myFileWriter = new FileWriter(filename);
            myBufferedWriter = new BufferedWriter(myFileWriter);
            for (String str : stringArray) {
                myBufferedWriter.write(str + "\r\n");
            }
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

    /**
     * Считать информацию, записанную в txt-файле.
     * Считанные данные переводятся в список строк.
     */
    public ArrayList<String> readData(String filename) {
        /*Временная переменная для хранения списка строк*/
        ArrayList<String> tempArray = new ArrayList<String>();
        try {
            /*Запись будет производиться из файла с указанным именем*/
            myFileReader = new FileReader(filename);
            myBufferedReader = new BufferedReader(myFileReader);
            while (true) {
                String line = myBufferedReader.readLine();
                if (line == null) break;
                /*Разбить считанные данные по символу новой строки*/
                String[] parts = line.split("\n");
                /*Записать считанный массив в список данных о пользователях*/
                for (String str : parts) {
                    tempArray.add(str);
                }
            }
            return tempArray;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            /*Сообщение об ошибке*/
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
}

