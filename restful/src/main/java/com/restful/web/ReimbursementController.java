package com.restful.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.entity.ErrorResult;
import com.core.entity.HttpResult;
import com.core.entity.Result;
import com.restful.entity.Reimbursement;
import com.restful.service.ReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping()
    public HttpResult index(Reimbursement reimbursement) {
        Page<Reimbursement> reimbursementPage = new Page<Reimbursement>(reimbursement.getCurrentPage(), 10);
        EntityWrapper<Reimbursement> reimbursementEntityWrapper = new EntityWrapper<Reimbursement>();
        reimbursementEntityWrapper.like("description", reimbursement.getDescription());
        return Result.OK(reimbursementService.selectPage(reimbursementPage, reimbursementEntityWrapper));
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
}

