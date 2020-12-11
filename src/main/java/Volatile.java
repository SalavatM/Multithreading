import java.util.Scanner;

//11.7 Ключевое слово Volatile (непостоянный)
//запрет кэширования значения переменной
public class Volatile {
    public static void main(String[] args) {
        VolatileTask task = new VolatileTask();
        new Thread(task).start();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        task.stop();
        System.out.println("Main: " + task.getCounterValue());
    }
}
