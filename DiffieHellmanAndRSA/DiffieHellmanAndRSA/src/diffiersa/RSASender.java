package diffiersa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class RSASender {
   
    private final static String FILENAME = "file.txt";
    
    public static final String N_MSG = "n";
   
    public static final String E_MSG = "e";
   
    public static final String TEXT_MSG = "text";
    
    private FileHelper myFileHelper;
    /*Диапазон генерируемых чисел*/
    private static final int MAX = 99;
    private static final int MIN = 90;
    private int bigNum;         //n
    private int exp;            //e
    /*Два случайных числа*/
    private int randNum1;
    private int randNum2;
    private int phiOfBigNum;    //k
    private int privateKey = 0;     //d
    /*Список принятых сообщений*/
    public LinkedList<Character> receivedMessages = new LinkedList<>();

    /*Конструктор*/
    public RSASender() {
        /*Инициализация объекта класса FileHelper*/
        myFileHelper = new FileHelper();
    }
    /**
     * Сгенерировать случайные значения, которые будут удовлетворять всем условиям
     */
    public void calculateNumbers() {
        Random random = new Random();
        /*Пока не найдётся число, у которого фи будет кратно трём*/
        do {
            /*Генерация двух случайных чисел в диапазоне*/
            randNum1 = random.nextInt(MAX - MIN + 1) + MIN;
            randNum2 = random.nextInt(MAX - MIN + 1) + MIN;
            /*Расчёт фи чисел и их произведения*/
            bigNum = randNum1 * randNum2;
            phiOfBigNum = (randNum1 - 1) * (randNum2 - 1);
        } while (phiOfBigNum % 3 != 0);
        exp = 3;
        /*Вычисление личного ключа*/
        privateKey = ((exp - 1) * phiOfBigNum + 1) / exp;
    }

    /*Отправить сообщение: записать тип сообщения и его содержание в файл*/
    public void sendMessage(String type, int content) {
        myFileHelper.writeData(type + " " + content, FILENAME);
    }

    /*Прочитать сообщение*/
    public void readMessage() {
        /*Читается последняя строка из списка считанных строк*/
        ArrayList<String> strings = myFileHelper.readData(FILENAME);
        String[] cur = strings.get(strings.size() - 1).split(" ");
        /*Последняя строка из файла разбивается на две части: тип сообщения и его содержимое*/
        /*В зависимости от типа сообщения, соответствующей перменной присваивается содержимое сообщения*/
        switch (cur[0]) {
            case N_MSG:
                this.bigNum = (Integer.valueOf(cur[1]));
                break;
            case E_MSG:
                this.exp = (Integer.valueOf(cur[1]));
                break;
            case TEXT_MSG:
//                this.receivedMessages.
                break;
        }
    }

    /*Вычисление выражения (base^power) mod divisor*/
    public int calculateMod(int base, int power, int divisor) {
        BigInteger p = new BigInteger(String.valueOf(base));
        p = p.pow(power);
        BigInteger r = new BigInteger(String.valueOf(divisor));
        p = p.mod(r);
        return p.intValue();
    }
    /*Шифровка текста в качестве шифра принимается значение int шифруемого символа*/
    /*Далее шифровка каждого символа по алгоритму*/
    public ArrayList<Integer> cryptText(ArrayList<Character> chars) {
        ArrayList<Integer> crypted = new ArrayList<>();
        for (Character ch : chars) {
            System.out.println((int) ch);
            crypted.add(calculateMod((int) ch, exp, bigNum));   //c
        }
        return crypted;
    }
    /*Чтобы расшифровать текст, необходимо выполнить обратную операцию по формуле из алгоритма*/
    public char encryptText(int value) {
        return (char) calculateMod(value, privateKey, bigNum);
    }


    public int getExp() {
        return exp;
    }

    public int getBigNum() {
        return bigNum;
    }

}
