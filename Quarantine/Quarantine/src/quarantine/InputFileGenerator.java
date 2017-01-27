package quarantine;

import java.io.*;
import java.util.*;

public class InputFileGenerator {

    /*Границы, в которые должен попадать int случайного символа*/
    private static final int MAX = 126;
    private static final int MIN = 32;
    /*Предельная длина одной строки файла*/
    private static final int STRING_MAX_LENGTH = 1000;
    /*Объекты классов для записи в файл*/
    private FileWriter myFileWriter;
    private BufferedWriter myBufferedWriter;
    /*Количество строк в файле*/
    private int numberOfStrings;
    /*Имя файла, в который будут записаны генерируемые строки*/
    private String fileName;
    /*Имя выходного файла, который следует очистить перед началом работы*/
    private String outputFile;

    /**
     * Конструктор
     */
    public InputFileGenerator(int numberOfStrings, String fileName, String outputFile){
        this.numberOfStrings = numberOfStrings;
        this.fileName = fileName;
        this.outputFile = outputFile;
    }

    /**
     * Начать работу генератора входного файла
     */
    public void start() {
        /*Записать в файл с указанным именем сгенерированные строки*/
        writeFile(generateRandomStrings(numberOfStrings), fileName);
        /*Очистить выходной файл*/
        clearOutputFile(outputFile);

    }

    /**
     * Запись в файл. Входные данные - список записываемых строк и имя файла.
     */
    private void writeFile(ArrayList<String> stringArray, String fileName) {
        try {
            myFileWriter = new FileWriter(fileName);
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
     * Сгенерировать список строк, содержащих случайные символы в указанном диапазоне
     */
    private ArrayList<String> generateRandomStrings(int length) {
        /*Временный список*/
        ArrayList<String> generatedStrings = new ArrayList<>();
        /*Здесь можно установить seed для конкретного рандома*/
        Random random = new Random();
        int randomCharNumber;
        int i = 0;
        /*Пока не будет достигнуто требуемое количество генерируемых строк*/
        while (i < length) {
            int j = 0;
            String currentStr = "";
            /*Пока не будет достигнуто треуемое количество генерируемых символов*/
            while (j < STRING_MAX_LENGTH) {
                /*Случайное число в заданном диапазоне*/
                randomCharNumber = random.nextInt(MAX - MIN + 1) + MIN;
                /*Добавляем символ, соответствующий случайному числу, в текущую строку*/
                currentStr += (char) randomCharNumber;
                j++;
            }
            /*Добавить сгенерированную строку в конечный список строк*/
            generatedStrings.add(currentStr);
            i++;
        }
        return generatedStrings;
    }
    /**
     * Очистить выходной файл
     */
    private void clearOutputFile(String fileName) {
        try {
            myFileWriter = new FileWriter(fileName);
            myBufferedWriter = new BufferedWriter(myFileWriter);
            myBufferedWriter.write("");
//            myBufferedWriter.newLine();
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
}
