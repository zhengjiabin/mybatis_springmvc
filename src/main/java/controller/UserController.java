package controller;

import model.UserModel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import serv.UserServ;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @RequestMapping("/getUser")
    public UserModel getUser() {
        UserServ serv = new UserServ();
        UserModel model = serv.getUserModel();
        return model;
    }
}
