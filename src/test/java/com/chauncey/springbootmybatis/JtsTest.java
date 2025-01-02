package com.chauncey.springbootmybatis;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;

public class JtsTest {
    @Test
    public void parseWKB() throws ParseException {
        String wkb = "0105000020E610000001000000010200000008000000CB8C368D2D995C401366F361EF813E40306C4C4E2D995C400A3732B8DF813E40306C4C4E2D995C400A3732B8DF813E401FFE19A420995C40B7F2AB6223803E40BB2FE6ADFA985C408FE57658DA7D3E40BF19B456F4985C405FBA626F5B7B3E4021106744A9985C4030FD64B5F2763E40D64248DCA3985C40B05DF3D3B1763E40";
        byte[] bytes = WKBReader.hexToBytes(wkb);
        Geometry geometry = new WKBReader().read(bytes);
        System.out.println(geometry);
    }
}
