package com.restful.service;

import com.baomidou.mybatisplus.service.IService;
import com.restful.entity.BaseEntity;
import com.restful.entity.WorkFlow;

import java.io.InputStream;
import java.util.Map;

/**
 * <p>
 *  服务类
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
 * @since 2018-08-17
 */
public interface WorkFlowService extends IService<WorkFlow> {

    boolean publish(Integer id);

    boolean startProcess(BaseEntity baseEntity, WorkFlow workFlow, Map params);

    InputStream traceProcessImage(String taskId);

    boolean passProcess(String taskId, Map<String, Object> variables);

    boolean backProcess(String taskId, String activityId, Map<String, Object> variables);
}
