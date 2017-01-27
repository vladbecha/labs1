package diffiersa;

import java.util.ArrayList;

public class RSA {
    
    public static final String N_MSG = "n";
    public static final String E_MSG = "e";
    public static final String TEXT_MSG = "text";
    private FileHelper myFileHelper;
    public RSA(){
       
        RSASender firstSender = new RSASender();
        RSASender secondSender = new RSASender();
       
        ArrayList<Character> chars = new ArrayList<>();
       
        firstSender.calculateNumbers();
        
        firstSender.sendMessage(N_MSG, firstSender.getBigNum());
        secondSender.readMessage();
        firstSender.sendMessage(E_MSG, firstSender.getExp());
        secondSender.readMessage();
       
        myFileHelper = new FileHelper();
        chars = myFileHelper.readFile("message.txt");
       
        ArrayList<Integer> messageFromCompanion = secondSender.cryptText(chars);
      
        myFileHelper.writeFileInt(messageFromCompanion, "firstSenderReceived.txt");
        /*Пересылка символов*/
        for(int msg : messageFromCompanion){
            secondSender.sendMessage(TEXT_MSG, msg);
            firstSender.readMessage();
        }
       
        int i = 0;
        while(i < firstSender.receivedMessages.size()){
            chars.add(firstSender.encryptText(firstSender.receivedMessages.pollFirst()));
            i++;
        }
        /*Записать в файл расшифрованный текст*/
        myFileHelper.writeFile(chars, "firstSenderEncrypted.txt");

        new StartingPoint();
    }
}
