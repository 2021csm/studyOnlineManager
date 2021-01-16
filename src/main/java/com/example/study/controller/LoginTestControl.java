package com.example.study.controller;
import com.example.study.bean.TestUser;
import com.example.study.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginTestControl {
    @Autowired
    private VerifyService verifyService;
    private String code;
    @PostMapping("/testLogin")
    public void loginTest(@RequestBody TestUser user){
       String UICode=user.getCode();
       if(verifyService.codeCheck(UICode,this.code)){
           System.out.println("验证码匹配成功");
       }
    }
    @PostMapping("/getCodeByPhone")
    public void getCodeByPhone(@RequestBody TestUser user){
        String phone=user.getPhone();
        this.code= verifyService.phoneCode(phone);

    }
}
