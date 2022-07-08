import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ex {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        System.out.println("This is main: "+Thread.currentThread().getName());
        latch.countDown();
        ExecutorService ex = Executors.newSingleThreadExecutor();
        ExecutorService ex2 = Executors.newSingleThreadExecutor();
        ex.execute(() -> new Dog().bark(latch));
        ex.shutdown();
        ex2.execute(() -> new Dog().bark(latch));
        ex2.shutdown();
    }
}

class Dog {
    public void bark(CountDownLatch lactch) {
        try {
            lactch.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.println("Bark Bark!!!" + Thread.currentThread().getName());
    }
}