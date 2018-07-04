package com.seu.job.controller;

import com.seu.common.utils.PageUtils;
import com.seu.common.component.R;
import com.seu.job.entity.ScheduleJobLogEntity;
import com.seu.job.service.ScheduleJobLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author Mark liangfeihu@163.com
 * @since 1.2.0 2016-11-28
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    /**
     * 定时任务日志列表
     */
    @GetMapping("/list")
    @RequiresPermissions("blog:schedule:log")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = scheduleJobLogService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 定时任务日志信息
     */
    @GetMapping("/info/{logId}")
    public R info(@PathVariable("logId") Long logId) {
        ScheduleJobLogEntity log = scheduleJobLogService.selectById(logId);

        return R.ok().put("log", log);
    }
}
