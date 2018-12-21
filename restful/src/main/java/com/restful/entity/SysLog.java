package com.restful.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
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
 * @since 2018-11-20
 */
public class SysLog extends BaseEntity<SysLog> {

    private static final long serialVersionUID = 1L;

    public static final String STRING_SPLIT_CHARTS = "@%@%@";
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 方法名称
     */
    private String method;
    private String params;
    /**
     * 标题
     */
    private String title;
    /**
     * 简述，描述性文字
     */
    private String description;
    /**
     * 创建者
     */
    private Integer operator;
    /**
     * 标签
     */
    private Integer label;
    /**
     * 创建时间
     */
    private String createdAt;

    @TableField(exist = false)
    private SysUser optor;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public SysUser getOptor() {
        return optor;
    }

    public void setOptor(SysUser optor) {
        this.optor = optor;
    }

    public SysLog(String method, String params, String title, String description, Integer operator, Integer label, String createdAt) {
        this.method = method;
        this.params = params;
        this.title = title;
        this.description = description;
        this.operator = operator;
        this.label = label;
        this.createdAt = createdAt;
    }

    public SysLog() {
    }

    @Override
    public String toString() {
        return "SysLog{" +
                ", id=" + id +
                ", method=" + method +
                ", params=" + params +
                ", title=" + title +
                ", description=" + description +
                ", operator=" + operator +
                ", label=" + label +
                ", createdAt=" + createdAt +
                "}";
    }

    public static SysLog str2Obj(String logStr){
        String[] logProps = logStr.split(STRING_SPLIT_CHARTS);
        List<String> ps = CollectionUtils.arrayToList(logProps);
        SysLog sysLog = new SysLog();
        sysLog.setMethod(ps.get(0));
        sysLog.setParams(ps.get(1));
        sysLog.setDescription(ps.get(2));
        sysLog.setOperator(Integer.parseInt(ps.get(3)));
        sysLog.setTitle(ps.get(4));
        sysLog.setCreatedAt(ps.get(5));
        return sysLog;
    }
}
