package com.example.post.list.app.utils;

import com.example.post.list.app.model.persistence.entities.Post;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import java.util.List;
import java.util.Map;

/**
 * this is utiliy class for Collection validations
 */
public final class CollectionsUtils {

    private CollectionsUtils() {
    }

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }
}
