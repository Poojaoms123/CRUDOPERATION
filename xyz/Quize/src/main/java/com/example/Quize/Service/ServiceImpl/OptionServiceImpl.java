package com.example.Quize.Service.ServiceImpl;

import com.example.Quize.Model.Option;
import com.example.Quize.Model.SaveRequest.SaveOptionRequest;
import com.example.Quize.Repository.OptionRepository;
import com.example.Quize.Service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionServiceImpl implements OptionService {
    @Autowired
    private OptionRepository optionRepository;
    @Override
    public Object saveOrUpdateOption(SaveOptionRequest saveOptionRequest) {
       if (optionRepository.existsById(saveOptionRequest.getOptionId())){
           Option option = optionRepository.findById(saveOptionRequest.getOptionId()).get();
           option.setOptionName(saveOptionRequest.getOptionName());
           option.setQuestionId(saveOptionRequest.getQuestionId());
           optionRepository.save(option);
           return "Updated Successfully";
       }else {
           Option option =new Option();
           option.setOptionName(saveOptionRequest.getOptionName());
           option.setQuestionId(saveOptionRequest.getQuestionId());
           optionRepository.save(option);
           return "Saved Successfully";

       }
    }
}
