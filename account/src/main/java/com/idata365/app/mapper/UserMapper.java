package com.idata365.app.mapper;

import java.util.List;

import com.idata365.app.entity.UserEntity;

public interface UserMapper {

    List<UserEntity> getAll();

    UserEntity getOne(Long id);

    void insert(UserEntity user);

    void update(UserEntity user);

    void delete(Long id);

}
