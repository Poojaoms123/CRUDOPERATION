package com.example.Quize.Service;

import com.example.Quize.Model.SaveRequest.SaveUserRequest;

public interface UserService {
    Object saveOrUpdate(SaveUserRequest saveUserRequest);
}
