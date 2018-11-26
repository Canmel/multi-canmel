package com.restful.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
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
 * @since 2018-08-15
 */
@ApiModel(value = "菜单实体类", description = "菜单实体类")
public class SysMenu extends BaseEntity<SysMenu> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(name="id", notes = "主键")
    private Integer id;

    @ApiModelProperty(name = "菜单名称", notes = "菜单名称", example = "admin")
    private String menuname;

    @ApiModelProperty(name = "简短的描述文字", notes = "简短的描述文字", example = "简短的描述文字")
    private String description;

    @ApiModelProperty(name="上级菜单", notes = "上级菜单", example = "1")
    private Integer pid;

    @ApiModelProperty(name = "简单的排序字段", notes = "简单的排序字段", example = "1")
    private Integer mindex;

    @ApiModelProperty(name = "菜单等级", notes = "菜单等级", example = "1")
    private Integer level;

    @ApiModelProperty(name = "状态", notes = "状态", example = "1")
    private Integer status;

    @ApiModelProperty(name = "目录地址", notes = "目录地址", example = "/app/users")
    private String address;

    @ApiModelProperty(name = "目录图标", notes = "目录图标", example = "fa fa-user-o")
    private String icon;
    /**
     * 逻辑删除 1是 0 否
     */
    @TableLogic
    @ApiModelProperty(name = "逻辑删除", notes = "逻辑删除 1是 0 否", example = "1")
    private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getMindex() {
        return mindex;
    }

    public void setMindex(Integer mindex) {
        this.mindex = mindex;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "SysMenu{" +
                ", id=" + id +
                ", menuname=" + menuname +
                ", description=" + description +
                ", pid=" + pid +
                ", mindex=" + mindex +
                ", level=" + level +
                ", status=" + status +
                ", address=" + address +
                ", isDel=" + isDel +
                "}";
    }

    public SysMenu addId(Integer id){
        this.id = id;
        return this;
    }
}
