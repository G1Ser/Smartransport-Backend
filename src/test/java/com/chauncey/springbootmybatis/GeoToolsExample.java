package com.chauncey.springbootmybatis;

import com.chauncey.springbootmybatis.entity.Camera;
import com.chauncey.springbootmybatis.utils.GeoJSONUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geojson.geom.GeometryJSON;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.simple.SimpleFeature;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GeoToolsExample {
    private static final int SRID_IN_DB = 4326;
    private static final WKTReader reader = new WKTReader();
    private static final StringWriter writer = new StringWriter();
    @Test
    public void JSONToGeoJSON() throws Exception {
        // 1. 定义几何（WKT 格式的 Point）
        String wkt = "POINT (114.40417883838779 30.48156050158492)";
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
        SimpleFeature feature = featureBuilder.buildFeature(null);
        FeatureJSON featureJSON = new FeatureJSON();
        featureJSON.writeFeature(feature,writer);
        String geoJSON  = writer.toString();
        // 输出特征
        System.out.println(geoJSON);
    }
    @Test
    public void WktToGeoJSON() throws Exception{
        String pgWkt = "SRID=4326;POINT(114.40417883838779 30.48156050158492)";
        String target = String.format("SRID=%s;", SRID_IN_DB);
        String wkt = pgWkt.replace(target, "");
        Point point = (Point) reader.read(wkt);
        GeometryJSON geoJSON = new GeometryJSON();
        System.out.println(geoJSON.toString(point));
    }
    @Test
    public void testConvertToGeoJSON() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String geometryJson = "{\"type\":\"Point\",\"coordinates\":[114.4042,30.4816]}";
        JsonNode geometry1 = objectMapper.readTree(geometryJson);
        Camera camera1 = new Camera();
        camera1.setId(1L);
        camera1.setCode("XXT001");
        camera1.setLocation("南湖大道与关山大道交叉路口");
        camera1.setUrl(null);
        camera1.setGeometry(geometry1);
        String geometryJson2 = "{\"type\":\"Point\",\"coordinates\":[114.4152,30.4805]}";
        JsonNode geometry2 = objectMapper.readTree(geometryJson2);
        Camera camera2 = new Camera();
        camera2.setId(2L);
        camera2.setCode("XXT002");
        camera2.setLocation("南湖大道与光谷大道交叉路口");
        camera2.setUrl(null);
        camera2.setGeometry(geometry2);
        List<Camera> cameras = Arrays.asList(camera1, camera2);
        GeoJSONUtils geoJSONUtils = new GeoJSONUtils();
        Map<String, Object> geoJSON = geoJSONUtils.convertToGeoJSON(cameras);
        String geoJSONStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(geoJSON);
        System.out.println(geoJSONStr);
    }
}
