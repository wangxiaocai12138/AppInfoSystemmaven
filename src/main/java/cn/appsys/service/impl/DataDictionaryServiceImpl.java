package cn.appsys.service.impl;

import cn.appsys.dao.datadictionary.DataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.DataDictionaryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {
    @Resource
    private DataDictionaryMapper dataDictionaryMapper;

    @Override
    public List<DataDictionary> getStatusList() {
        return dataDictionaryMapper.getStatusList();
    }

    @Override
    public List<DataDictionary> getFlatFormList() {
        return dataDictionaryMapper.getFlatFormList();
    }
}
