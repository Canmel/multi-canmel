package com.restful.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author *
 * ┏ ┓   ┏ ┓
 * ┏┛ ┻━━━┛ ┻┓
 * ┃         ┃
 * ┃    ━    ┃
 * ┃  ┳┛  ┗┳ ┃
 * ┃         ┃
 * ┃    ┻    ┃
 * ┃         ┃
 * ┗━━┓    ┏━┛
 * ┃    ┃神兽保佑
 * ┃    ┃代码无BUG！
 * ┃    ┗━━━━━━━┓
 * ┃            ┣┓
 * ┃            ┏┛
 * ┗┓┓┏━━━━━━┳┓┏┛
 * ┃┫┫      ┃┫┫
 * ┗┻┛      ┗┻┛
 * @since 2018-11-15
 */
@ApiModel(value = "系统用户实体类", description = "系统用户实体类")
public class SysUser extends BaseEntity<WorkFlow> {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键 ID", name = "id")
    private Integer id;
    /**
     * 用户名，用户登录使用
     */
    @ApiModelProperty(value = "用户名称", name = "username")
    private String username;
    /**
     * 密码 用户登录使用
     */
    @ApiModelProperty(value = "密码 用户登录使用", name = "password")
    private String password;
    /**
     * 昵称，现在在页面上的友情提示信息
     */
    @ApiModelProperty(value = "昵称，现在在页面上的友情提示信息", name = "nickname")
    private String nickname;
    /**
     * 邮箱，发送邮件信息
     */
    @ApiModelProperty(value = "邮箱，发送邮件信息", name = "email")
    private String email;
    /**
     * 移动电话，联系方式
     */
    @ApiModelProperty(value = "移动电话，联系方式", name = "mobile")
    private String mobile;
    /**
     * 简短的备注
     */
    @ApiModelProperty(value = "简短的备注", name = "remarke")
    private String remarke;
    /**
     * 状态
     */
    private Integer status;
    private Integer isDel;
    private Date createdAt;
    /**
     * 地址坐标
     */
    @ApiModelProperty(value = "地址坐标", name = "address")
    private String address;

    @TableField(exist = false)
    private List roleIds;

    @TableField(exist = false)
    private List<SysRole> sysRoles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemarke() {
        return remarke;
    }

    public void setRemarke(String remarke) {
        this.remarke = remarke;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List roleIds) {
        this.roleIds = roleIds;
    }

    public List<SysRole> getSysRoles() {
        return sysRoles;
    }

    public void setSysRoles(List<SysRole> sysRoles) {
        this.sysRoles = sysRoles;
    }

    public SysUser addId(Integer id){
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                ", id=" + id +
                ", username=" + username +
                ", password=" + password +
                ", nickname=" + nickname +
                ", email=" + email +
                ", mobile=" + mobile +
                ", remarke=" + remarke +
                ", status=" + status +
                ", isDel=" + isDel +
                ", createdAt=" + createdAt +
                ", address=" + address +
                "}";
    }


}
