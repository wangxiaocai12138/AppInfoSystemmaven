package cn.appsys.service;

import cn.appsys.pojo.BackendUser;

public interface BackendUserService {
    /**
     * 登录
     * @param userCode
     * @param userPassword
     * @return
     */
    BackendUser getUser(String userCode, String userPassword);
}
