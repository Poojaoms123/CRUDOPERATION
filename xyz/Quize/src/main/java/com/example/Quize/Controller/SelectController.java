package com.example.Quize.Controller;

import com.example.Quize.Model.Response.EntityResponse;
import com.example.Quize.Model.SaveRequest.SaveSelectRequest;
import com.example.Quize.Service.SelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/select")
public class SelectController {
    @Autowired
    private SelectService selectService;

    @PostMapping("/saveOrUpdateSelect")
    public ResponseEntity<?>saveOrUpdateSelect(@RequestBody SaveSelectRequest saveSelectRequest){
        try {
            return new ResponseEntity<>(new EntityResponse(selectService.saveOrUpdateSelect(saveSelectRequest),0),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1), HttpStatus.OK);
        }
    }
}
