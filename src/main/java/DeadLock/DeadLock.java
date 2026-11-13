package DeadLock;

public class DeadLock implements Runnable{

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    private final boolean firstThread;

    public DeadLock(boolean firstThread) {
        this.firstThread = firstThread;
    }

    /*
     * Сценарий взаимной блокировки (Deadlock).
     *
     * 1. Первый поток захватывает lock1.
     * 2. Он засыпает на 100 мс,но продолжает удерживать lock1.
     * 3. За это время второй поток захватывает lock2
     *    и тоже засыпает, продолжая удерживать lock2.
     * 4. Первый поток просыпается и пытается захватить lock2.
     *    Поскольку lock2 занят, поток переходит в состояние BLOCKED.
     * 5. Второй поток просыпается и пытается захватить lock1.
     *    Поскольку lock1 занят, он тоже переходит в состояние BLOCKED.
     *
     * Результат:
     * Первый поток удерживает lock1 и ожидает lock2.
     * Второй поток удерживает lock2 и ожидает lock1.
     * Ни один из потоков не может продолжить выполнение
     * и освободить удерживаемую блокировку.
     * Получается взаимная блокировка.
     */

    @Override
    public void run() {

        // Логика первого потока
        if (firstThread) {
            synchronized (lock1) {
                System.out.println("Поток 1 захватил lock1");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                synchronized (lock2) {
                    System.out.println("Поток 1 захватил lock2");
                }
            }
            // Логика второго потока
        } else {
            synchronized (lock2) {
                System.out.println("Поток 2 захватил lock2");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                synchronized (lock1) {
                    System.out.println("Поток 2 захватил lock1");
                }
            }
        }
    }
}
