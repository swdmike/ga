package com.gisquest.ga.utils;

import com.esri.core.geometry.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Created by swd on 2018/8/9
 *
 * @Description:
 */
public class GeometryUtil
{
    /**
     * 功能描述: 将经纬度的WKT转成平面的
     * @param:
     * @return:
     */
    public static String CovertWKTPolygonJWDToXY(String wkt, int ellipse, double centralM, int dai)
    {
        Polygon polygon = createPolygonFromWKT(wkt);
        if (polygon == null)
        {
            return "";
        }
        Polygon pg = CovertPolygonJWDToXY(polygon, ellipse, centralM, dai);
        wkt = OperatorExportToWkt.local().execute(WktExportFlags.wktExportDefaults,
                pg, null);
        return wkt;
    }

    public static Polygon CovertPolygonJWDToXY(Polygon polygon, int ellipse, double centralM, int dai){
        Polygon polygonNew = new Polygon();
        SegmentIterator iterator = polygon.querySegmentIterator();
        while (iterator.nextPath()) {
            Boolean startNewPath = true;
            while (iterator.hasNextSegment())
            {
                Segment segment = iterator.nextSegment();
                double lgn = segment.getStartX();
                double lat = segment.getStartY();
                double[] xyStart = BLToXY(lgn, lat, ellipse, centralM, dai);
                segment.setStartXY(xyStart[0], xyStart[1]);
                lgn = segment.getEndX();
                lat = segment.getEndY();
                double[] xyEnd = BLToXY(lgn, lat, ellipse, centralM, dai);
                segment.setEndXY(xyEnd[0], xyEnd[1]);
                polygonNew.addSegment(segment, startNewPath);
                startNewPath = false;
            }
        }
        return polygonNew;
    }

    /**
     * 功能描述:经纬度转平面坐标
     * @param:
     * @return:
     */
    public static double[] BLToXY(double longitude, double latitude, int ellipse, double centralM, int dai)
    {
        double pi = Math.PI / 180;
        double a = 6378137.0;
        double b = 6356752.314140356;
        if (ellipse == 1980)
        {
            a = 6378140.0;
            b = 6356755.2882;
        }
        double e2 = (Math.pow(a, 2) - Math.pow(b, 2)) / (Math.pow(a, 2) * 1.0);
        double e1 = (Math.pow(a, 2) - Math.pow(b, 2)) / Math.pow(b, 2);
        double ca = a * (1 - e2) * (1 + 3 / 4.0 * e2 + 45 / 64.0 * Math.pow(e2, 2) + 175 / 256.0 * Math.pow(e2, 3) +
                11025 / 16384.0 * Math.pow(e2, 4));
        double cb = a * (1 - e2) * (3 / 4.0 * e2 + 45 / 64.0 * Math.pow(e2, 2) + 175 / 256.0 * Math.pow(e2, 3) +
                11025 / 16384.0 * Math.pow(e2, 4));
        double cc = a * (1 - e2) * (15 / 32.0 * Math.pow(e2, 2) + 175 / 368.0 * Math.pow(e2, 3) +
                3675 / 8192.0 * Math.pow(e2, 4));
        double cd = a * (1 - e2) * (35 / 96.0 * Math.pow(e2, 3) + 735 / 2048.0 * Math.pow(e2, 4));
        double ce = a * (1 - e2) * (315 / 1024.0 * Math.pow(e2, 4));
        double ro = 180 / Math.PI;
        double la = latitude * pi;
        double lo = centralM;
        double dl = (longitude - lo) / ro;
        double xx = ca / ro * la / pi - Math.cos(la) * (cb * Math.sin(la) +
                cc * Math.pow(Math.sin(la), 3) + cd * Math.pow(Math.sin(la), 5)
                + ce * Math.pow(Math.sin(la), 7));
        double n = a / Math.sqrt(1 - e2 * Math.pow(Math.sin(la), 2));
        double t = Math.tan(la);
        double nn = Math.sqrt(e1) * Math.cos(la);

        double X = xx + n * Math.sin(la) * Math.cos(la) * Math.pow(dl, 2) / 2.0 +
                n / 24.0 * Math.sin(la) * Math.pow(Math.cos(la), 3) * (5 - Math.pow(t, 2) + 9 * Math.pow(nn, 2) +
                        4 * Math.pow(nn, 4)) * Math.pow(dl, 4) +
                n / 720.0 * Math.sin(la) * Math.pow(Math.cos(la), 5) * (61 - 58 * t * t + Math.pow(t, 4)) * Math.pow(dl, 6);
        double Y = 500000.0 + n * Math.cos(la) * dl + n / 6.0 * Math.pow(Math.cos(la), 3) * (1 - t * t + nn * nn) * Math.pow(dl, 3) +
                n / 120.0 * Math.pow(Math.cos(la), 5) * (5 - 18 * t * t + Math.pow(t, 4) + 14 * nn * nn - 58 * nn * nn * t * t) * Math.pow(dl, 5);

        return new double[] { Y + dai * 1000000, X };
    }

    /**
     * 平面坐标转经纬度
     * 调用时翻一下坐标: XYToBL(y, x - dai * 1000000, eclipse, centralM);
     * @param:
     * @return: [longitude,latitude]
     */
    public static double[] XYToBL(double x, double y, int ellipse, double centralM)
    {
        double a = 6378137.0;
        double b = 6356752.314140356;
        if (ellipse == 1980)
        {
            a = 6378140.0;
            b = 6356755.2882;
        }
        y = y - 500000.0;
        double PI = 3.14159265358979;
        double PI2 = PI / 180.0;
        double ro = 180.0 / Math.PI;
        double e2 = (a * a - b * b) / (a * a);
        double ef2 = (a * a - b * b) / (b * b);

        double A0 = a * (1 - e2) * (1 + 3.0 / 4.0 * e2 + 45.0 / 64.0 * e2 * e2 + 175.0 / 256.0 * Math.pow(e2, 3) + 11025.0 / 16384.0 * Math.pow(e2, 4));
        double B0 = a * (1 - e2) * (3.0 / 4.0 * e2 + 45.0 / 64.0 * e2 * e2 + 175 / 256.0 * Math.pow(e2, 3) + 11025.0 / 16384.0 * Math.pow(e2, 4));
        double C0 = a * (1 - e2) * (15.0 / 32.0 * e2 * e2 + 175.0 / 368.0 * Math.pow(e2, 3) + 3675.0 / 8192.0 * Math.pow(e2, 4));
        double D0 = a * (1 - e2) * (35.0 / 96.0 * Math.pow(e2, 3) + 735.0 / 2048.0 * Math.pow(e2, 4));
        double E0 = a * (1 - e2) * (315.0 / 1024.0 * Math.pow(e2, 4));
        double BBf = (x * ro) / A0;
        double Bf = 0;

        int dloop = 0;
        while (true)
        {
            dloop += 1;
            if (dloop > 100)
            {
                break;
            }
            Bf = BBf;
            double tempRadBf = Bf * Math.PI / 180.0;

            BBf = (x + B0 * Math.cos(tempRadBf) * Math.sin(tempRadBf) +
                    C0 * Math.cos(tempRadBf) * Math.pow(Math.sin(tempRadBf), 3) +
                    D0 * Math.cos(tempRadBf) * Math.pow(Math.sin(tempRadBf), 5) +
                    E0 * Math.cos(tempRadBf) * Math.pow(Math.sin(tempRadBf), 7));
            BBf = BBf * ro / A0;

            if (BBf - Bf < 1e-30)
            {
                break;
            }
        }

        double tempBf = Bf * PI2;
        double tf = Math.tan(tempBf);
        double cosbf = Math.cos(tempBf);
        double tf2 = tf * tf;
        double nf2 = ef2 * Math.pow(Math.cos(tempBf), 2);
        double n = y * Math.sqrt(1 + nf2) * b / Math.pow(a, 2);
        double n2 = n * n;
        double B = Bf - (1 + nf2) / PI * tf * (90.0 * n2 - 7.5 * (5.0 + 3.0 * tf2 + nf2 - 9.0 * nf2 * tf2) * n2 * n2) -
                (1 + nf2) / PI * tf * (0.25 * (61.0 + 90.0 * tf2 + 45.0 * tf2 * tf2) * Math.pow(n2, 3));
        double L = (180.0 * n - 30.0 * (1.0 + 2.0 * tf2 + nf2) * n * n2 + 1.5 * (5.0 + 28.0 * tf2 + 24.0 * tf2 * tf2) * n2 * n2 * n) / PI / cosbf + centralM;

        return new double[] { L, B };
    }

    public static Polygon createPolygonFromWKT(String wkt)
    {
        Geometry geom = OperatorImportFromWkt.local().execute(WktImportFlags.wktImportDefaults,
                Geometry.Type.Polygon, wkt, null);

        return (Polygon)geom;
    }

    public static String simplifyPolygonFromWKT(String wkt)
    {
        //TODO
//        Polygon pg = createPolygonFromWKT(wkt);
//
//        return (Polygon)geom;
        return "";
    }

    public static String simplifyPolygon(Polygon polygon)
    {
        //TODO
//        Polygon pg = createPolygonFromWKT(wkt);
//        boolean isSimple = OperatorSimplifyOGC.local().isSimpleOGC(pg,  null,  true, null, null);
//        System.out.println(isSimple);
//        System.out.print("Running simplify operation... ");
//        Geometry gSimple = OperatorSimplifyOGC.local().execute(g, null, true, null);
//        return (Polygon)geom;
        return "";
    }
}
