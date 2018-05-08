package com.idata365.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.Users;
import com.idata365.app.mapper.UsersMapper;

@Service
@Transactional
public class UsersService {

	@Autowired
	private UsersMapper usersMapper;

	public List<Users> getUsers() {
		List<Users> users = usersMapper.selectByExample(null);
		return users;
	}

	public Users getUser(Long usersid) {
		Users user = usersMapper.selectByPrimaryKey(usersid);
		return user;
	}

	public void save(Users user) {
		usersMapper.insert(user);
	}

	public void update(Users user) {
		usersMapper.updateByPrimaryKey(user);
	}

	public void delete(Long id) {
		usersMapper.deleteByPrimaryKey(id);
	}

}
