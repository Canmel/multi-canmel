package com.restful.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.restful.entity.Reimbursement;
import com.restful.entity.SysUser;
import com.restful.entity.WorkFlowInstance;
import com.restful.exception.UnAuthenticationException;
import com.restful.mapper.ReimbursementMapper;
import com.restful.service.ReimbursementService;
import com.restful.service.SysUserService;
import com.restful.service.WorkFlowInstanceService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
public class ReimbursementServiceImpl extends ServiceImpl<ReimbursementMapper, Reimbursement> implements ReimbursementService {

    @Autowired
    private RuntimeService runtimeService;


    /**
     * describe: 插入报销申请
     * creat_user: baily
     * creat_date: 2018/8/19
     **/
    @Override
    public boolean insert(Reimbursement entity) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SysUser currentUser = userService.current(request);
        if (ObjectUtils.isEmpty(currentUser)) {
            throw new UnAuthenticationException();
        }
        entity.setCreator(currentUser.getId());
        return super.insert(entity);
    }

    @Override
    public Page<Reimbursement> selectPage(Page<Reimbursement> page, Wrapper<Reimbursement> wrapper) {
        Page<Reimbursement> reimbursementPage = super.selectPage(page, wrapper);
        Iterator<Reimbursement> i = reimbursementPage.getRecords().iterator();
        while (i.hasNext()){
            Reimbursement reimbursement = i.next();
            EntityWrapper<WorkFlowInstance> workFlowInstanceEntityWrapper = new EntityWrapper<>();
            workFlowInstanceEntityWrapper.eq("business_id", reimbursement.getId());
        }
        return reimbursementPage;
    }

    @Override
    public boolean apply(Reimbursement reimbursement) {
        reimbursement.setStatus(1); // TODO magic number 需要转用枚举类型
        this.updateById(reimbursement);

        // 将业务和流程绑定来
        String busniessKey = reimbursement.getClass().getSimpleName() + reimbursement.getId();
        String definitionKey = reimbursement.getClass().getSimpleName() + "Flow";
        Map<String, Object> map = new HashMap<>();
        map.put("optor", "admin");
        // 启动流程
        runtimeService.startProcessInstanceByKey("Reimbursement1", busniessKey, map);

        // 查询业务流程
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey(busniessKey, "Reimbursement1").list();

        System.out.println(processInstances);
        return false;
    }

    @Autowired
    private SysUserService userService;
}
