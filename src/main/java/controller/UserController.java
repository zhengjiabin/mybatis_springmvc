package controller;

import javax.servlet.http.HttpServletRequest;

import model.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import serv.UserServ;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserServ serv;
    
    public UserModel getUser(String id) {
        UserModel model = serv.getUserModel(Integer.parseInt(id));
        return model;
    }
    
    /**
     * http://localhost:8080/mybatis_springmvc/user/getUser1.do?id=1
     * 
     * @param id
     * @param request
     * @return
     */
    
    public String getUser1(String id, HttpServletRequest request) {
        UserModel model = serv.getUserModel(Integer.parseInt(id));
        request.setAttribute("userModel", model);
        
        return "user";
    }
    
    /**
     * http://localhost:8080/mybatis_springmvc/user/getUser2/1.do
     * 
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/getUser/{id}")
    public String getUser2(@PathVariable
    String id, HttpServletRequest request) {
        UserModel model = serv.getUserModel(Integer.parseInt(id));
        request.setAttribute("userModel", model);
        
        return "user";
    }
    
    /**
     * http://localhost:8080/mybatis_springmvc/user/1/getUser.do
     * 
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/{id}/getUser")
    public String getUser(@PathVariable
    String id, HttpServletRequest request) {
        UserModel model = serv.getUserModel(Integer.parseInt(id));
        request.setAttribute("userModel", model);
        
        return "user";
    }
}
