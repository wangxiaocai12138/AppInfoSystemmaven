package cn.appsys.service.impl;

import cn.appsys.dao.devuser.DevUserMapper;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.DevUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DevUserServiceImpl implements DevUserService {
    @Resource
    private DevUserMapper devUserMapper;
    @Override
    public DevUser getLoginUser(String devCode, String devPassword) {
        return devUserMapper.getLoginUser(devCode,devPassword);
    }
}
