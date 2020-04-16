package cn.appsys.service;

import cn.appsys.pojo.AppVersion;

import java.util.List;

public interface AppVersionService {
    /**
     * 历史版本列表
     * @param id
     * @return
     */
    List<AppVersion> getAppVersionList(Integer id);

    /**
     * 添加版本
     * @param appVersion
     * @return
     */
    boolean addAppVersion(AppVersion appVersion);

    /**
     * 根据id查询版本信息
     * @param vid
     * @return
     */
    AppVersion getAppVersion(Integer vid);

    /**
     * 删除app
     * @param id
     * @return
     */
    boolean delapp(Integer id);

    /**
     * 修改版本信息
     * @param appVersion
     * @return
     */
    boolean updAppVersion(AppVersion appVersion);
}
