package com.chauncey.springbootmybatis.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GeoJSONUtils {
    public static <T> Map<String,Object> convertToGeoJSON(List<T> objects){
        Map<String,Object> geoJSON = new HashMap<>();
        geoJSON.put("type","FeatureCollection");
        List<Map<String,Object>> features = objects.stream().map(object ->{
            Map<String, Object> feature = new HashMap<>();
            feature.put("type", "Feature");
            // 获取对象的所有字段
            Field[] fields = object.getClass().getDeclaredFields();
            Map<String, Object> properties = new HashMap<>();
            for(Field field : fields){
                // 设置访问权限
                field.setAccessible(true);
                Object value = null;
                try {
                    value = field.get(object);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                if(field.getName().equals("geometry")){
                    feature.put("geometry",value);
                }else{
                    properties.put(field.getName(),value);
                }
            }
            feature.put("properties",properties);
            return feature;
        }).collect(Collectors.toList());
        geoJSON.put("features",features);
        return geoJSON;
    }
}
