package com.anervea.employee.service.impl;



import com.anervea.employee.service.ICorporateEmployeeService;
import com.anervea.employee.utility.FileOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;




@Service
public class CorporateEmployeeService implements ICorporateEmployeeService {

    @Value("${fileBaseUrl}")
    private String fileBaseUrl;
    @Autowired
    private FileOperation ubuntuServerFileOperation;

}













