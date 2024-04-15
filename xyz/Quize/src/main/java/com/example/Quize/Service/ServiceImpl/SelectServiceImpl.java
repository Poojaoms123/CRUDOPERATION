package com.example.Quize.Service.ServiceImpl;

import com.example.Quize.Model.SaveRequest.SaveSelectRequest;
import com.example.Quize.Model.Select;
import com.example.Quize.Repository.SelectRepository;
import com.example.Quize.Service.SelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectServiceImpl implements SelectService {
    @Autowired
    private SelectRepository selectRepository;

    @Override
    public Object saveOrUpdateSelect(SaveSelectRequest saveSelectRequest) {
        if (selectRepository.existsById(saveSelectRequest.getSelectId())){
            Select select = selectRepository.findById(saveSelectRequest.getSelectId()).get();
            select.setOptionId(saveSelectRequest.getOptionId());
            select.setQuestionId(saveSelectRequest.getQuestionId());
            select.setUserId(saveSelectRequest.getUserId());
            select.setQuizId(saveSelectRequest.getQuizId());
            selectRepository.save(select);
            return "Updated Sucessfully";
        }else {
            Select select = new Select();
            select.setOptionId(saveSelectRequest.getOptionId());
            select.setQuestionId(saveSelectRequest.getQuestionId());
            select.setUserId(saveSelectRequest.getUserId());
            select.setQuizId(saveSelectRequest.getQuizId());
            selectRepository.save(select);
            return "Saved Sucessfully";
        }
    }
}
