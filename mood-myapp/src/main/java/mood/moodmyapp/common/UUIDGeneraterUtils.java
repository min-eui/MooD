package mood.moodmyapp.common;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDGeneraterUtils {



    //  11자리의 UUID todtjd
    public static String makeShortUUID(){
        UUID uuid = UUID.randomUUID();
        return parseToShortUUID(uuid.toString());
    }

    private static String parseToShortUUID(String uuid) {
        long l = ByteBuffer.wrap(uuid.getBytes()).getLong();
        return Long.toString(l, Character.MAX_RADIX);
    }


}
