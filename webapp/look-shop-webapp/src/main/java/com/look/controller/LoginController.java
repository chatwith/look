package com.look.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Administrator on 2016/1/1 0001.
 */
@Controller
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        System.out.println("login start");
        ModelAndView model = new ModelAndView();
        model.addObject("name","中国");
        String url ="https://www.baidu.com/s?wd=%20org.springframework.web.servlet.DispatcherServlet%20noHandlerFound&rsv_spt=1&rsv_iqid=0xa715a1380026bd7e&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=0&rsv_t=95455IIPqVqEq5%2BpsxdyVqq5EXFkf%2BLO3g3bfVvgxvaAwz8FgaYlouzVpHUdxlct4mgv&oq=asm%20classreader&rsv_pq=92a80cc100046cb0&rsv_sug3=2&rsv_sug7=000";
        System.out.println(url);
        url = URLDecoder.decode(url,"utf-8");
        System.out.println(url);
        model.addObject("url",url);
        model.setViewName("login/login");
        return model;
    }
}
