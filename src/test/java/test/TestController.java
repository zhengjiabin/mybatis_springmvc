package test;

import model.UserModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import controller.UserController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring_mybatis.xml", "classpath:spring_mvc.xml"})
public class TestController {
    
    @Autowired
    private UserController userController;
    
    @Test
    public void testController() {
        UserModel user = userController.getUser("0");
        System.out.println(user);
    }
}
