import java.util.Random;

public class Utils {

    public static String randomString(int length){
        Random random = new Random();
        int leftLimit = 97;

        int rightLimit = 122;
        StringBuilder buffer = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append(Character.toChars(randomLimitedInt));
        }

        return buffer.toString();
    }

    public static String randomEmail(int length){
        Random random = new Random();
        int leftLimit = 97;

        int rightLimit = 122;
        StringBuilder buffer = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append(Character.toChars(randomLimitedInt));
        }

        return buffer.toString()+"@gmail.com";
    }

    public static int rnd( int max)
    {
        int min = 2;
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

}
