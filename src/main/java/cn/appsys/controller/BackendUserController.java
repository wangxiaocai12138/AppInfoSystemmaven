package cn.appsys.controller;

import cn.appsys.pojo.BackendUser;
import cn.appsys.service.BackendUserService;
import cn.appsys.tools.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/back")
public class BackendUserController {

    @Resource
    private BackendUserService backendUserService;
    /**
     * 管理首页
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "backendlogin";
    }

    /**
     * 登录
     * @param userCode
     * @param userPassword
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/dologin.html")
    public String doLogin(@RequestParam String userCode,
                          @RequestParam String userPassword,
                          Model model, HttpSession session){
        BackendUser backendUser= backendUserService.getUser(userCode,userPassword);
        if(backendUser!=null){
            session.setAttribute(Constants.USER_SESSION,backendUser);
            return "redirect:/back/main.html";
        }
        model.addAttribute("error","用户名或者密码错误！");
        return "backendlogin";
    }

    /**
     * 跳转到首页
     * @param session
     * @return
     */
    @RequestMapping("/main.html")
    public String main(HttpSession session){
        BackendUser backendUser=(BackendUser) session.getAttribute(Constants.USER_SESSION);
        if(backendUser!=null){
            return "backend/main";
        }else{
            return "403";
        }
    }

}
