import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        List<String> pesquisas = Arrays.asList("Gladis", "Pedro", "Maria", "João", "Faria", "Douglas", "Alice", "Lima", "josé", "Daniel", "Rodrigo", "Ronaldo", "Luis", "Lucas", "Bruno");
        Semaphore semaphore = new Semaphore(5);
        List<Thread> threads = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < pesquisas.size(); i++) {
            Thread t = new Worker(semaphore, pesquisas.get(i));
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
