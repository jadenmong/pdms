package test;

import com.len.Application;
import com.len.entity.SysUser;
import com.len.redis.RedisService;
import com.len.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author zhuxiaomeng
 * @date 2018/1/5.
 * @email 154040976@qq.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class BootTest {

    @Autowired
    SysUserService userService;

    @Autowired
    RedisService redisService;

    @Test
    public void testStartJob() throws Exception {
        //12
        SysUser user = userService.selectByPrimaryKey("2211fec3e17c11e795ed201a068c6482");
        System.out.println(user.getUsername());
    }

    @Test
    public void redisTest() throws InterruptedException {
        redisService.set("str", "你好 世界", 10L);
        String str = redisService.get("str");
        System.out.println("value:" + str);
        Thread.sleep(1000 * 10L);
        System.out.println("value:" + redisService.get("str"));

    }

}