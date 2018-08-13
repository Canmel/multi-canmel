package com.restful.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;

/**
 * <p>
 * 实体类基类，继承model,得到mybatis-plus组件功能
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
 * @since 2018-08-12
 */
public class BaseEntity<T extends Model> extends Model<T> {

    @TableField(exist = false)
    public Integer currentPage = 1;

    @TableField(exist = false)
    public Integer tsize;

    private Integer id;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTsize() {
        return tsize;
    }

    public void setTsize(Integer tsize) {
        this.tsize = tsize;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BaseEntity(Integer id) {
        this.id = id;
    }

    public BaseEntity() {
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
