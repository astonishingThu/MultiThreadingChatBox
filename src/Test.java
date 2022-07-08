import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String[] args) {
        System.out.println("This is main");
        ExecutorService ex = Executors.newSingleThreadExecutor();
        ex.execute(() -> System.out.println("This is not in main"));
        ex.shutdown();
    }
}
