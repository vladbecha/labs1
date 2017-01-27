package quarantine.modules;

import quarantine.Quarantine;

public class LogModule {
    private static int counter;
    /*Обработка строки: вычисляется время, ушедшее на обработку строки и её номер*/
    public void handleString(String string, long timeOut){
        String strNum = string.substring(string.length()-2);
        counter = Integer.valueOf(strNum);
        Quarantine.Log.get(counter).setStringItself(string);
        Quarantine.Log.get(counter).setTimeOut(timeOut);
        Quarantine.Log.get(counter).setTimeTaken(timeOut - Quarantine.Log.get(counter).getTimeIn());
    }
}
