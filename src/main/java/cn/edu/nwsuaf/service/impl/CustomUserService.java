package cn.edu.nwsuaf.service.impl;

import cn.edu.nwsuaf.model.MyUserDetails;
import cn.edu.nwsuaf.model.User;
import cn.edu.nwsuaf.model.UserRole;
import cn.edu.nwsuaf.service.UserRoleService;
import cn.edu.nwsuaf.service.UserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Created by huangrongchao on 2017/3/26.
 */
public class CustomUserService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user;
        try {
            user = userService.selectByUserName(s);
        } catch (Exception e) {
            throw new UsernameNotFoundException("user select fail");
        }
        if (user == null) {
            user = userService.selectByEmail(s);
        }

        System.out.println("user.email" + JSON.toJSONString(user));
        if (user == null) {
            throw new UsernameNotFoundException("no user found");
        } else {
            try {
                List<UserRole> roles = userRoleService.getRoleByUser(user);
                MyUserDetails userDetails = new MyUserDetails(user);
                userDetails.setRoles(roles);

                return userDetails;
            } catch (Exception e) {
                throw new UsernameNotFoundException("user role select fail");
            }
        }
    }
}
