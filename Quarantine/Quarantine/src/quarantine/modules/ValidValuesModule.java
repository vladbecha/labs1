package quarantine.modules;

import java.util.LinkedList;

public class ValidValuesModule {
    /*Обработать строку: при обработке строки данный модуль оставляет в поступившей строке только
    * те символы, позиции которых указаны во входном списке numbers*/
    public String handleString(String string, LinkedList<Integer> numbers) {
        String str = "";
        while(!numbers.isEmpty()){
            str+=string.charAt(numbers.pollFirst());
        }
        return str;
    }
}
