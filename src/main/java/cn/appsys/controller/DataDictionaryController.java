package cn.appsys.controller;

import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.DataDictionaryService;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/data")
public class DataDictionaryController{
    @Resource
    private DataDictionaryService dataDictionaryService;

    /**
     * 添加页面的平台列表
     * @return
     */
    @RequestMapping("/dictionarylist.json")
    @ResponseBody
    public String getFlatFormList(){
        List<DataDictionary> list= dataDictionaryService.getFlatFormList();
        return JSONArray.toJSONString(list);
    }

}
