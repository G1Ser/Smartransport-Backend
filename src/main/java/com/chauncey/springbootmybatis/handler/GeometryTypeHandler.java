package com.chauncey.springbootmybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;
import org.postgis.PGgeometry;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GeometryTypeHandler extends BaseTypeHandler<Geometry> {
    private static final WKTReader reader = new WKTReader();
    private static final WKTWriter writer = new WKTWriter();
    // 数据库空间坐标系
    private static final int SRID_IN_DB = 4326;
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Geometry parameter, JdbcType jdbcType) throws SQLException {
        String wkt = writer.write(parameter);
        PGgeometry pg = new PGgeometry(wkt);
        org.postgis.Geometry geometry = pg.getGeometry();
        geometry.setSrid(SRID_IN_DB);
        ps.setObject(i,pg);
    }
    @Override
    public Geometry getNullableResult(ResultSet rs, String columnName) throws SQLException {
        PGgeometry pg = (PGgeometry) rs.getObject(columnName);
        return getResult(pg);
    }

    @Override
    public Geometry getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        PGgeometry pg = (PGgeometry) rs.getObject(columnIndex);
        return getResult(pg);
    }

    @Override
    public Geometry getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        PGgeometry pg = (PGgeometry) cs.getObject(columnIndex);
        return getResult(pg);
    }

    private Geometry getResult(PGgeometry pg){
        if(pg == null){
            return null;
        }
        String pgWKT = pg.toString();
        String target = String.format("SRID=%s;",SRID_IN_DB);
        String wkt = pgWKT.replace(target,"");
        try{
            return reader.read(wkt);
        }catch (Exception e) {
            throw new RuntimeException("解析wkt失败：" + wkt, e);
        }
    }
}
