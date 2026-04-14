package io.siddharth.myapplication.database;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.siddharth.myapplication.domain.model.GrowthModel;
import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static List<String> fromString(String value) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static GrowthModel fromGrowthString(String value) {
        return gson.fromJson(value, GrowthModel.class);
    }

    @TypeConverter
    public static String fromGrowthModel(GrowthModel model) {
        return gson.toJson(model);
    }
}
