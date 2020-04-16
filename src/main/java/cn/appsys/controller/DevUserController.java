package cn.appsys.controller;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.DevUserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/dev")
public class DevUserController {
    @Resource
    private DevUserService devUserService;
    private Logger log = Logger.getLogger(DevUserController.class);

    /**
     * 开发者登录首页
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        log.info("进入登录页面！");
        return "devlogin";
    }

    /**
     * 登录
     * @param devCode
     * @param devPassword
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/dologin.html")
    public String dologin(@RequestParam String devCode,
                          @RequestParam String devPassword,
                          Model model, HttpSession session){
        DevUser devUser=devUserService.getLoginUser(devCode,devPassword);
        if(devUser!=null){
            session.setAttribute("devUserSession",devUser);
            return "redirect:/dev/flatform/main";
        }
        model.addAttribute("error","用户名或者密码错误！");
        return "devlogin";
    }

    /**
     * 进入首页
     * @param session
     * @return
     */
    @RequestMapping("/flatform/main")
    public String main(HttpSession session){
        DevUser user= (DevUser) session.getAttribute("devUserSession");
        if(user==null){
            return "redirect:/dev/login";
        }
        return "developer/main";
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @RequestMapping("/logout.html")
    public String logout(HttpSession session){
        session.removeAttribute("devUserSession");
        return "redirect:/dev/login";
    }


}
