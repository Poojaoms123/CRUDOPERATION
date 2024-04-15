package com.example.Quize.Controller;

import com.example.Quize.Model.Response.EntityResponse;
import com.example.Quize.Model.SaveRequest.SaveOptionRequest;
import com.example.Quize.Service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/option")
public class OptionController {
    @Autowired
    private OptionService optionService;

    @PostMapping("/saveOrUpdateOption")
    public ResponseEntity<?>saveOrUpdateOption(@RequestBody SaveOptionRequest saveOptionRequest){
        try {
            return new ResponseEntity<>(new EntityResponse(optionService.saveOrUpdateOption(saveOptionRequest),0), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }
}
