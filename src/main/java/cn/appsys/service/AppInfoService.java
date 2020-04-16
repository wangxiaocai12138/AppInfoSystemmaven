package cn.appsys.service;

import cn.appsys.pojo.AppInfo;

import java.util.List;

public interface AppInfoService {
    /**
     * app信息列表
     * @return
     * @param devId
     * @param querySoftwareName
     * @param queryStatus
     * @param queryFlatformId
     * @param queryCategoryLevel1
     * @param queryCategoryLevel2
     * @param queryCategoryLevel3
     * @param index
     * @param pageSize
     */
    List<AppInfo> getAppInfoList(Integer devId, String querySoftwareName, Integer queryStatus, Integer queryFlatformId, Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3, int index, Integer pageSize);

    /**
     * 查询总记录数
     * @return
     * @param devId
     * @param querySoftwareName
     * @param queryStatus
     * @param queryFlatformId
     * @param queryCategoryLevel1
     * @param queryCategoryLevel2
     * @param queryCategoryLevel3
     */
    int getCount(Integer devId, String querySoftwareName, Integer queryStatus, Integer queryFlatformId, Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3);

    /**
     * 验证App名称是否重名
     * @param apkName
     * @param devId
     * @return
     */
    boolean apkexist(String apkName, Integer devId);

    /**
     * 添加
     * @param appInfo
     * @return
     */
    boolean appadd(AppInfo appInfo);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean delapp(Integer id);

    /**
     * 查看
     * @param id
     * @return
     */
    AppInfo getAppInfoById(Integer id);

    /**
     * 删除logo
     * @param id
     * @return
     */
    boolean delLogo(Integer id);

    /**
     * 修改app信息
     * @param appInfo
     * @return
     */
    boolean updateAppinfo(AppInfo appInfo);

    /**
     * 修改状态
     * @param appId
     * @param status
     * @return
     */
    boolean updateAppStatus(Integer appId, Integer status);
}
