import java.util.ArrayList;
import java.util.Vector;

//11.11 Потокобезопасные классы (HashMap -> HashTable; Collection -> synchronizedCollection)
public class ThreadSafeClasses {
    //private static ArrayList<Double> numbers = new ArrayList<>(); //потоконебезопасный класс
    private static Vector<Double> numbers = new Vector<>();         //потокобезопасный класс

    //private static StringBuilder builder = new StringBuilder(); //потоконебезопасный класс
    private static StringBuffer builder = new StringBuffer();     //потокобезопасный класс

    public static void main(String[] args) {
        arraySample();
        stringSample();
    }

    public static void arraySample() {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threads.add(new Thread( () -> {
                for (int j = 0; j < 100000; j++) {
                    numbers.add(Math.random());
                }
                System.out.println(numbers.size());
            }));
        }
        threads.forEach(Thread :: start);
    }

    public static void stringSample() {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threads.add(new Thread( () -> {
                for (int j = 0; j < 100000; j++) {
                    builder.append("d");
                }
                System.out.println(builder.length());
            }));
        }
        threads.forEach(Thread :: start);
    }
}
