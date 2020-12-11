public class ValueStorage {
    private static int value;

    //11.5 Состояние гонки и критические секции
    public static void  incrementValue() {
        value = value + 1;
    }

    public static int getValue() {
        return value;
    }
}
