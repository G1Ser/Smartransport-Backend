package com.chauncey.springbootmybatis.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.geotools.geojson.geom.GeometryJSON;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.postgis.PGgeometry;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes({String.class})
public class GeometryTypeHandler extends BaseTypeHandler<JsonNode > {
    private static final int SRID_IN_DB = 4326;
    private static final WKTReader reader = new WKTReader();
    private static final GeometryJSON geometryJSON = new GeometryJSON();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JsonNode parameter, JdbcType jdbcType) throws SQLException {
        PGgeometry pGgeometry = new PGgeometry(String.valueOf(parameter));
        ps.setObject(i, pGgeometry);
    }

    @Override
    public JsonNode  getNullableResult(ResultSet rs, String columnName) throws SQLException {
        PGgeometry pGgeometry = new PGgeometry(rs.getString(columnName));
        try {
            return getGeometryResult(pGgeometry);
        } catch (ParseException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JsonNode  getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        PGgeometry pGgeometry = new PGgeometry(rs.getString(columnIndex));
        try {
            return getGeometryResult(pGgeometry);
        } catch (ParseException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JsonNode  getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {

        PGgeometry pGgeometry = new PGgeometry(cs.getString(columnIndex));
        try {
            return getGeometryResult(pGgeometry);
        } catch (ParseException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private JsonNode  getGeometryResult(PGgeometry pGgeometry) throws ParseException, JsonProcessingException {
        if (pGgeometry == null) {
            return null;
        }
        String pgWkt = pGgeometry.toString();
        String target = String.format("SRID=%s;", SRID_IN_DB);
        String wkt = pgWkt.replace(target, "");
        Point point = (Point) reader.read(wkt);
        String geoJSON = geometryJSON.toString(point);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(geoJSON);
        return jsonNode;
    }
}