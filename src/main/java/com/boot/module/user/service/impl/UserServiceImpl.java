package com.boot.module.user.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.boot.module.user.entity.User;
import com.boot.module.user.mapper.UserMapper;
import com.boot.module.user.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lwd
 * @since 2018-03-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
	
}
