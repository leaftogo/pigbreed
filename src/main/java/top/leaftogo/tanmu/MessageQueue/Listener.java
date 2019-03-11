package top.leaftogo.tanmu.MessageQueue;

public class Listener extends Thread {

    private boolean ifend;

    private MessageQueue messageQueue;

    public Listener(MessageQueue messageQueue){
        ifend = false;
        this.messageQueue = messageQueue;
    }


    @Override
    public void run(){
        while(!ifend){
            messageQueue.distribute();
            try {
                Thread.currentThread().sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void stopping(){
        ifend = true;
    }



}


