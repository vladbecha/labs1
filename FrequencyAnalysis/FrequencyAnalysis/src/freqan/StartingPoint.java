package freqan;

import java.util.Scanner;

/**
 * Начальный интерфейс
 */
public class StartingPoint {
    /*Конструктор начального интерфейса, который запускается в начале программы*/
    public StartingPoint() {
        /*Отобразить сообщение о доступных действиях*/
        System.out.println("0 - Encryption\n" + "1 - Frequency Analysis");
        /*Получить ответ пользователя*/
        int userAnswer = readIntData();
        /*В зависимости от выбора пользователя, запустить
         * соответствующий процесс*/
        if (userAnswer == 0) {
            new Encryption();
        } else if (userAnswer == 1) {
            new FreqAnalysis();
        } else {
            /*Если был введён неверный ответ, отобразить
             *Соответствующее сообщение и завершить программу. */
            System.out.println("Bad input");
        }
    }

    /**
     * Считать ответ пользователя. Тип ответа int.
     */
    private int readIntData() {
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }

}
