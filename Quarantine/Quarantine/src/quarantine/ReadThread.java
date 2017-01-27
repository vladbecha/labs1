package quarantine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadThread extends Thread {
    /*Объекты классов для чтения из файла*/
    private FileReader myFileReader;
    private BufferedReader myBufferedReader;
    /*Имя файла*/
    private String fileName;
    /*String number's counter*/
    private int counter;

    public ReadThread(String fileName) {
        this.fileName = fileName;
    }

    /*Запускается при вызове метода start данного класса*/
    @Override
    public void run() {
        try {
            /*Инициализация объектов классов для чтения из файла*/
            myFileReader = new FileReader(this.fileName);
            myBufferedReader = new BufferedReader(myFileReader);
            /*Счётчик строк*/
            counter = 0;
            /*Повторять цикл, пока не пришло прерывание*/
            do {
                if (!Thread.interrupted())    //Проверка прерывания
                {
                    try {
                        /*Синхронизация по первому буферу обеспечивает
                        * верную очерёдность обращения потоков к буферу:
                        * если два потока одновременно запрашивают буфер,
                        * то сначала вс ним будет работать один, а потом второй*/
                        synchronized (Quarantine.fBuf) {
                            /*Считывание по две строки*/
                            for (int i = 0; i < 2; i++) {
                                String line = myBufferedReader.readLine();
                                if (line != null) {
                                    /*Добавление строки в первый буфер (номер строки записывается в неё же как последний char)*/
                                    if (counter < 10) {
                                        Quarantine.fBuf.add(line + "0" + counter);
                                    } else {
                                        Quarantine.fBuf.add(line + counter);
                                    }
                                    /*Фиксируется время прихода блока в первый буфер*/
                                    synchronized (Quarantine.Log) {
                                        Quarantine.Log.put(counter, new TimeIO(System.currentTimeMillis()));
                                    }
                                } else {
                                    /*Когда файл считан до конца - прерывание работы потока*/
                                    Thread.currentThread().interrupt();
                                }
                                /*Инкремент счётчика строк*/
                                counter++;
                            }
                        }
                    } catch (IOException ioe) {
                    }
                } else {
                    return;        //Завершение потока
                }
                try {
                    /*DON"T TOUCH IT*/
                    Thread.sleep(20);        //Приостановка потока на 1 сек.
                } catch (InterruptedException e) {
                    return;    //Завершение потока после прерывания
                }
            }
            while (true);
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
    }
}
