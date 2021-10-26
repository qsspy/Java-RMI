import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static int getRandomInt(final int min,final int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static long getRandomLong(final long min,final long max) {
        return ThreadLocalRandom.current().nextLong(min, max + 1);
    }
}
