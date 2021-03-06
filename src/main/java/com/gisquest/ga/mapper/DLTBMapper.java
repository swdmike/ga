package com.gisquest.ga.mapper;

import com.gisquest.ga.domain.DLTB;
import com.gisquest.ga.dto.Area;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by swd on 2018/8/7
 *
 * @Description:
 */
@Repository
@Mapper
public interface DLTBMapper
{
    public void delete();

    public Integer insert(@Param("areas") List<Area> areas, @Param("srid") Integer srid);

    public List<DLTB> intersect();

    public List<DLTB> intersectOne(@Param("wkt") String wkt, @Param("srid") Integer srid);

}
