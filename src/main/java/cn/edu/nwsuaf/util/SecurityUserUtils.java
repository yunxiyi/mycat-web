package cn.edu.nwsuaf.util;

import cn.edu.nwsuaf.model.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by huangrongchao on 2017/4/20.
 */
public class SecurityUserUtils {
    public static MyUserDetails getPrincipal() {
        MyUserDetails user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            user = ((MyUserDetails) principal);
        }
        return user;
    }
}
