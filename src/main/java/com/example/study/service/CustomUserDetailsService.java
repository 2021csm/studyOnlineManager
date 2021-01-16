//package com.example.study.service;
//
//import com.example.study.bean.SysRole;
//import com.example.study.bean.SysUser;
//import com.example.study.bean.SysUserRole;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private SysUserService userService;
//
//    @Autowired
//    private SysRoleService roleService;
//
//    @Autowired
//    private SysUserRoleService userRoleService;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        // 从数据库中取出用户信息
//        SysUser user = userService.selectByName(username);
//
//        // 判断用户是否存在
//        if(user == null) {
//            throw new UsernameNotFoundException("用户名不存在");
//        }
//
//        // 添加权限
//        Map<String,Object> map=new HashMap<>();
//        map.put("user_id",user.getId());
//        SysRole role=null;
//        List<SysUserRole> userRoles = userRoleService.findList(map);
//        for (SysUserRole userRole : userRoles) {
//            role = roleService.findById(userRole.getRoleId());
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//
//        // 返回UserDetails实现类
//
//        return new User(user.getName(), user.getPassword(), authorities);
//    }
//}
