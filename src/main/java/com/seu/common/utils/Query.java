package com.seu.common.utils;

import com.baomidou.mybatisplus.plugins.Page;
import com.seu.common.xss.SQLFilter;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author liangfeihu@163.com
 * @since 2.0.0 2017-03-14
 */
public class Query<T> extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    /**
     * mybatis-plus分页参数
     */
    private Page<T> page;
    /**
     * 当前页码
     */
    private int pageNo = 1;
    /**
     * 每页条数
     */
    private int pageSize = 10;

    public Query(Map<String, Object> params) {
        this.putAll(params);

        //分页参数
        if (params.get("pageNo") != null) {
            pageNo = Integer.parseInt((String) params.get("pageNo"));
        }
        if (params.get("pageSize") != null) {
            pageSize = Integer.parseInt((String) params.get("pageSize"));
        }

        this.put("offset", (pageNo - 1) * pageSize);
        this.put("page", pageNo);
        this.put("limit", pageSize);

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = SQLFilter.sqlInject((String) params.get("sidx"));
        String order = SQLFilter.sqlInject((String) params.get("order"));
        this.put("sidx", sidx);
        this.put("order", order);

        //mybatis-plus分页
        this.page = new Page<>(pageNo, pageSize);

        //排序
        if (StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(order)) {
            this.page.setOrderByField(sidx);
            this.page.setAsc("ASC".equalsIgnoreCase(order));
        }

    }

    public Page<T> getPage() {
        return page;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }
}
