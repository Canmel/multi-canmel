package com.restful.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.entity.ErrorResult;
import com.core.entity.HttpResult;
import com.core.entity.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.config.activiti.ActivitiForm;
import com.restful.entity.Reimbursement;
import com.restful.entity.WorkFlow;
import com.restful.entity.enums.ReimbursementStatus;
import com.restful.service.ReimbursementService;
import com.restful.service.WorkFlowService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
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
@RequestMapping("/api/reimbursement")
public class ReimbursementController extends BaseController {

    @Autowired
    private ReimbursementService reimbursementService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WorkFlowService workFlowService;

    @GetMapping()
    public HttpResult index(Reimbursement reimbursement) {
        Page<Reimbursement> reimbursementPage = new Page<Reimbursement>(reimbursement.getCurrentPage(), 10);
        EntityWrapper<Reimbursement> reimbursementEntityWrapper = new EntityWrapper<Reimbursement>();
        reimbursementEntityWrapper.like("description", reimbursement.getDescription());
        Page page = reimbursementService.selectPage(reimbursementPage, reimbursementEntityWrapper);
        page = reimbursementService.recordFlowStatus(page);
        return Result.OK(page);
    }

    @PostMapping
    public HttpResult create(@RequestBody Reimbursement reimbursement) {
        if (reimbursementService.insert(reimbursement)) {
            return Result.OK("新建报销申请成功");
        } else {
            return ErrorResult.UNAUTHORIZED();
        }
    }

    @GetMapping("/{id}")
    public HttpResult details(@PathVariable Integer id) {
        return Result.OK(reimbursementService.selectById(id));
    }

    @PutMapping("/{id}")
    public HttpResult edit(@RequestBody Reimbursement reimbursement, @PathVariable Integer id){
        reimbursement.setId(id);
        if (reimbursementService.updateById(reimbursement)) {
            return Result.OK("修改报销申请成功!");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable Integer id){
        if (reimbursementService.deleteById(id)) {
            return Result.OK("删除报销申请成功!");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    @GetMapping("/apply/{id}")
    public Result apply(@PathVariable Integer id, Integer flowId){
        Reimbursement reimbursement = reimbursementService.selectById(id);
        reimbursementService.apply(reimbursement, flowId);
        return null;
    }

    /**
     * 通过
     * @param id
     * @param params
     * @return
     */
    @GetMapping("/task/pass/{id}")
    public Result pass(@PathVariable String id, ActivitiForm params) {
        Map paramMap = objectMapper.convertValue(params, HashMap.class);
        boolean isPass = workFlowService.passProcess(id, paramMap);
        return Result.OK("审批成功");
    }

    /**
     * 驳回
     * @param id
     * @param params
     * @return
     */
    @GetMapping("/task/back/{id}")
    public Result back(@PathVariable String id, ActivitiForm params) {
        Map paramMap = objectMapper.convertValue(params, HashMap.class);
        boolean isBack = workFlowService.backProcess(id, null, paramMap, () -> {
            Reimbursement reimbursement = reimbursementService.selectById(params.getBusinessId());
            reimbursement.setStatus(ReimbursementStatus.APPLY_FAILD.getValue());
            reimbursementService.updateById(reimbursement);
        });
        return Result.OK("驳回成功");
    }
}

