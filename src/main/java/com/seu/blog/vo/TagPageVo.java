package com.seu.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by liangfeihu on 2018/7/7.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagPageVo {
    Integer offset;
    Integer pageSize;
    Integer tagId;
}
