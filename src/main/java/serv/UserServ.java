package serv;

import java.util.ArrayList;
import java.util.List;

import model.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bean.User;
import dao.UserMapper;

@Service
public class UserServ {
    
    @Autowired
    private UserMapper userMapper;
    
    public UserModel getUserModel(int id) {
        User user = userMapper.selectByPrimaryKey(id);
        
        UserModel model = new UserModel();
        model.setUser(user);
        return model;
    }
    
    public List<UserModel> getAllUserModel() {
        List<User> users = userMapper.selectAll();
        
        List<UserModel> userModels = new ArrayList<UserModel>();
        UserModel userModel = null;
        for (User user : users) {
            userModel = new UserModel();
            userModel.setUser(user);
            userModels.add(userModel);
        }
        
        return userModels;
    }
}
