package com.restful.service.impl;


import com.restful.service.SystemFlowService;
import org.springframework.stereotype.Service;

@Service
public class SystemFlowServiceImpl implements SystemFlowService {
    @Override
    public void dosth() {
        System.out.println("任务已经执行------------------------------------------------------------------");
    }
}
