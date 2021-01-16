package com.example.study.service;

import com.example.study.util.SendSms;
import com.example.study.util.VerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class VerifyService {
    @Autowired
    private MQSender sender;
    @Autowired
    private RedisService redisService;

    /**
     * 生成随机的几位数算法
     * @return
     */
    private int getNumber(){
        //生成随机6位数字
        Random random = new Random();
        int randomNumber = random.nextInt(999999);
        if (randomNumber < 100000) {
            randomNumber += 100000;
        }
        return randomNumber;
    }

    /**
     * @param os
     * @return code
     * 返回验证码字符串
     * 在服务器本地生成验证码图片，通过response流传到客户前端
     * 用户通过验证码图片输入验证码后，和code进行比对
     */
    public String nativeCode(OutputStream os) {
        VerifyCode vc = new VerifyCode();
        BufferedImage image = vc.getImage();
        String code = vc.getText();
        try {
            VerifyCode.output(image, os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }
    /**
     * 手机接收6位的数字验证码，不包含字符
     *无定时功能
     * @return
     */
    public String phoneCode(String phone) {
        //得到6位随机码
        int randomNumber=getNumber();
        String code = Integer.toString(randomNumber);
        //封装消息
        Map map = new HashMap();
        map.put("phone", phone);
        map.put("code", code);
        //发送到MQ
        sender.SendPhoneCode(map);
        return code;
    }
    /**
     * 手机接收6位的数字验证码，不包含字符
     *验证码被存到redis中
     * 可以定时
     */
    public void phoneCode(String phone,boolean saveOnRedis) {
        String code= phoneCode(phone);
        //验证码存储服务
        if(saveOnRedis==true)
           redisService.saveCode(phone,code);
    }

    /**
     * 检测验证码是否正常
     * @param UIcode
     * @param serviceCode
     * @return
     */
    public boolean codeCheck(String UIcode, String serviceCode )  {
        if (serviceCode == null && "".equals(serviceCode))
            return false;
        if (UIcode == null && "".equals(UIcode))
            return false;
        if (!UIcode.equals(serviceCode))
            return false;
        return true;
    }

}
