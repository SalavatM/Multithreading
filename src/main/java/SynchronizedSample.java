import java.util.ArrayList;

public class SynchronizedSample {

    private static ArrayList<Double> numbers = new ArrayList<>();

    public static void main(String[] args) {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(SynchronizedSample :: someHeavyMethod));
        }
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(SynchronizedSample :: someHeavyMethod2));
        }
        threads.forEach(t -> t.start());
    }

    //11.8 Synchronized-метод обеспечивает работу метода только в одном потоке
    private static synchronized void someHeavyMethod() {
        for (int i = 0; i < 1000000; i++) {
            numbers.add(Math.random() / Math.random());
        }
        System.out.println(numbers.size());
        numbers.clear();
    }

    //11.9 Synchronized-блок обеспечивает потокобезопасные операции
    private static  void someHeavyMethod2() {
        for (int i = 0; i < 1000000; i++) {
            //монопольный доступ потока к объекту numbers
            double value = Math.random() / Math.random();
            synchronized(numbers) {
                numbers.add(value);
            }
        }
        System.out.println(numbers.size());
        numbers.clear();
    }

}
