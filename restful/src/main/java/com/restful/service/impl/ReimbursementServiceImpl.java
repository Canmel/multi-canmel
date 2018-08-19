package com.restful.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.restful.entity.Reimbursement;
import com.restful.entity.SysUser;
import com.restful.exception.UnAuthenticationException;
import com.restful.mapper.ReimbursementMapper;
import com.restful.service.ReimbursementService;
import com.restful.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    private SysUserService userService;
}
