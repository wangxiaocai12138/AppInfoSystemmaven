package cn.appsys.dao.datadictionary;

import cn.appsys.pojo.DataDictionary;

import java.util.List;

public interface DataDictionaryMapper {
    /**
     * 获取状态码列表
     * @return
     */
    List<DataDictionary> getStatusList();

    /**
     * 获取所属平台列表
     * @return
     */
    List<DataDictionary> getFlatFormList();
}
