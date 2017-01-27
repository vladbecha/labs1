package diffiersa;

public class DiffieHellman {
   
    public static final String GENERATOR_MSG = "generator";
   
    public static final String PRIME_NUMBER_MSG = "primeNumber";
    
    public static final String COMPANION_RES_MSG = "companionResult";
    public DiffieHellman(){
       
        DiffieSender firstSender = new DiffieSender();
        DiffieSender secondSender = new DiffieSender();

       
        firstSender.setGenerator(3);
        firstSender.sendMessage(GENERATOR_MSG, firstSender.getGenerator());
        secondSender.readMessage();
       
        firstSender.setPrimeNumber(17);
        firstSender.sendMessage(PRIME_NUMBER_MSG, firstSender.getPrimeNumber());
        secondSender.readMessage();
       
        firstSender.setPrivateKey(54);
        firstSender.sendMessage(COMPANION_RES_MSG,
                firstSender.calculateMod(firstSender.getGenerator(),firstSender.getPrivateKey(),firstSender.getPrimeNumber()));
       
        secondSender.setPrivateKey(24);
        secondSender.sendMessage(COMPANION_RES_MSG,
                secondSender.calculateMod(secondSender.getGenerator(),secondSender.getPrivateKey(),secondSender.getPrimeNumber()));
       
        firstSender.setMyKey(firstSender.calculateMod(firstSender.getCompanionResult(), firstSender.getPrivateKey(), firstSender.getPrimeNumber()));
        secondSender.setMyKey(secondSender.calculateMod(secondSender.getCompanionResult(), secondSender.getPrivateKey(), secondSender.getPrimeNumber()));
        if(firstSender.getMyKey() == secondSender.getMyKey()){
            System.out.println("Keys are equal");
        }else{
            System.out.println("Keys are not equal");
        }
       
        new StartingPoint();
    }
}
