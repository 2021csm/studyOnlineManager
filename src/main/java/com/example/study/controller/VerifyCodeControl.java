package com.example.study.controller;

import com.example.study.service.VerifyService;
import com.example.study.util.VerifyCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class VerifyCodeControl {
    @Autowired
    private VerifyService verifyService;

    @RequestMapping("/VerifyCode")
    @ResponseBody
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("image/jpeg");
        // 不缓存此内容
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        VerifyCode vc=new VerifyCode();
        BufferedImage image=vc.getImage();
        String code=vc.getText();
        VerifyCode.output(image, response.getOutputStream());
         //String code=request.getParameter("code");
        //String nativeCode = verifyService.nativeCode(response.getOutputStream());
        //verifyService.check(code,nativeCode);

    }


}
