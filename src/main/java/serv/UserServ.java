package serv;

import model.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bean.User;
import dao.UserMapper;

@Service
public class UserServ {
    
    @Autowired
    private UserMapper userMapper;
    
    public UserModel getUserModel() {
        User user = userMapper.selectByPrimaryKey(1);
        
        UserModel model = new UserModel();
        model.setUser(user);
        return model;
    }
}
