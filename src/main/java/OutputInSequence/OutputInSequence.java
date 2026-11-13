package OutputInSequence;

public class OutputInSequence implements Runnable{

    //turn — общий флаг, который показывает, чья сейчас очередь
    //firstThread — переменная, определяющая, какой это поток
    private static final Object lock = new Object();

    private static boolean turn = true;

    private final boolean firstThread;

    public OutputInSequence(boolean firstThread) {
        this.firstThread = firstThread;
    }

    @Override
    public void run() {
        while (printNumber()) {
            // Повторяем вывод
        }
    }

    //Логика метода
    //Если firstThread == true, выводим 1 и устанавливаем turn = false, передавая очередь второму потоку
    //Если firstThread == false, выводим 2 и устанавливаем turn = true, передавая очередь первому потоку
    //lock.notify() уведомляет ожидающий поток о смене очереди.
    private boolean printNumber() {
        synchronized (lock) {
            while (turn != firstThread) {

                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
            if (firstThread) {
                System.out.print("1 ");
                turn = false;
            } else {
                System.out.print("2 ");
                turn = true;
            }
            lock.notify();
            return true;
        }
    }
}
