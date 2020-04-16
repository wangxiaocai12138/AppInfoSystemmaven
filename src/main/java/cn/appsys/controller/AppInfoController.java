package cn.appsys.controller;

import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.*;
import cn.appsys.service.AppCategoryService;
import cn.appsys.service.AppInfoService;
import cn.appsys.service.DataDictionaryService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/appinfo")
public class AppInfoController {
    @Resource
    private AppInfoService appInfoService;
    @Resource
    private AppCategoryService appCategoryService;
    @Resource
    private DataDictionaryService dataDictionaryService;
    @Resource
    private AppVersionMapper appVersionMapper;

    @RequestMapping("/list.html")
    public String appinfolist(Model model, HttpSession session, @RequestParam(required = false) String querySoftwareName,
                              @RequestParam(required = false) Integer queryStatus, @RequestParam(required = false) Integer queryFlatformId,
                              @RequestParam(required = false) Integer queryCategoryLevel1, @RequestParam(required = false) Integer queryCategoryLevel2,
                              @RequestParam(required = false) Integer queryCategoryLevel3, @RequestParam(required = false,defaultValue = "1") Integer pageIndex){
        Integer devId=((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId();
        List<AppCategory> categoryList=appCategoryService.getCategoryNameLsitByParentId(null);
        model.addAttribute("categoryLevel1List",categoryList);
        int index=(pageIndex-1)* Constants.pageSize;//起始下标
        List<AppInfo> alist= appInfoService.getAppInfoList(devId,querySoftwareName,queryStatus,queryFlatformId,
                                                            queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3,
                                                            index,Constants.pageSize);
        model.addAttribute("appInfoList",alist);
        int count=appInfoService.getCount(devId,querySoftwareName,queryStatus,queryFlatformId,
                                            queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3);
        //分页
        PageSupport page=new PageSupport();
        page.setTotalCount(count);
        page.setCurrentPageNo(pageIndex);
        model.addAttribute("pages",page);
        //状态码列表
        List<DataDictionary> statusList=dataDictionaryService.getStatusList();
        model.addAttribute("statusList",statusList);
        //所属平台列表
        List<DataDictionary> flatFormList=dataDictionaryService.getFlatFormList();
        model.addAttribute("flatFormList",flatFormList);
        return "developer/appinfolist";
    }

    /**
     * 登录前跳转
     * @param appInfo
     * @return
     */
    @RequestMapping("/doappadd.html")
    public String doAppadd(@ModelAttribute AppInfo appInfo){
        return "developer/appinfoadd";
    }

    /**
     * app名称验证
     * @param APKName
     * @param session
     * @return
     */
    @RequestMapping("/apkexist.json")
    @ResponseBody
    public String apkexist(@RequestParam String APKName,HttpSession session){
        Map<String,String> map=new HashMap<>();
        Integer devId=((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId();
        if(StringUtils.isEmpty(APKName)){
            map.put("APKName","empty");
        }else{
            if(appInfoService.apkexist(APKName,devId)){//为true说明该APP名称使用过
                map.put("APKName","exist");
            }else{
                map.put("APKName","noexist");
            }
        }
        return JSONArray.toJSONString(map);
    }

    @RequestMapping(value = "/appadd.html",method = RequestMethod.POST)
    public String appadd(AppInfo appInfo, HttpServletRequest request,
                         @RequestParam(value = "a_logoPicPath") MultipartFile attach/*,
                         @RequestParam(value = "softwareSize") String size*/){
       /* BigDecimal softwareSize=new BigDecimal(size);//类型转换
        appInfo.setSoftwareSize(softwareSize);*/
        Integer devId=((DevUser)request.getSession().getAttribute(Constants.DEV_USER_SESSION)).getId();
        String logoPicPath =  null; //项目路径
        String logoLocPath =  null; //全路径
        //2、查看文件域是否为空
        if(!attach.isEmpty()){//true为空
            //3、定义上传目标的路径
            String path=request.getSession().getServletContext().//separator自适应的文件路径分隔符，减低代码的可入侵性
                    getRealPath("statics"+ File.separator+"uploadfiles");
            //4、获取源文件名
            String oldFileName=attach.getOriginalFilename();
            //文件后缀
            String prefix= FilenameUtils.getExtension(oldFileName);
            //文件大小判断
            if(attach.getSize()>500000){//说明文件过大
                request.setAttribute("fileUploadError",Constants.FILEUPLOAD_ERROR_4);
                return "developer/appinfoadd";
            }else if(prefix.equalsIgnoreCase("jpg")||
                    prefix.equalsIgnoreCase("jpeg")||
                    prefix.equalsIgnoreCase("png")||
                    prefix.equalsIgnoreCase("pneg")){//判断文件后缀
                String fileName=appInfo.getAPKName()+".jpg";
                //5、开始上传
                File targetFile=new File(path,fileName);
                if(!targetFile.exists()){//判断文件夹是否存在，不存在就创建
                    targetFile.mkdirs();
                }
                //6、接收文件流
                try {
                    attach.transferTo(targetFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    request.setAttribute("fileUploadError",Constants.FILEUPLOAD_ERROR_2);
                    return "developer/appinfoadd";
                }
                logoLocPath=path+File.separator+fileName;//文件全路径
                logoPicPath= request.getContextPath()+"/statics/uploadfiles/"+fileName;//项目路径
            }else {
                request.setAttribute("fileUploadError",Constants.FILEUPLOAD_ERROR_3);
                return "developer/appinfoadd";
            }
            appInfo.setLogoLocPath(logoLocPath);
            appInfo.setLogoPicPath(logoPicPath);
            appInfo.setDevId(devId);
            appInfo.setCreatedBy(devId);
            appInfo.setCreationDate(new Date());
            if(appInfoService.appadd(appInfo)){
                return "redirect:/appinfo/list.html";
            }
        }
        request.setAttribute("fileUploadError"," * 上传文件没有logo ");
        return "developer/appinfoadd";
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delapp.json")
    @ResponseBody
    public String delapp(@RequestParam Integer id){
        Map<String,String> map=new HashMap<>();
        if(id==null){
            map.put("delResult","notexist");
        }else{
            if(appInfoService.delapp(id)){
                map.put("delResult","true");
            }else{
                map.put("delResult","false");
            }
        }
        return JSONArray.toJSONString(map);
    }

    /**
     * 查询
     * @param id
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/appview/{id}")
    public String view(@PathVariable Integer id,Model model,HttpServletRequest request){
        AppInfo appInfo= appInfoService.getAppInfoById(id);
        model.addAttribute("appInfo",appInfo);
        List<AppVersion> versionList=appVersionMapper.getAppVersionList(id);
        model.addAttribute("appVersionList",versionList);
        return "developer/appinfoview";
    }

    /**
     * 修改前页面跳转
     * @param appInfo
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/modify.html")
    public String doModify(@ModelAttribute AppInfo appInfo,
                           @RequestParam Integer id,Model model){
        AppInfo app= appInfoService.getAppInfoById(id);
        model.addAttribute("appInfo",app);
        return "developer/appinfomodify";
    }

    /**
     * 删除logo图片
     * @param id
     * @return
     */
    @RequestMapping("/dellogo.json")
    @ResponseBody
    public String delLogo(@RequestParam Integer id){
        Map<String,String> map=new HashMap<>();
        if(appInfoService.delLogo(id)){//删除成功
            map.put("result","success");
        }else{
            map.put("result","failed");
        }
        return JSONArray.toJSONString(map);
    }

    /**
     * 修改
     * @param appInfo
     * @param request
     * @param attach
     * @return
     */
    @RequestMapping("/modifysave.html")
    public String modifySave(AppInfo appInfo, HttpServletRequest request,
                             @RequestParam(value = "attach") MultipartFile attach){
        Integer devId=((DevUser)request.getSession().getAttribute(Constants.DEV_USER_SESSION)).getId();
        String logoPicPath =  null; //项目路径
        String logoLocPath =  null; //全路径
        //2、查看文件域是否为空
        if(!attach.isEmpty()){//true为空
            //3、定义上传目标的路径
            String path=request.getSession().getServletContext().//separator自适应的文件路径分隔符，减低代码的可入侵性
                    getRealPath("statics"+ File.separator+"uploadfiles");
            //4、获取源文件名
            String oldFileName=attach.getOriginalFilename();
            //文件后缀
            String prefix= FilenameUtils.getExtension(oldFileName);
            //文件大小判断
            if(attach.getSize()>500000){//说明文件过大
                request.setAttribute("fileUploadError",Constants.FILEUPLOAD_ERROR_4);
                return "redirect:appinfo/modify.html";
            }else if(prefix.equalsIgnoreCase("jpg")||
                    prefix.equalsIgnoreCase("jpeg")||
                    prefix.equalsIgnoreCase("png")||
                    prefix.equalsIgnoreCase("pneg")){//判断文件后缀
                String fileName=appInfo.getAPKName()+".jpg";
                //5、开始上传
                File targetFile=new File(path,fileName);
                if(!targetFile.exists()){//判断文件夹是否存在，不存在就创建
                    targetFile.mkdirs();
                }
                //6、接收文件流
                try {
                    attach.transferTo(targetFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    request.setAttribute("fileUploadError",Constants.FILEUPLOAD_ERROR_2);
                    return "redirect:appinfo/modify.html";
                }
                logoLocPath=path+File.separator+fileName;//文件全路径
                logoPicPath= request.getContextPath()+"/statics/uploadfiles/"+fileName;//项目路径
            }else {
                request.setAttribute("fileUploadError",Constants.FILEUPLOAD_ERROR_3);
                return "redirect:appinfo/modify.html";
            }
            appInfo.setLogoLocPath(logoLocPath);
            appInfo.setLogoPicPath(logoPicPath);
            appInfo.setDevId(devId);
            appInfo.setModifyBy(devId);
            appInfo.setModifyDate(new Date());
            if(appInfoService.updateAppinfo(appInfo)){
                return "redirect:/appinfo/list.html";
            }
        }
        request.setAttribute("fileUploadError"," * 上传文件没有logo ");
        return "redirect:appinfo/modify.html";
    }

    @RequestMapping("/{appId}/sale.json")
    @ResponseBody
    public String sale(@PathVariable Integer appId){
        AppInfo appInfo= appInfoService.getAppInfoById(appId);
        Map<String,String> map=new HashMap<>();
        if(!StringUtils.isEmpty(appId)){//true是为空
            map.put("errorCode","0");
            if(appInfoService.updateAppStatus(appId,appInfo.getStatus())){
                map.put("resultMsg","success");//修改成功
            }else{
                map.put("resultMsg","failed");//修改成功
            }
        }else{
            map.put("errorCode","param000001");
        }
        return JSONArray.toJSONString(map);
    }
}
