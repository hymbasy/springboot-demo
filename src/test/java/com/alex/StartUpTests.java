package com.alex;

import com.alex.model.User;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StartUpTests {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	public void contextLoads() {
		stringRedisTemplate.opsForValue().set("name", "hello world");
		Object name = stringRedisTemplate.opsForValue().get("name");
		System.out.println(name.toString());
	}

	@Test
	public void saveUser() {
		User user = new User();
		user.setName("测试");
		user.setAddress("北京市海淀区学清路");
		user.setEmail("4651349@154.com");
		user.setTelephone("115555555555");
		mongoTemplate.insert(user);
		Query query = new Query(Criteria.where("name").is("测试"));
		List<User> userList = mongoTemplate.find(query, User.class);
		System.out.println(JSON.toJSONString(userList));
	}


	@Test
	public void savePerson() {
		StringBuilder sql = new StringBuilder("INSERT INTO PERSON(username,password,phone,address,remark,created) values (?,?,?,?,?,?)");
		int update = jdbcTemplate.update(sql.toString(), new Object[]{"测试数据", "123456", "15555555555", "北京市海淀区学清路", "这是一条测试数据",System.currentTimeMillis()});
		System.out.println(update);
       /* StringBuilder sql = new StringBuilder("SELECT * FROM person WHERE id=?");
        List<User> query = jdbcTemplate.query(sql.toString(), new Object[]{1}, new RowMapper<User>() {
            @Nullable
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setTelephone(resultSet.getString("phone"));
                user.setName(resultSet.getString("username"));
                user.setAddress(resultSet.getString("address"));
                user.setId(resultSet.getInt("id") + "");
                return user;
            }
        });

        System.out.println(JSON.toJSONString(query));*/


	}

}
