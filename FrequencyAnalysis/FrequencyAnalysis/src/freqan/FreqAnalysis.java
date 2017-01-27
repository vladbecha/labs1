package freqan;

import java.util.*;

/**
 * Частотный анализ
 */
public class FreqAnalysis {
    /*Размер алфавита*/
    private final static int ALPHABET_SIZE = 26;
    /*Название txt-файла, содержащего частоту появления в тексте каждого символа*/
    private final static String ALPHABET_FREQ_FILENAME = "frequency.txt";
    /*Имя txt-файла, содержащего шифр*/
    private final static String TEXT_FILENAME = "encrypted.txt";
    /*Символ в начале txt-файла, который необходимо удалить*/
    private final static int FIRST_CHAR = 65279;
    /*Объект класса, который позволяет считывать/записывать из/в txt-файл(а)*/
    private FileHelper myFileHelper;
    /*Список строк алфавита и частот каждой буквы*/
    private ArrayList<String> alphaStrings;
    /*Список строк, считанных из txt-файла*/
    private ArrayList<String> textStrings;
    /*Хэшмэп, содержащий алфавит и частоту каждой буквы, расположенных в порядке убывания*/
    private LinkedHashMap<Character, Double> alphabetFrequency;
    /*Хэшсет с уникальными значениями считанных символов*/
    HashSet<String> numbers;
    /*Хэшмэп с вычисленными частотами прочитанных символов*/
    HashMap<String, Double> numFrequency;

    /**
     * Конструктор, который вызывается при действии "Частотный анализ"
     */
    public FreqAnalysis() {
        /*Инициализация объекта класса FileHelper*/
        myFileHelper = new FileHelper();
        /*Инициализировать список строк алфавита - считать из txt-файла*/
        alphaStrings = myFileHelper.readData(ALPHABET_FREQ_FILENAME);
        /*Заполнить частоту алфавита в хэшмэп*/
        alphabetFrequency = fillAlphabet(alphaStrings);
        /*Инициализировать список строк зашифрованного текста - считать из txt-файла*/
        textStrings = myFileHelper.readData(TEXT_FILENAME);
        /*Инициализировать хэшсет с уникальными символами -
         *заполнить, основываясь на списке строк зашифрованного текста*/
        numbers = fillNumbersHashSet(textStrings);
        /*Инициализировать хэшмэп с частотами символов*/
        numFrequency = new HashMap<>();
        /*В хэшмэп заносятся уникальные символы в качестве ключей.
         *Значение частоты пока не подсчитано, поэтому оно null. */
        for (String str : numbers) {
            numFrequency.put(str, null);
        }
        /*Подсчитать частоту появления каждого символа в тексте*/
        countFrequency(textStrings, numFrequency, numbers);
        /*Отсортировать частоты символов по убыванию*/
        numFrequency = sortByValues(numFrequency);
        /*Хэшмэп reverseKey, в котором символ можно найти по его шифру*/
        HashMap<Character, Integer> key = Encryption.generateKey();
        HashMap<Integer, Character> reverseKey = new HashMap<>();
        Iterator<Map.Entry<Character, Integer>> itr = key.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<Character, Integer> currentStr = itr.next();
            reverseKey.put(currentStr.getValue(), currentStr.getKey());
        }
        /*Количество верно угаданных символов*/
        int correct = 0;
        /*Пройти по всем символам*/
        Iterator<Map.Entry<Character, Double>> itr1 = alphabetFrequency.entrySet().iterator();
        Iterator<Map.Entry<String, Double>> itr2 = numFrequency.entrySet().iterator();
        /*Пока есть следующий символ*/
        while (itr1.hasNext()) {
            /*Переменная, которая фиксирует совпадение символов*/
            boolean match = false;
            Map.Entry<Character, Double> currentStr1 = itr1.next();
            Map.Entry<String, Double> currentStr2 = itr2.next();
            /*Сравниваются два упорядоченных хэшмэпа:
            * тот, в котором хранятся частоты появления символов латинского алфавита в англоязычных текстах,
            *   составленные на основе многочисленных исследований - alphabetFrequency - в порядке убывания, и
            * тот, в котором хранятся частоты появления символов латинского алфавита,
            *   на основе подсчёта количества появления конкретного символа в конкретном тексте*/
            /*Фиксируется совпадение положения символов из двух хэшмэпов*/
            if (reverseKey.get(Integer.valueOf(currentStr2.getKey())).equals(currentStr1.getKey())) {
                match = true;
                correct++;
            }
            /*Строка для отображения информации*/
            String oneStr = String.format("%.3f", currentStr1.getValue());
            System.out.print(currentStr1.getKey() + ": "
                    + oneStr + (oneStr.length() > 5 ? " -- " : " --- ")
                    + (currentStr2.getKey().length() > 1 ? currentStr2.getKey() : currentStr2.getKey() + " ")
                    + ": " + String.format("%.5f", currentStr2.getValue()) + " and it was: "
                    + reverseKey.get(Integer.valueOf(currentStr2.getKey()))
                    + (match ? " +" : "")
                    + "\n");
        }
        /*Отобразить количество угаданных символов*/
        System.out.println(correct + " out of " + ALPHABET_SIZE);
        /*Вернуться к начальному интерфейсу*/
        new StartingPoint();

    }

    /**
     * Заполнить хэшмэп, содержащий алфавит и частоты появления каждой буквы.
     * Хэшмэп сортируется в порядке убывания.
     */
    private LinkedHashMap<Character, Double> fillAlphabet(ArrayList<String> letterFreq) {
        /*Хэшмэп, содержащий буквы и соответствующие им частоты появления*/
        HashMap<Character, Double> alphabetFreqHashMap = new HashMap<Character, Double>();
        /*Первый char файла необходимо удалить*/
        letterFreq.set(0, letterFreq.get(0).replace((char) FIRST_CHAR, ' ').trim());
        /*Последовательный перебор строк*/
        for (String str : letterFreq) {
            /*Разбить каждую строку по символу " "*/
            String[] parts = str.split(" ");
            /*Первая часть - буква*/
            Character letter = parts[0].charAt(0);
            /*Вторая часть - частота*/
            Double frequency = Double.parseDouble(parts[1]);
            /*Поместить букву и её частоту в хэшмэп*/
            alphabetFreqHashMap.put(letter, frequency);
        }
        /*Отсортировать хэшмэп по убыванию*/
        return sortByValues(alphabetFreqHashMap);
    }

    /**
     * Отсортировать по убыванию
     */
    private static LinkedHashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue())
                        .compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        // Here I am copying the sorted list in HashMap
        // using LinkedHashMap to preserve the insertion order
        LinkedHashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    /**
     * Заполнить хэшсет считанных символов
     */
    private HashSet<String> fillNumbersHashSet(ArrayList<String> strings) {
        /*Временный хэшсет*/
        HashSet<String> tempHashSet = new HashSet<>();
        /*Цикл исполняется до тех пор, пока размер хэшсета
          не будет равен размеру алфавита.*/
        for (String str : strings) {
            /*Строка символов разбивается на части,
             *разделённые пробелом*/
            String[] parts = str.split(" ");
            /*Каждая часть представляет собой символ -
             * - зашифрованную букву. Если символ не пустое
             * значение, то он добавляется в хэшсет.*/
            for (String partStr : parts) {
                /*Если встретившийся символ не пустой,
                * то добавить его в хэшсет.*/
                if (!partStr.equals("")) {
                    /*Коллекция хэшсет гарантирует, что
                     *каждый символ в коллекции будет уникален.*/
                    tempHashSet.add(partStr);
                    /*Прекращение цикла при заполнении хэшсета*/
                    if (tempHashSet.size() == ALPHABET_SIZE) {
                        break;
                    }
                }
            }
            /*Прекращение цикла при заполнении хэшсета*/
            if (tempHashSet.size() == ALPHABET_SIZE) {
                break;
            }
        }
        return tempHashSet;
    }

    /**
     * Вычисляет частоту появления символов в тексте.
     */
    public void countFrequency(ArrayList<String> strings,
                               HashMap<String, Double> freq,
                               HashSet<String> num) {
        int amountOfLetters = 0;
        /*Цикл по всем строкам списка строк*/
        for (String str : strings) {
            /*Каждая строка разбивается на символы, разделённые пробелом*/
            String[] parts = str.split(" ");
            /*Для каждого символа*/
            for (String partStr : parts) {
                /*Временное значение - частота текущего символа*/
                Double currentValue = freq.get(partStr);
                /*Если встретившийся символ не пустое значение*/
                if (!partStr.equals("")) {
                    /*Если символ встретился в первый раз - присвоить
                     * его частоте значение ноль. Оно будет инкрементировано
                     * далее.*/
                    if (currentValue == null) {
                        currentValue = 0.0;
                    }
                    /*Инкремент частоты конкретного встретившегося символа*/
                    freq.put(partStr, currentValue + 1.0);
                    /*Инкремент общего количества символов*/
                    amountOfLetters++;
                }
            }
        }
        /*Для всех строк хэшсета num*/
        for (String str : num) {
            double currentDouble = freq.get(str);
            /*Добавить к хэшмэп частот символов из файла символ и его частоту
            * появления в тексте*/
            freq.put(str, (currentDouble / amountOfLetters) * 100);
        }
    }
}
