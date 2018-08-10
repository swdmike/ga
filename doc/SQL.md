### 叠加


```sql
select t.dldm, t.dlmc, sde.st_area(t.shape) mj, sde.st_astext(t.shape) wkt from
        (select dldm, dlmc, sde.st_intersection(f.shape, SDE.ST_Geometry(#{wkt},#{srid})) shape from JQDLTB3308_2017 f
        where sde.st_intersects(f.shape, SDE.ST_Geometry(#{wkt}, #{srid}))=1) t
);
```


