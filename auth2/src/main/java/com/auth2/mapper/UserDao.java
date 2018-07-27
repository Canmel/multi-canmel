package com.auth2.mapper;

import com.auth2.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    SysUser selectByUsername(@Param("username") String username);
}
