package cn.appsys.service.impl;

import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.AppVersion;
import cn.appsys.service.AppVersionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppVersionServiceImpl implements AppVersionService {
    @Resource
    private AppVersionMapper appVersionMapper;
    @Override
    public List<AppVersion> getAppVersionList(Integer id) {
        return appVersionMapper.getAppVersionList(id);
    }

    @Override
    public boolean addAppVersion(AppVersion appVersion) {
        if (appVersionMapper.addAppVersion(appVersion)>0)
            return true;
        return false;
    }

    @Override
    public AppVersion getAppVersion(Integer vid) {
        return appVersionMapper.getAppVersion(vid);
    }

    @Override
    public boolean delapp(Integer id) {
        if (appVersionMapper.delapp(id)>0)
            return true;
        return false;
    }

    @Override
    public boolean updAppVersion(AppVersion appVersion) {
        if (appVersionMapper.updAppVersion(appVersion)>0)
            return true;
        return false;
    }
}
