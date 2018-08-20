package com.restful.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.core.entity.HttpResult;
import com.core.entity.Result;
import com.restful.entity.WorkFlow;
import com.restful.entity.enums.MenuLevel;
import com.restful.entity.enums.WorkFlowPublish;
import com.restful.entity.enums.WorkFlowType;
import com.restful.service.WorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *
 *  前端控制器
 *
 * @author baily
 * 描述:
 * ${DESCRIPTION}
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
 * @since 2018年08月15日
 */
@RestController
@RequestMapping("/api/options")
public class OptionsController {

    @Autowired
    private WorkFlowService workFlowService;

    /**
     * describe: 菜单级别
     * creat_user: baily
     * creat_date: 2018/8/19
     **/
    @GetMapping("/menus/levels")
    public HttpResult menuLevel(HttpServletRequest request) {
        return Result.OK(MenuLevel.all());
    }

    /**
     * describe: 工作流类型
     * creat_user: baily
     * creat_date: 2018/8/19
     **/
    @GetMapping("/workflow/typies")
    public HttpResult workflowTypies() {
        return Result.OK(WorkFlowType.all());
    }

    /**
     * describe: 工作流类型名称，查找所有工作流
     * creat_user: baily
     * creat_date: 2018/8/19
     **/
    @GetMapping("/workflow")
    public HttpResult workflowByType(String type) {
        WorkFlowType workFlowType = WorkFlowType.valueOf(type);
        EntityWrapper<WorkFlow> workFlowEntityWrapper = new EntityWrapper<>();
        workFlowEntityWrapper.eq("flowType", workFlowType.getValue());
        workFlowEntityWrapper.eq("is_public", WorkFlowPublish.PUBLISHED.getValue());
        return Result.OK(workFlowService.selectList(workFlowEntityWrapper));
    }

}
