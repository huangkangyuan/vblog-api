package com.seu.job.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.seu.job.entity.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志
 *
 * @author Mark liangfeihu@163.com
 * @since 1.2.0 2016-11-28
 */
@Mapper
public interface ScheduleJobLogDao extends BaseMapper<ScheduleJobLogEntity> {
}
