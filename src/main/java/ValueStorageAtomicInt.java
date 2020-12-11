import java.util.concurrent.atomic.AtomicInteger;

public class ValueStorageAtomicInt {
    private static AtomicInteger value = new AtomicInteger();

    //11.6 Атомарные переменные
    public static void  incrementValue() {
        value.incrementAndGet();
    }

    public static int getValue() {
        return value.intValue();
    }
}
