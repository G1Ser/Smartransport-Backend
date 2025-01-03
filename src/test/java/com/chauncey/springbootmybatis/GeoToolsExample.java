package com.chauncey.springbootmybatis;

import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geojson.feature.FeatureJSON;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.simple.SimpleFeature;

import java.io.StringWriter;

public class GeoToolsExample {
    public static void main(String[] args) throws Exception {
        // 1. 定义几何（WKT 格式的 Point）
        String wkt = "POINT (114.40417883838779 30.48156050158492)";
        WKTReader reader = new WKTReader();
        Point point = (Point) reader.read(wkt);

        // 2. 使用 SimpleFeatureTypeBuilder 创建 SimpleFeatureType
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("Feature");
        builder.add("id", Integer.class);      // 添加 id 属性
        builder.add("code", String.class);     // 添加 code 属性
        builder.add("location", String.class); // 添加 location 属性
        builder.add("url", String.class);      // 添加 url 属性
        builder.add("geometry", Point.class);  // 添加 geometry 属性（几何数据）

        // 3. 创建 SimpleFeatureType
        SimpleFeatureType featureType = builder.buildFeatureType();

        // 4. 创建 SimpleFeatureBuilder 并构建 SimpleFeature
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(featureType);
        featureBuilder.add(1);                                      // 设置 id
        featureBuilder.add("XXT01");                                // 设置 code
        featureBuilder.add("南湖大道与关山大道交叉路口");           // 设置 location
        featureBuilder.add(null);                                   // 设置 url（为空）
        featureBuilder.add(point);                                  // 设置几何数据（geometry）

        // 5. 创建 SimpleFeature 实例
        SimpleFeature feature = featureBuilder.buildFeature("1");
        FeatureJSON featureJSON = new FeatureJSON();
        StringWriter writer = new StringWriter();
        featureJSON.writeFeature(feature,writer);

        // 输出特征
        System.out.println(writer.toString());
    }
}
