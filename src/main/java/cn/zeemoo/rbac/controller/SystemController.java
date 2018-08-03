package cn.zeemoo.rbac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统默认控制器
 *
 * @author zeemoo
 * @date 2018/7/28 14:30
 */
@Controller
public class SystemController {

    @RequestMapping("noPermission")
    public String noPermission(){
        return "noPermission";
    }
}
