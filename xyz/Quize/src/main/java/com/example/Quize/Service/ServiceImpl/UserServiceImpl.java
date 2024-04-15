package com.example.Quize.Service.ServiceImpl;

import com.example.Quize.Model.SaveRequest.SaveUserRequest;
import com.example.Quize.Model.User;
import com.example.Quize.Repository.UserRepository;
import com.example.Quize.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public Object saveOrUpdate(SaveUserRequest saveUserRequest) {
       if (userRepository.existsById(saveUserRequest.getUserId())){
           User user = userRepository.findById(saveUserRequest.getUserId()).get();
           user.setUserName(saveUserRequest.getUserName());
           user.setUserEmail(saveUserRequest.getUserEmail());
           user.setUserMobileNo(saveUserRequest.getUserMobileNo());
           user.setUserPassword(saveUserRequest.getUserPassword());
           user.setUserIsDeleted(false);
           user.setUserIsActive(true);
           userRepository.save(user);
           return "Updated Successfuly";
       }else {
           User user = new User();
           user.setUserName(saveUserRequest.getUserName());
           user.setUserEmail(saveUserRequest.getUserEmail());
           user.setUserMobileNo(saveUserRequest.getUserMobileNo());
           user.setUserPassword(saveUserRequest.getUserPassword());
           user.setUserIsDeleted(false);
           user.setUserIsActive(true);
           userRepository.save(user);
           return "Saved Successfuly";
       }
    }
}
