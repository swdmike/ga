package com.gisquest.ga.utils;

import com.esri.core.geometry.*;
import com.gisquest.ga.config.AppConfig;
import com.gisquest.ga.enums.ResultEnum;
import com.gisquest.ga.exception.AppException;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by swd on 2018/8/9
 *
 * 几何处理工具
 */
@Component
@Slf4j
public class GeometryUtil
{
    private static AppConfig appConfig;

    @Autowired
    public GeometryUtil(AppConfig appConfig) {
        GeometryUtil.appConfig = appConfig;
    }

    public static Boolean isEmptyGeometry(Geometry geometry)
    {
        if (geometry == null || geometry.isEmpty())
        {
            return true;
        }
        return false;
    }

    /**
     * 从WKT创建多边形
     */
    public static Polygon createPolygonFromWKT(String wkt)
    {
        Geometry geometry = OperatorImportFromWkt.local().execute(WktImportFlags.wktImportDefaults,
                Geometry.Type.Polygon, wkt, null);

        if (isEmptyGeometry(geometry))
        {
            ResultEnum geometryError = ResultEnum.GEOMETRY_EMPTY;
            log.error(geometryError.getMessage());
            throw new AppException(geometryError.getCode(),
                    geometryError.getMessage());
        }
        return (Polygon) geometry;
    }

    /**
     * 从多边形创建WKT
     */
    public static String createWKTFromPolygon(Polygon polygon)
    {
        if (isEmptyGeometry(polygon))
        {
            ResultEnum geometryError = ResultEnum.GEOMETRY_EMPTY;
            log.error(geometryError.getMessage());
            throw new AppException(geometryError.getCode(),
                    geometryError.getMessage());
        }
        String wkt = OperatorExportToWkt.local().execute(WktExportFlags.wktExportDefaults,
                polygon, null);


        return wkt;
    }

    public static String simplifyWKTPolygon(String wkt)
    {
        Polygon polygonFromWKT = createPolygonFromWKT(wkt);
        Polygon polygon = simplifyPolygon(polygonFromWKT);
        String wktFromPolygon = createWKTFromPolygon(polygon);
        return wktFromPolygon;
    }

    /**
     * 纠正几何
     */
    public static Polygon simplifyPolygon(Polygon polygon)
    {
        if (isEmptyGeometry(polygon))
        {
            ResultEnum geometryError = ResultEnum.GEOMETRY_EMPTY;
            log.error(geometryError.getMessage());
            throw new AppException(geometryError.getCode(),
                    geometryError.getMessage());
        }
        double area2D = polygon.calculateArea2D();
        System.out.println(area2D);
        SpatialReference spatialReference = SpatialReference.create(appConfig.getSrid());
        NonSimpleResult nonSimpleResult = new NonSimpleResult();
        boolean isSimple = OperatorSimplifyOGC.local().isSimpleOGC(polygon,
                spatialReference, true, nonSimpleResult, null);
        boolean isSimpleB =OperatorSimplify.local().isSimpleAsFeature(polygon,
                spatialReference, true, nonSimpleResult, null);
        if (isSimple)
        {
            return polygon;
        }
        else
        {
            Geometry geometry = OperatorSimplifyOGC.local().execute(polygon,
                    spatialReference, true, null);
            return (Polygon) geometry;
        }
    }
}
