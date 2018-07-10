package com.seu.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @author liangfeihu
 * @since 2018/7/10 14:52.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildCommentDto implements Serializable{

    private static final long serialVersionUID = 5061212992497947120L;

    private Long articleId;
    private Long parentId;
}
