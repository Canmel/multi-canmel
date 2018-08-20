package com.restful.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.restful.entity.WorkFlow;
import com.restful.entity.WorkFlowInstance;
import com.restful.exception.WorkFlowInstanceAlreadyExistException;
import com.restful.mapper.WorkFlowInstanceMapper;
import com.restful.service.WorkFlowInstanceService;
import com.restful.service.WorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 *  服务实现类
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
@Service
public class WorkFlowInstanceServiceImpl extends ServiceImpl<WorkFlowInstanceMapper, WorkFlowInstance> implements WorkFlowInstanceService {

    @Autowired
    private WorkFlowService workFlowService;

    /**
     * describe: 重写报销申请工作流实例
     * creat_user: baily
     * creat_date: 2018/8/19
     **/
    @Override
    public boolean insert(WorkFlowInstance entity) {
        EntityWrapper<WorkFlowInstance> workFlowInstanceEntityWrapper = new EntityWrapper<>();
        workFlowInstanceEntityWrapper.eq("business_id", entity.getBusinessId());
        WorkFlowInstance workFlowInstance = this.selectOne(workFlowInstanceEntityWrapper);

        if (!ObjectUtils.isEmpty(workFlowInstance)) {
            throw new WorkFlowInstanceAlreadyExistException();
        }

        WorkFlow workFlow = workFlowService.selectById(entity.getWorkFlowId());
        entity.setFlow(workFlow.getFlow());

        entity.updateInstance();
        return super.insert(entity);
    }
}
