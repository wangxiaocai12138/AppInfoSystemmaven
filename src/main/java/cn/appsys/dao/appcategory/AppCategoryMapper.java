package cn.appsys.dao.appcategory;

import cn.appsys.pojo.AppCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppCategoryMapper {

    /**
     * 分类列表
     * @param parentId
     * @return
     */
    List<AppCategory> getCategoryNameLsitByParentId(@Param("parentId") Integer parentId);

}
