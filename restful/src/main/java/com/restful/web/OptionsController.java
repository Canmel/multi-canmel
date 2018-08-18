package com.restful.web;

import com.core.entity.HttpResult;
import com.core.entity.Result;
import com.restful.entity.enums.MenuLevel;
import com.restful.entity.enums.WorkFlowType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/menus/levels")
    public HttpResult menuLevel(HttpServletRequest request) {
        return Result.OK(MenuLevel.all());
    }

    @GetMapping("/workflow/typies")
    public HttpResult workflowTypies() {
        return Result.OK(WorkFlowType.all());
    }
}
