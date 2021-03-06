package com.InfoWeb.demo.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ViewObject {
    private Map<String,Object> objs = new HashMap<>();

    public void set(String key,Object value){
        objs.put(key,value);
    }

    public Object get(String key){
        return objs.get(key);
    }
}
