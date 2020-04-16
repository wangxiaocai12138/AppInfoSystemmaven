package cn.appsys.dao.appinfo;

import cn.appsys.pojo.AppInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppInfoMapper {

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
    List<AppInfo> getAppInfoList(@Param("devId") Integer devId, @Param("softwareName") String querySoftwareName,
                                 @Param("status") Integer queryStatus,
                                 @Param("flatformId") Integer queryFlatformId,
                                 @Param("categoryLevel1") Integer queryCategoryLevel1,
                                 @Param("categoryLevel2") Integer queryCategoryLevel2,
                                 @Param("categoryLevel3") Integer queryCategoryLevel3,
                                 @Param("index") int index, @Param("pageSize") Integer pageSize);

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
    int getCount(@Param("devId") Integer devId, @Param("softwareName") String querySoftwareName,
                 @Param("status") Integer queryStatus,
                 @Param("flatformId") Integer queryFlatformId,
                 @Param("categoryLevel1") Integer queryCategoryLevel1,
                 @Param("categoryLevel2") Integer queryCategoryLevel2,
                 @Param("categoryLevel3") Integer queryCategoryLevel3);

    /**
     * 验证App名称是否重名
     * @param apkName
     * @param devId
     * @return
     */
    AppInfo apkexist(@Param("apkName") String apkName,
                     @Param("devId") Integer devId);

    /**
     * 添加
     * @param appInfo
     * @return
     */
    Integer appadd(AppInfo appInfo);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer delapp(@Param("id") Integer id);

    /**
     * 查看
     * @param id
     * @return
     */
    AppInfo getAppInfo(@Param("id") Integer id);

    /**
     * 删除logo图片
     * @param id
     * @return
     */
    Integer delLogo(@Param("id") Integer id);

    /**
     * 修改app信息
     * @param appInfo
     * @return
     */
    Integer updateAppinfo(AppInfo appInfo);

    /**
     * 修改状态
     * @param appId
     * @param status
     * @return
     */
    Integer updateAppStatus(@Param("id") Integer appId,
                            @Param("status") Integer status);
}
