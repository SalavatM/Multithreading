//параллельное выполнение
public class Concurrency {
    public static void main(String[] args) {
        //11.5 Состояние гонки и критические секции
        for (int i = 0; i < 4; i++) {
            new Thread( () -> {
                for (int j = 0; j < 1000; j++) {
                    ValueStorage.incrementValue();
                }
                System.out.println(ValueStorage.getValue());
            }).start();
        }

        //11.6 Атомарные переменные
        for (int i = 0; i < 4; i++) {
            new Thread( () -> {
                for (int j = 0; j < 10000; j++) {
                    ValueStorageAtomicInt.incrementValue();
                }
                System.out.println(ValueStorageAtomicInt.getValue());
            }).start();
        }
    }
}
