package com.example.Quize.Controller;

import com.example.Quize.Model.Response.EntityResponse;
import com.example.Quize.Model.SaveRequest.SaveUserRequest;
import com.example.Quize.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    @GetMapping("/firstapi")
    public String firstapi(){
        return "working";
    }

    @PostMapping("/saveOrUpdateUser")
    public ResponseEntity<?>saveOrUpdate(@RequestBody SaveUserRequest saveUserRequest){
        try{
            return new ResponseEntity<>(new EntityResponse(userService.saveOrUpdate(saveUserRequest),0), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }

}
