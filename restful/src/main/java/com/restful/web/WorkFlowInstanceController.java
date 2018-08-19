package com.restful.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.entity.ErrorResult;
import com.core.entity.HttpResult;
import com.core.entity.Result;
import com.restful.entity.WorkFlow;
import com.restful.entity.WorkFlowInstance;
import com.restful.service.WorkFlowInstanceService;
import com.restful.service.WorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  流程实例
 * </p>
 *
 * @author  * 
 *   ┏ ┓   ┏ ┓
 *  ┏┛ ┻━━━┛ ┻┓
 *  ┃         ┃
 *  ┃    ━    ┃
 *  ┃  ┳┛  ┗┳ ┃
 *  ┃         ┃
 *  ┃    ┻    ┃
 *  ┃         ┃
 *  ┗━━┓    ┏━┛
 *     ┃    ┃神兽保佑
 *     ┃    ┃代码无BUG！
 *     ┃    ┗━━━━━━━┓
 *     ┃            ┣┓
 *     ┃            ┏┛
 *     ┗┓┓┏━━━━━━┳┓┏┛
 *      ┃┫┫      ┃┫┫
 *      ┗┻┛      ┗┻┛
 * @since 2018-08-18
 */
@RestController
@RequestMapping("/api/workflow/instance")
public class WorkFlowInstanceController {

    @Autowired
    private WorkFlowInstanceService workFlowInstanceService;

    @Autowired
    private WorkFlowService workFlowService;

    @GetMapping
    public HttpResult index(WorkFlowInstance workFlowInstance, String workFlowName) {
        EntityWrapper<WorkFlow> workFlowEntityWrapper = new EntityWrapper<WorkFlow>();
        List<WorkFlow> workFlowList = new WorkFlow(workFlowName).selectList(null);
        List<Integer> businessIds = new ArrayList<>();
        for (WorkFlow workFlow : workFlowList) {
            businessIds.add(workFlow.getId());
        }
        EntityWrapper<WorkFlowInstance> workFlowInstanceEntityWrapper = new EntityWrapper<WorkFlowInstance>();
        workFlowInstanceEntityWrapper.in("business_id", businessIds);
        Page<WorkFlowInstance> workFlowPage = new Page<WorkFlowInstance>(workFlowInstance.getCurrentPage(), 10);
        return Result.OK(workFlowInstanceService.selectPage(workFlowPage, workFlowInstanceEntityWrapper));
    }

    @PostMapping
    public HttpResult create(@RequestBody WorkFlowInstance workFlowInstance) {
        if (workFlowInstanceService.insert(workFlowInstance)) {
            return Result.OK("发起工作流实例成功");
        } else {
            return ErrorResult.UNAUTHORIZED();
        }

    }

}

