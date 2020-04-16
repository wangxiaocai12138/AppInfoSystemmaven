package cn.appsys.controller;

import cn.appsys.pojo.AppCategory;
import cn.appsys.service.AppCategoryService;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Api(value = "appcate")
@RequestMapping("/appcategory")
public class AppCategoryController {
    @Resource
    private AppCategoryService appCategoryService;

    @ApiOperation(value = "根据分类id查询App分类列表，ajax请求",httpMethod = "GET",response = AppCategory.class,
                    protocols = "HTTP",produces = "application/json",notes = "<h1>根据分类id查询App分类列表</h1>")
    @RequestMapping("/list.json")
    @ResponseBody
    public String list(@ApiParam(required = false,name = "pid",value = "分类id") @RequestParam Integer pid){
        List<AppCategory> categoryList=appCategoryService.getCategoryNameLsitByParentId(pid);
        return JSONArray.toJSONString(categoryList);
    }
}
