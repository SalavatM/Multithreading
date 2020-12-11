public class VolatileTask implements Runnable {
    private long counter;

    //volatile запрещает кэшировать переменную
    //при обращении к переменной любой поток получит её последнее записанное значение
    private volatile boolean isRunning;

    public VolatileTask() {
        isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            counter++;
        }
        System.out.println("Task: " + counter);
    }

    public void stop() {
        isRunning = false;
    }

    public long getCounterValue() {
        return  counter;
    }
}
