import ch.qos.logback.core.util.SystemInfo;
import java.io.File;

public class Main {
    private static  int newWidth = 300;
    private static String srcFolder = "D:\\Java\\Files\\srcFolder";
    private static String dstFolder = "D:\\Java\\Files\\dstFolder";
    private static int cores = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        File srcDir = new File((srcFolder));

        long start = System.currentTimeMillis();
        //System.out.println(cores);

        File[] files = srcDir.listFiles();
        int minCount = Math.min(files.length, cores);
        int part = files.length / minCount;

        for (int i = 0; i < minCount; i++) {
            File[] files1 = new File[part];
            System.arraycopy(files, i, files1, 0, files1.length);

            //11.2 Класс Thread
            doWithThreadClass(files1, start);

            //11.3 Интерфейс Runnable
            doWithRunnableInterface(files1, start);
        }
    }

    public static void doWithThreadClass(File[] files1, long start) {
        ImageResizer resizer1 = new ImageResizer(files1, newWidth, dstFolder, start);
        resizer1.start();
    }

    public static void doWithRunnableInterface(File[] files1, long start) {
        ImageResizerRunnable resizer1 = new ImageResizerRunnable(files1, newWidth, dstFolder, start);
        new Thread(resizer1).start();
    }

}
