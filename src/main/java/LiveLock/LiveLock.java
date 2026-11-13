package LiveLock;

import java.util.concurrent.atomic.AtomicBoolean;

public class LiveLock implements Runnable {

    private static final AtomicBoolean turn = new AtomicBoolean(true);
    private final boolean firstThread;

    public LiveLock(boolean firstThread) {
        this.firstThread = firstThread;
    }

    //1. Первый поток проверяет turn
    //2. Если turn == true, выводит сообщение и изменяет флаг на false
    //3. Вызывает Thread.yield(), предлагая уступить процессор
    //4. Второй поток получает возможность выполнить свою часть кода
    //5. Второй поток изменяет флаг обратно на true и тоже вызывает Thread.yield()
    //6. Всё повторяется
    //Таким образом потоки постоянно уступают друг другу и не переходят к какой-либо полезной работе

    @Override
    public void run() {

        while (true) {

            if (firstThread) {
                if (turn.get()) {
                    System.out.println("Поток 1 уступает потоку 2");
                    turn.set(false);
                }
            } else {
                if (!turn.get()) {
                    System.out.println("Поток 2 уступает потоку 1");
                    turn.set(true);
                }
            }

            Thread.yield();
        }
    }
}
