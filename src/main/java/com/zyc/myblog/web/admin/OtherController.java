package com.zyc.myblog.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OtherController {
    @RequestMapping("/forward")
    public String go(){
        return "/admin/noPermission";

    }
}
