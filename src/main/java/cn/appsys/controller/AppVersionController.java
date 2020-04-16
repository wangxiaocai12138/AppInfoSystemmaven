package cn.appsys.controller;

import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.AppInfoService;
import cn.appsys.service.AppVersionService;
import cn.appsys.tools.Constants;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/version")
public class AppVersionController {
    @Resource
    private AppVersionService appVersionService;
    @Resource
    private AppInfoService appInfoService;

    /**
     * 添加版本页面前跳转
     * @param appVersion
     * @return
     */
    @RequestMapping("/doaddversion.html")
    public String doaddversion(@ModelAttribute AppVersion appVersion,
                               @RequestParam Integer id , Model model){
        List<AppVersion> appVersionList= appVersionService.getAppVersionList(id);
        appVersion.setAppId(id);
        model.addAttribute("appVersionList",appVersionList);
        return "developer/appversionadd";
    }
    @RequestMapping("/addversionsave.html")
    public String addversionsave(AppVersion appVersion, HttpServletRequest request,
                                 @RequestParam(value = "a_downloadLink") MultipartFile attach){
        Integer devId=((DevUser)request.getSession().getAttribute(Constants.DEV_USER_SESSION)).getId();
        String downloadLink =  null; //项目路径
        String apkLocPath =  null; //全路径
        String apkFileName=null;
        String appName=appInfoService.getAppInfoById(appVersion.getAppId()).getAPKName();
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
            if(attach.getSize()>500000000){//说明文件过大
                request.setAttribute("fileUploadError",Constants.FILEUPLOAD_ERROR_4);
                return "developer/appversionadd";
            }else if(prefix.equalsIgnoreCase("apk")){//判断文件后缀
                apkFileName=appName+"-"+appVersion.getVersionNo()+".apk";

                //5、开始上传
                File targetFile=new File(path,apkFileName);
                if(!targetFile.exists()){//判断文件夹是否存在，不存在就创建
                    targetFile.mkdirs();
                }
                //6、接收文件流
                try {
                    attach.transferTo(targetFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    request.setAttribute("fileUploadError",Constants.FILEUPLOAD_ERROR_2);
                    return "developer/appversionadd";
                }
                apkLocPath=path+ File.separator+apkFileName;//文件全路径
                downloadLink= request.getContextPath()+"/statics/uploadfiles/"+apkFileName;//项目路径
            }else {
                request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_3);
                return "developer/appversionadd";
            }
            appVersion.setApkLocPath(apkLocPath);
            appVersion.setDownloadLink(downloadLink);
            appVersion.setApkFileName(apkFileName);
            appVersion.setCreatedBy(devId);
            appVersion.setCreationDate(new Date());
            if(appVersionService.addAppVersion(appVersion)){
                return "redirect:/appinfo/list.html";//添加成功后跳转到app信息页面
            }
        }
        request.setAttribute("fileUploadError"," * 没有上传相应的apk文件 ");
        return "developer/appversionadd";
    }

    /**
     * 修改前页面跳转
     * @param vid
     * @param aid
     * @param model
     * @param appVersion
     * @return
     */
    @RequestMapping("/modify.html")
    public String doModify(@RequestParam Integer vid,
                           @RequestParam Integer aid,
                           Model model,
                           @ModelAttribute AppVersion appVersion){
        List<AppVersion> appVersionList= appVersionService.getAppVersionList(aid);
        AppVersion appVs=appVersionService.getAppVersion(vid);
        model.addAttribute("appVersionList",appVersionList);
        model.addAttribute("appVersion",appVs);
        return "developer/appversionmodify";
    }

    /**
     * 删除app
     * @param id
     * @return
     */
    @RequestMapping("/delapp.json")
    @ResponseBody
    public String update(@RequestParam Integer id){
        Map<String,String> map=new HashMap<>();
         if(appVersionService.delapp(id)){
             map.put("result","success");
         }else{
             map.put("result","failed");
         }
         return JSONArray.toJSONString(map);
    }
    @RequestMapping("/modifySave.html")
    public String save(AppVersion appVersion, HttpServletRequest request,
                       @RequestParam(value = "attach") MultipartFile attach){
        Integer devId=((DevUser)request.getSession().getAttribute(Constants.DEV_USER_SESSION)).getId();
        String downloadLink =  null; //项目路径
        String apkLocPath =  null; //全路径
        String apkFileName=null;
        String appName=appInfoService.getAppInfoById(appVersion.getAppId()).getAPKName();
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
            if(attach.getSize()>500000000){//说明文件过大
                request.setAttribute("fileUploadError",Constants.FILEUPLOAD_ERROR_4);
                return "redirect:/version/modify.html";
            }else if(prefix.equalsIgnoreCase("apk")){//判断文件后缀
                apkFileName=appName+"-"+appVersion.getVersionNo()+".apk";

                //5、开始上传
                File targetFile=new File(path,apkFileName);
                if(!targetFile.exists()){//判断文件夹是否存在，不存在就创建
                    targetFile.mkdirs();
                }
                //6、接收文件流
                try {
                    attach.transferTo(targetFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    request.setAttribute("fileUploadError",Constants.FILEUPLOAD_ERROR_2);
                    return "redirect:/version/modify.html";
                }
                apkLocPath=path+ File.separator+apkFileName;//文件全路径
                downloadLink= request.getContextPath()+"/statics/uploadfiles/"+apkFileName;//项目路径
            }else {
                request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_3);
                return "redirect:/version/modify.html";
            }
            appVersion.setApkLocPath(apkLocPath);
            appVersion.setDownloadLink(downloadLink);
            appVersion.setApkFileName(apkFileName);
            appVersion.setModifyBy(devId);
            appVersion.setModifyDate(new Date());
            if(appVersionService.updAppVersion(appVersion)){
                return "redirect:/appinfo/list.html";//添加成功后跳转到app信息页面
            }
        }
        request.setAttribute("fileUploadError"," * 没有上传相应的apk文件 ");
        return "redirect:/version/modify.html";
    }
}
