package com.boot.user.service.impl;

import com.boot.user.entity.User;
import com.boot.user.mapper.UserMapper;
import com.boot.user.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
