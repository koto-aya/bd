package com.bd.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 夕雾
 * @description: lru缓存
 * @date 2024/9/22 16:26
 */
public class LRUCache<K,V> extends LinkedHashMap<K,V> {
    private final Integer maxCapacity;

    public LRUCache(Integer size, Integer maxCapacity){
        super(size,0.75f,true);
        this.maxCapacity = maxCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size()>maxCapacity;
    }

    public boolean isMax(){
        return this.size()==maxCapacity;
    }
}
