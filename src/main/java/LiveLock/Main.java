package LiveLock;

public class Main {

    public static void main(String[] args) {

        Thread thread1 = new Thread(new LiveLock(true));
        Thread thread2 = new Thread(new LiveLock(false));

        thread1.start();
        thread2.start();
    }
}