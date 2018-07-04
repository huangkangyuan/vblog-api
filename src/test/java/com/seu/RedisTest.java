package com.seu;

import com.seu.common.component.RedisUtils;
import com.seu.blog.entity.UserEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
	@Autowired
	private RedisUtils redisUtils;

	@Test
	public void contextLoads() {
		UserEntity user = new UserEntity();
		user.setEmail("qqq@qq.com");
		redisUtils.set("user", user);

		System.out.println(ToStringBuilder.reflectionToString(redisUtils.get("user", UserEntity.class)));
	}

}
