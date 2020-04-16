package cn.appsys.dao.appversion;

import cn.appsys.pojo.AppVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppVersionMapper {
    /**
     * 历史版本列表
     * @param id
     * @return
     */
    List<AppVersion> getAppVersionList(@Param("id") Integer id);

    int addAppVersion(AppVersion appVersion);

    /**
     * 查询版本信息
     * @param vid
     * @return
     */
    AppVersion getAppVersion(@Param("id") Integer vid);

    /**
     * 删除app
     * @param id
     * @return
     */
    Integer delapp(@Param("id") Integer id);

    /**
     * 修改版本信息
     * @param appVersion
     * @return
     */
    Integer updAppVersion(AppVersion appVersion);
}
