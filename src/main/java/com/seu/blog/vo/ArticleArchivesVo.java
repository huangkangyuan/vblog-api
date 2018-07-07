package com.seu.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by liangfeihu on 2018/7/7.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleArchivesVo {
    private String year;
    private String month;
    private Integer count;
}
