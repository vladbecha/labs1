package freqan;

import java.util.*;


public class Encryption {
   
    private final static int SEED = 3;
  
    private final static int ALPHABET_SIZE = 26;
    
    private final static int ALPHABET_START = (int) 'a';
   
    private final static String FILE_TO_ENCRYPT = "text.txt";
    
    private final static String ENCRYPTED_FILE = "encrypted.txt";
    
    private FileHelper myFileHelper;
    
    private ArrayList<String> textStrings;
    
    private ArrayList<String> encryptedText;
 
    private HashMap<Character, Integer> key;

    
    public Encryption() {
        /*Инициализация объекта класса FileHelper*/
        myFileHelper = new FileHelper();
        /*Инициализировать список строк - добавить в него считанные из файла строки*/
        textStrings = myFileHelper.readData(FILE_TO_ENCRYPT);
        /*Инициализировать ключ к шифру с помощью метода generateKey*/
        key = generateKey();
        /*Отобразить ключ*/
        printHashMap(key);
        /*Инициализировать список зашифрованных строк - зашифровать считанные строки*/
        encryptedText = encrypt(textStrings);
        /*Записать в файл зашифрованный текст*/
        myFileHelper.writeData(encryptedText, ENCRYPTED_FILE);
        /*Вернуться к начальному интерфейсу*/
        new StartingPoint();
    }

    /**
     * Сгенерировать ключ
     */
    public static HashMap<Character, Integer> generateKey() {
        /*Если строку заменить на*/
        /*Random random = new Random();*/
        /*то ключ каждый раз будет разным*/
        Random random = new Random(SEED);
        /*Смещение - случайное, в пределах размера алфавита*/
        int displacement = random.nextInt(ALPHABET_SIZE + 1);
        /*Отобразить сгенерированное смещение*/
        System.out.println(displacement);
        /*Временная переменная, в которой хранится сгенерированный ключ*/
        HashMap<Character, Integer> keyToReturn = new HashMap<>();
        /*Цикл по всем буквам алфавита*/
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            /*Временная переменная - текущий номер буквы. Он равен сумме номера буквы и смещения*/
            /*Ключом является сгенерированное случайное смещение.*/
            /*Смещение прибавляется к номеру буквы алфавита - так шифруется каждая буква.*/
            /*Если сумма смещения и ключа превосходит размер алфавита, то из неё вычитается размер алфавита.*/
            int curNum = i + displacement;
            /*Если текущее значение больше размера алфавита, то размер алфавита вычитается
            * из текущего значения.*/
            if (curNum > ALPHABET_SIZE) {
                curNum = curNum - ALPHABET_SIZE;
            }
            /*Во временный хэшмэп добавляется буква текущая алфавита и её шифр*/
            keyToReturn.put((char) (i + ALPHABET_START), curNum);
        }
        return keyToReturn;
    }

    /**
     * Зашифровать текст
     */
    public ArrayList<String> encrypt(ArrayList<String> text) {

        /*Перевести буквы строк текста в нижний регистр*/
        for (int i = 0; i < text.size(); i++) {
            text.set(i, text.get(i).toLowerCase());
        }
        /*Список, в котором будут храниться строки зашифрованного текста*/
        ArrayList<String> encryptedFile = new ArrayList<String>();
        /*Для всех строк текста*/
        for (String str : text) {
            /*Если строка не пустая*/
            if (!str.equals("")) {
                /*Текущая шифруемая строка*/
                String currentString = "";
                int i = 0;
                /*Для каждого символа строки*/
                while (i < str.length()) {
                    /*Переменная текущего символа*/
                    char currentChar = str.charAt(i);
                    /*Если текущий символ принадлежит алфавиту
                    * (т. е. попадает в границы значений номеров букв),
                    * то зашифровать его и добавить в текущую строку*/
                    if ((int) currentChar >= ALPHABET_START &&
                            (int) currentChar < ALPHABET_START + ALPHABET_SIZE) {
                        /*Зашифровать символ и добавить в текущую строку*/
                        currentString += this.getNumberOfLetter(str.charAt(i)) + " ";
                    }
                    /*Перейти к следующему символу*/
                    i++;
                }
                /*Добавить текущую зашифрованную строку в список строк*/
                encryptedFile.add(currentString);
            }
        }
        return encryptedFile;
    }

    /**
     * Вернуть шифр буквы.
     */
    private int getNumberOfLetter(char letter) {
        return key.get(letter);
    }

    /**
     * Напечатать хэшмэп
     */
    private void printHashMap(HashMap<Character, Integer> hashMap) {
        /*Итератор для хэшмэпа*/
        Iterator<Map.Entry<Character, Integer>> itr = hashMap.entrySet().iterator();
        /*Пока есть следующее значение*/
        while (itr.hasNext()) {
            /*Отобразить текущие ключ и значение*/
            Map.Entry<Character, Integer> currentStr = itr.next();
            System.out.print(currentStr.getKey() + ": " + currentStr.getValue() + "\n");
        }
    }
}
