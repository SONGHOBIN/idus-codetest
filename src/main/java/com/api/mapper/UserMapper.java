package com.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.api.entity.UserEntity;

@Mapper
public interface UserMapper {
	List<UserEntity> selectUsers(Map<String, Object> params);
}
