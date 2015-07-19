package test;

import model.UserModel;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import serv.UserServ;
import bean.User;

public class TestBean {
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
        UserModel model = userServ.getUserModel(0);
        User user = model.getUser();
        System.out.println(user);
    }
}
