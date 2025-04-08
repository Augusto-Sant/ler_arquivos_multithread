import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Worker extends Thread {
    private final Semaphore semaphore;
    private final String filePath;
    private String searchString;

    public Worker(Semaphore semaphore, String filePath, String searchString) {
        this.semaphore = semaphore;
        this.filePath = filePath;
        this.searchString = searchString;
    }

    public List<String> readFile(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return List.of(); // return empty list on error
        }
    }

    public String findName(String filePath, String name) {
        List<String> lines = readFile(filePath);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains(name)) {
                System.out.println("Encontrou em: " + filePath +
                    " | Linha: " + i +
                    " | Nome: " + line);
            }
        }
        return null;
    }

    @Override
    public void run() {
        try {
//            System.out.println(filePath + " waiting for permit...");
            semaphore.acquire();
//            System.out.println(filePath + " got permit.");

            findName(filePath, searchString);

//            System.out.println(filePath + " releasing permit.");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
