package com.example.post.list.app.model.persistence.entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.room.TypeConverter;

/**
 * this {@link DataTypeConverter} implementation is used to handle
 * complex structure object list like a primitive attribute
 * @author fabian hoyos
 */
public class DataTypeConverter {
    private static Gson gson = new Gson();

    @TypeConverter
    public static List<Comment> stringToList(String data) {
        if (data == null) {
            return new ArrayList<>();
        }

        Type listType = new TypeToken<List<Comment>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String ListToString(List<Comment> comments) {
        return gson.toJson(comments);
    }
}