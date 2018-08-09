package com.gisquest.ga.mapper;

import com.gisquest.ga.domain.DLTB;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
    List<DLTB> intersect(@Param("wkt") String wkt, @Param("srid") Integer srid);
}
