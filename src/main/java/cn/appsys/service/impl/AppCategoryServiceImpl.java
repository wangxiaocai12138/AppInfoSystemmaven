package cn.appsys.service.impl;

import cn.appsys.dao.appcategory.AppCategoryMapper;
import cn.appsys.pojo.AppCategory;
import cn.appsys.service.AppCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppCategoryServiceImpl implements AppCategoryService {

    @Resource
    private AppCategoryMapper appCategoryMapper;

    @Override
    public List<AppCategory> getCategoryNameLsitByParentId(Integer parentId) {
        return appCategoryMapper.getCategoryNameLsitByParentId(parentId);
    }
}
