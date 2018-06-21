package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class GsonUtil {
    private static Gson gson = new GsonBuilder().create();

    public static String bean2Json(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T json2Bean(String jsonStr, Class<T> objectClass) {
        return gson.fromJson(jsonStr, objectClass);
    }

    /**
     * 下面这种是针对json字符串不稳定，会变动情况
     * @param json
     * @return
     */

    public static String jsonFormatter(String json) {
        //对json格式化
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(json);
        return gson.toJson(je);
    }
}
