package test;

import java.util.List;

import model.UserModel;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import serv.UserServ;
import bean.User;

public class TestBean {
    
    private static final Logger logger = Logger.getLogger(TestBean.class);
    
    private ApplicationContext ac;
    
    private UserServ userServ;
    
    @Before
    public void before() {
        String springConfig = "spring.xml";
        String springMybatisConfig = "spring_mybatis.xml";
        ac = new ClassPathXmlApplicationContext(springConfig, springMybatisConfig);
        userServ = ac.getBean(UserServ.class);
    }
    
    @Test
    public void testGetBean() {
        UserModel model = userServ.getUserModel(1);
        User user = model.getUser();
        logger.info(user);
    }
    
    @Test
    public void testSelectAll() {
        List<UserModel> allUserModel = userServ.getAllUserModel();
        logger.info(allUserModel);
    }
}
