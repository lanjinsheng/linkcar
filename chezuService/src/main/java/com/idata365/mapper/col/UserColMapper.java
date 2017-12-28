package com.idata365.mapper.col;

import java.util.List;

import com.idata365.entity.UserEntity;

public interface UserColMapper {

    List<UserEntity> getAll();

    UserEntity getOne(Long id);

    void insert(UserEntity user);

    void update(UserEntity user);

    void delete(Long id);

}
