package controller;

import javax.servlet.http.HttpServletRequest;

import model.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    
    @RequestMapping("/getUser")
    public String getUser(String id, HttpServletRequest request) {
        UserModel model = serv.getUserModel(Integer.parseInt(id));
        request.setAttribute("userModel", model);
        
        return "user";
    }
}
