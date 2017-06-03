package com.myapp.core.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapp.core.entity.UserInfo;
import com.myapp.core.service.base.BaseInterfaceService;

@Service("userService")
@Transactional
public class UserService extends BaseInterfaceService<UserInfo> {
	
}
