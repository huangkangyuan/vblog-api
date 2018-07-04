package com.seu.job.service;

import com.baomidou.mybatisplus.service.IService;
import com.seu.common.utils.PageUtils;
import com.seu.job.entity.ScheduleJobLogEntity;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author Mark liangfeihu@163.com
 * @since 1.2.0 2016-11-28
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
