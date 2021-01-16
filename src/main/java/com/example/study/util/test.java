package com.example.study.util;

import com.example.study.bean.Subject_info;
import com.example.study.bean.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

public class test {


    public static void main(String[] args) {
//        String gensalt=BCrypt.gensalt();
//        String password=BCrypt.hashpw("123",gensalt);
//        System.out.println(gensalt);
//        System.out.println(password);
//        System.out.println(BCrypt.checkpw("1234","$2a$10$.uyC7m4DRpZ0GYluevbvUumq09roVY77uyCLMEM5idoPsYJELQH9i"));
        Gson gson=new Gson();
//        User user=new User();
//        user.setName("csm");
//        user.setEmail("2793219495@qq.com");
//        user.setPassword("123");
//        user.setType("public");
      Subject_info s1=  new Subject_info();
      s1.setSubject_id("123");
      s1.setSubject_info_id("23");
      s1.setSubject_info_name("test");
      s1.setValue_list("linux");
        Subject_info s2=  new Subject_info();
        s2.setSubject_id("123new");
        s2.setSubject_info_id("23");
        s2.setSubject_info_name("testooo");
        s2.setValue_list("linuxnew");
        List<Subject_info> infoList=new ArrayList<>();
        infoList.add(s1);
        infoList.add(s2);
        String json=gson.toJson(infoList);
        System.out.println(json);

    }


}
