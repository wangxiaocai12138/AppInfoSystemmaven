package cn.appsys.service.impl;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.service.AppInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppInfoServiceImpl implements AppInfoService {
    @Resource
    private AppInfoMapper appInfoMapper;
    @Override
    public List<AppInfo> getAppInfoList(Integer devId, String querySoftwareName, Integer queryStatus, Integer queryFlatformId, Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3, int index, Integer pageSize) {
        return appInfoMapper.getAppInfoList(devId,querySoftwareName,queryStatus,queryFlatformId,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3,index,pageSize);
    }

    @Override
    public int getCount(Integer devId, String querySoftwareName, Integer queryStatus, Integer queryFlatformId, Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3) {
        return appInfoMapper.getCount(devId,querySoftwareName,queryStatus,queryFlatformId,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3);
    }

    @Override
    public boolean apkexist(String apkName, Integer devId) {
        if(appInfoMapper.apkexist(apkName,devId)!=null){
            return true;
        }
        return false;
    }

    @Override
    public boolean appadd(AppInfo appInfo) {
        if(appInfoMapper.appadd(appInfo)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean delapp(Integer id) {
        if(appInfoMapper.delapp(id)>0){
            return true;
        }
        return false;
    }

    @Override
    public AppInfo getAppInfoById(Integer id) {
        return appInfoMapper.getAppInfo(id);
    }

    @Override
    public boolean delLogo(Integer id) {
        if(appInfoMapper.delLogo(id)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateAppinfo(AppInfo appInfo) {
        if(appInfoMapper.updateAppinfo(appInfo)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateAppStatus(Integer appId, Integer status) {
        if(appInfoMapper.updateAppStatus(appId,status)>0){
            return true;
        }
        return false;
    }

}
