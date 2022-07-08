import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    public static void main(String[] args) {
        ExecutorService ex = Executors.newSingleThreadExecutor();
        CountDownLatch latch = new CountDownLatch(1);
        ex.execute(() -> waitForTimeAndPrint(latch));
        System.out.println("This is main");
        latch.countDown();
        ex.shutdown();
    }

    public static void waitForTimeAndPrint(CountDownLatch latch) {
        try {
            latch.await();
            System.out.println("This is not in main");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }
}
