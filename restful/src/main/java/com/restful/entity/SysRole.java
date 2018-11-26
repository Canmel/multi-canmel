package com.restful.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

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
 * @since 2018-08-12
 */
@ApiModel(value = "角色", description = "角色")
public class SysRole extends BaseEntity<SysRole> {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    @ApiModelProperty(value = "菜单集合", notes = "菜单集合")
    private List<SysMenu> menus;

    @TableField(exist = false)
    @ApiModelProperty(value = "菜单ids", notes = "菜单ids")
    private List<Integer> menuIds;

    @ApiModelProperty(value = "主键", notes = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "角色名称", notes = "角色名称")
    private String rolename;

    @ApiModelProperty(value = "状态", notes = "状态")
    private Integer status;

    @ApiModelProperty(value = "简短的描述", notes = "简短的描述")
    private String description;

    @ApiModelProperty(value = "是否删除", notes = "是否删除")
    @TableLogic
    private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SysRole addId(Integer id) {
        this.id = id;
        return this;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public List<SysMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<SysMenu> menus) {
        this.menus = menus;
    }

    public List<Integer> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Integer> menuIds) {
        this.menuIds = menuIds;
    }

    @Override
    public String toString() {
        return "SysRole{" +
                ", id=" + id +
                ", rolename=" + rolename +
                ", status=" + status +
                ", description=" + description +
                "}";
    }
}
