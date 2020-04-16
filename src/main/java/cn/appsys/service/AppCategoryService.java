package cn.appsys.service;

import cn.appsys.pojo.AppCategory;

import java.util.List;

public interface AppCategoryService {
    /**
     * 分类列表
     * @param parentId
     * @return
     */
    List<AppCategory> getCategoryNameLsitByParentId(Integer parentId);
}
