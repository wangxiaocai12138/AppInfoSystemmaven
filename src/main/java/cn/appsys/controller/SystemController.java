package cn.appsys.controller;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.AppCategoryService;
import cn.appsys.service.AppInfoService;
import cn.appsys.service.AppVersionService;
import cn.appsys.service.DataDictionaryService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys")
public class SystemController {
    @Resource
    private AppInfoService appInfoService;
    @Resource
    private DataDictionaryService dataDictionaryService;
    @Resource
    private AppCategoryService appCategoryService;
    @Resource
    private AppVersionService appVersionService;

    @RequestMapping("/applist.html")
    public String applist(Model model, @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                          @RequestParam(required = false) String querySoftwareName,
                          @RequestParam(required = false) Integer queryStatus, @RequestParam(required = false) Integer queryFlatformId,
                          @RequestParam(required = false) Integer queryCategoryLevel1, @RequestParam(required = false) Integer queryCategoryLevel2,
                          @RequestParam(required = false) Integer queryCategoryLevel3){
        int index=(pageIndex-1)* Constants.pageSize;//起始下标
        List<AppInfo> appInfoList= appInfoService.getAppInfoList(null,querySoftwareName,null,
                                                                    queryFlatformId,queryCategoryLevel1,queryCategoryLevel2,
                                                                    queryCategoryLevel3,index, Constants.pageSize);
        model.addAttribute("appInfoList",appInfoList);
        //分页
        int count=appInfoService.getCount(null,querySoftwareName,null,queryFlatformId,
                                            queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3);
        PageSupport page=new PageSupport();
        page.setTotalCount(count);
        page.setCurrentPageNo(pageIndex);
        model.addAttribute("pages",page);
        //所属平台列表
        List<DataDictionary> flatFormList=dataDictionaryService.getFlatFormList();
        model.addAttribute("flatFormList",flatFormList);
        //一级菜单
        List<AppCategory> categoryList=appCategoryService.getCategoryNameLsitByParentId(null);
        model.addAttribute("categoryLevel1List",categoryList);
        return "backend/applist";
    }

    /**
     * 查看详细信息
     * @param model
     * @param aid
     * @param vid
     * @return
     */
    @RequestMapping("/check")
    public String check(Model model, @RequestParam Integer aid,
                        @RequestParam Integer vid,
                        @ModelAttribute AppInfo appInfo){
        AppInfo app=appInfoService.getAppInfoById(aid);
        AppVersion appVersion=appVersionService.getAppVersion(vid);
        model.addAttribute("appInfo",app);
        model.addAttribute("appVersion",appVersion);
        return "backend/appcheck";
    }

    /**
     * 审核
     * @param appInfo
     * @return
     */
    @RequestMapping("/checksave")
    public String checksave(AppInfo appInfo){
        appInfo.setVersionId(0);
        if(appInfoService.updateAppinfo(appInfo)){
            return "redirect:/sys/applist.html";
        }
        return "redirect:/sys/check?aid="+appInfo.getId()+"&vid="+appInfo.getVersionId();
    }
}
