package com.iauth2.dao;

import com.iauth2.config.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao {
    User selectByUsername(@Param("username") String username);

    User selectByPrimaryKey(Integer id);

    List<String> roleList(@Param("id") Integer id);
}
