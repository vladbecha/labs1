package quarantine.modules;

import sun.awt.image.ImageWatched;

import java.util.LinkedList;

public class DataTypeModule {
    /*Константы, в которых хранятся границы различных по типу данных символов*/
    private static final int SPACE = 32;
    private static final int SYMB_FIRST_START = 33;
    private static final int NUM_START = 48;
    private static final int SYMB_SECOND_START = 58;
    private static final int UPCASE_START = 65;
    private static final int SYMB_THIRD_START = 91;
    private static final int LOWCASE_START = 97;
    private static final int SYMB_FORTH_START = 123;
    private static final int SYMB_FORTH_END = 126;
    private int spaceAmout = 0;
    private int symbolsAmout = 0;
    private int numberAmout = 0;
    private int validAmout = 0;
    private int allAmout = 0;
    /*Обработка поступившей в модуль строки*/
    public LinkedList<Integer> handleString(String string) {
        LinkedList<Integer> numbersOfValidValues = new LinkedList<>();
        char[] symbols = string.toCharArray();
        int i = 0;
        /*Цикл по всем символам строки:
        * если int текущего символа попадает в границы указанных символов, то
        * добавить номер его позиции в строке в список номеров корректных символов,
        * который возвращает этот метод.
        * В данном случае, допустимыми символами являются буквы латинского алфавита в
        * любом регистре и пробел. При необходимости можно изменить тип допустимых символов
        * путём изменения условий попадания в границы*/
        for (char ch : symbols) {
            int curCharNum = (int) ch;
            /*Если int текущего символа совпадает с int пробела, то зафиксировать
            * номер его позиции в строке, а также инкрементировать счётчик пробелов*/
            if (curCharNum == SPACE) {
                numbersOfValidValues.add(i);
                spaceAmout++;
            } else if ((curCharNum >= SYMB_FIRST_START && curCharNum < NUM_START) ||
                    (curCharNum >= SYMB_SECOND_START && curCharNum < UPCASE_START) ||
                    (curCharNum >= SYMB_THIRD_START && curCharNum < LOWCASE_START) ||
                    (curCharNum >= SYMB_FORTH_START && curCharNum <= SYMB_FORTH_END)) {
                symbolsAmout++;
            } else if (curCharNum >= NUM_START && curCharNum < SYMB_SECOND_START) {
                numberAmout++;
                /*Если int текущего символа попадает в границы int допустимых символов, то зафиксировать
            * номер его позиции в строке, а также инкрементировать счётчик корректных символов*/
            } else if ((curCharNum >= UPCASE_START && curCharNum < SYMB_THIRD_START) ||
                    (curCharNum >= LOWCASE_START && curCharNum < SYMB_FORTH_START)) {
                validAmout++;
                numbersOfValidValues.add(i);
            }
            i++;
        }

        return numbersOfValidValues;
    }
}
