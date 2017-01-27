package diffiersa;

import java.math.BigInteger;
import java.util.ArrayList;

public class DiffieSender {
    
    private final static String FILENAME = "file.txt";
   
    public static final String GENERATOR_MSG = "generator";
   
    public static final String PRIME_NUMBER_MSG = "primeNumber";
    
    public static final String COMPANION_RES_MSG = "companionResult";
  
    private FileHelper myFileHelper;
    
    private int generator;
    
    private int primeNumber;
    
    private int companionResult;
   
    private int privateKey;
    
    private int myKey;
   
    public DiffieSender(){
       
        myFileHelper = new FileHelper();
    }
   
    public void sendMessage(String type, int content){
        myFileHelper.writeData(type + " " + content, FILENAME);
    }

  
    public void readMessage(){
       
        ArrayList<String> strings = myFileHelper.readData(FILENAME);
        String[] cur = strings.get(strings.size()-1).split(" ");
       
        switch(cur[0]){
            case GENERATOR_MSG:
                this.generator = (Integer.valueOf(cur[1]));
                break;
            case PRIME_NUMBER_MSG:
                this.primeNumber = (Integer.valueOf(cur[1]));
                break;
            case COMPANION_RES_MSG:
                this.companionResult = (Integer.valueOf(cur[1]));
                break;
        }
    }
   
    public int calculateMod(int base, int power, int divisor) {
        BigInteger p = new BigInteger(String.valueOf(base));
        p = p.pow(power);
        BigInteger r = new BigInteger(String.valueOf(divisor));
        p = p.mod(r);
        return p.intValue();
    }

    public int getGenerator() {
        return generator;
    }

    public void setGenerator(int generator) {
        this.generator = generator;
    }

    public int getPrimeNumber() {
        return primeNumber;
    }

    public void setPrimeNumber(int primeNumber) {
        this.primeNumber = primeNumber;
    }

    public int getCompanionResult() {
        return companionResult;
    }

    public void setCompanionResult(int companionResult) {
        this.companionResult = companionResult;
    }

    public int getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(int privateKey) {
        this.privateKey = privateKey;
    }

    public int getMyKey() {
        return myKey;
    }

    public void setMyKey(int myKey) {
        this.myKey = myKey;
    }
}
