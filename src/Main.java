import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        String searchString = "Gladis";
        Semaphore semaphore = new Semaphore(10);
        List<Thread> threads = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            String path = "src/arquivosNomes/nomescompletos-0" + i + ".txt";
            Thread t = new Worker(semaphore, path, searchString);
            t.start();
            threads.add(t);
        }

        for (Thread t : threads) {
            try {
                t.join(); // wait for thread to finish
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Pesquisa demorou " + duration + " ms");
    }
}
