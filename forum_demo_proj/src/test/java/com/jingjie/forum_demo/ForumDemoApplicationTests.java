package com.jingjie.forum_demo;

import com.jingjie.forum_demo.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ForumDemoApplicationTests {

	@Autowired
	UserDao userDaoTest;

	@Test
	public void contextLoads() {
	}

}
