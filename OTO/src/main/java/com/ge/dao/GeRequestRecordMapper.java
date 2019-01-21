package com.ge.dao;


import com.ge.po.GeRequestRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

@Repository
public interface GeRequestRecordMapper extends Mapper<GeRequestRecord> {

    int queryCount(@Param("requestUser") String requestUser,@Param("requesturlSimple") String requesturlSimple);

    List<GeRequestRecord>queryRecord(@Param("requesturlSimple")String requesturlSimple,
                                     @Param("startDate")Date startDate,@Param("endDate")Date endDate);
}