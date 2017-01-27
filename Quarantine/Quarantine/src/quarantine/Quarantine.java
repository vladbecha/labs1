package quarantine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;

public class Quarantine {
    /*Количество строк в файле*/
    private static final int NUMBER_OF_STINGS = 30;
    /*Имя входного файла*/
    private static final String INPUT_FILENAME = "input.txt";
    /*Имя выходного файла*/
    private static final String OUTPUT_FILENAME = "output.txt";
    /*Первый буфер: ограничен по количеству хранимых записей: 5 строк*/
    public static FirstBuf<String> fBuf = new FirstBuf<>(5);
    /*Второй буфер*/
    public static LinkedList<String> sBuf = new LinkedList<>();
    /*Хэшмэп для хранения информации об обрабатываемых блоках и для вывода этой информации*/
    public static HashMap<Integer, TimeIO> Log = new HashMap<>();

    public static void main(String[] args) {
        /*Генерация входного файла и очистка выходного файла*/
        InputFileGenerator fileGen = new InputFileGenerator(NUMBER_OF_STINGS, INPUT_FILENAME, OUTPUT_FILENAME);
        fileGen.start();

        /*Начало работы потока чтения и потока обработки*/
        ReadThread read = new ReadThread(INPUT_FILENAME);
        read.setName("ReadThread");
        HandleThread handle = new HandleThread();
        handle.setName("HandleThread");
        read.start();
        handle.start();

        /*Ожидание окончания потока обработки*/
        synchronized (handle){
            try {
                handle.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /*Отображение информации об обработанных блоках*/
        synchronized (Log) {
            /*Последовательно по всем элементам хэшмэпа с информацией*/
            Iterator<Map.Entry<Integer, TimeIO>> itr = Log.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<Integer, TimeIO> currentStr = itr.next();
                System.out.print(currentStr.getKey() + ": " + currentStr.getValue() + "\n");
            }
        }
    }
}
