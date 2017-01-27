package quarantine;

import quarantine.modules.DataTypeModule;
import quarantine.modules.LogModule;
import quarantine.modules.ValidValuesModule;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class HandleThread extends Thread {
    private static final String FILENAME = "output.txt";
    private FileWriter myFileWriter;
    private BufferedWriter myBufferedWriter;

    @Override
    public void run() {
        try {
            /*Delay*/
            Thread.sleep(20);
        } catch (InterruptedException ie) {

        }
        do {
            if (!Thread.interrupted())    //Проверка прерывания
            {
                /*Создание модуля проверки типа данных*/
                DataTypeModule dTMod = new DataTypeModule();
                /*Создание модуля, оставляющего только корректные данные*/
                ValidValuesModule vVMod = new ValidValuesModule();
                /*Создание модуля, описывающего информацию об обработанных строках*/
                LogModule lMod = new LogModule();
                String currentString;
                synchronized (Quarantine.fBuf) {
                    try {
                        /*Взять из первого буфера первую строку*/
                        currentString = Quarantine.fBuf.pollFirst();
                        /*Строка - переменная для хранения номера поступившей строки*/
                        /*следует помнить о том, что если цифры объявлены корректным типом данных, то
                        * необходимо вырезать последние два символа из строки*/
                        String strNumber = currentString.substring(currentString.length() - 2);
                        /*Список, в котором хранятся номера позиций допустимых по типу данных символов*/
                        /*Они заносятся в список путём обработки строки в модуле проверки типа данных*/
                        LinkedList<Integer> validNumbers = dTMod.handleString(currentString);
                        /*Обработка строки модулем, оставляющим только корректные символы*/
                        currentString = vVMod.handleString(currentString, validNumbers);
                        /*Снова прибавляем номер строки*/
                        currentString += strNumber;
                        /*Фиксируется время окончания обработки строки*/
                        lMod.handleString(currentString, System.currentTimeMillis());
                        /*Во второй буфер добавляется обработанная строка*/
                        Quarantine.sBuf.add(currentString);
                        /*Запись в файл строки из второго буфера*/
                        writeFile(Quarantine.sBuf.pollFirst(), FILENAME);
                        /*Если буфер пуст, то подождать. Если в буфер не поступило новых значений,
                        * то закончить поток*/
                    } catch (NullPointerException npe) {
                        try {
                            Thread.sleep(40);
                            if(Quarantine.fBuf.isEmpty()){
                                System.out.println("I am done " + Thread.currentThread().getName());
                                Thread.currentThread().interrupt();
                                synchronized (this){
                                    this.notify();
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            } else
                return;        //Завершение потока
            try {
                Thread.sleep(15);        //Приостановка потока
            } catch (InterruptedException e) {
                return;    //Завершение потока после прерывания
            }
        }
        while (true);
    }

    /**
     * Запись в файл
     */
    private void writeFile(String str, String fileName) {
        try {
            myFileWriter = new FileWriter(fileName, true);
            myBufferedWriter = new BufferedWriter(myFileWriter);
            myBufferedWriter.write(str + "\r\n");
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
