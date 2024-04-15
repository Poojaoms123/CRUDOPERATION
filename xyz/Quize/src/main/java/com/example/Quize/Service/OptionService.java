package com.example.Quize.Service;

import com.example.Quize.Model.SaveRequest.SaveOptionRequest;

public interface OptionService {
    Object saveOrUpdateOption(SaveOptionRequest saveOptionRequest);
}
