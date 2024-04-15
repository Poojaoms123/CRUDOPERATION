package com.example.Quize.Model.SaveRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SaveOptionRequest {
    private Long optionId;
    private String optionName;
    private Long questionId;
}
