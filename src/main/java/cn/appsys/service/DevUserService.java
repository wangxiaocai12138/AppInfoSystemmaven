package cn.appsys.service;

import cn.appsys.pojo.DevUser;

public interface DevUserService {
    /**
     * 开发者登录
     * @param devCode
     * @param devPassword
     * @return
     */
    DevUser getLoginUser(String devCode, String devPassword);

}
