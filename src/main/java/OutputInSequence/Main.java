package OutputInSequence;

public class Main {

    public static void main(String[] args) {

        Thread thread1 = new Thread(new OutputInSequence(true));
        Thread thread2 = new Thread(new OutputInSequence(false));

        thread1.start();
        thread2.start();
    }
}