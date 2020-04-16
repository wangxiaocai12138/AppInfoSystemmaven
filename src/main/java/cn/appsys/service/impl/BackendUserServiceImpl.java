package cn.appsys.service.impl;

import cn.appsys.dao.backenduser.BackendUserMapper;
import cn.appsys.pojo.BackendUser;
import cn.appsys.service.BackendUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BackendUserServiceImpl implements BackendUserService {
    @Resource
    private BackendUserMapper backendUserMapper;
    @Override
    public BackendUser getUser(String userCode, String userPassword) {
        return backendUserMapper.getUser(userCode,userPassword);
    }
}
