package cn.edu.nwsuaf.controller;

import cn.edu.nwsuaf.model.MyUserDetails;
import com.alibaba.fastjson.JSON;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * Created by sang on 2017/1/10.
 */
@Controller
public class HomeController {
    @RequestMapping("/")
    public String main(Model model) {
        MyUserDetails user = getPrincipal();
        if (user != null) {
            model.addAttribute("user", user);
        }
        System.out.println(JSON.toJSONString(user));
        if (contains(user.getAuthorities(), "ROLE_ADMIN")) {
            return "index";
        } else {
            return "user-profile";
        }
    }

    @RequestMapping("/top")
    public String top(Model model) {
        model.addAttribute("user", getPrincipal());
        return "top";
    }

    @RequestMapping("/index")
    @PreAuthorize("hasPermission('ROLE_ADMIN')")
    public String index(Model model) {
        MyUserDetails user = getPrincipal();
        if (user != null) {
            model.addAttribute("user", user);
        }
        if (contains(user.getAuthorities(), "ROLE_ADMIN")) {
            return "index";
        } else {
            return "user-profile";
        }
    }

    @RequestMapping("/login")
    public String login(Model model) {
        MyUserDetails user = getPrincipal();
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "login";
    }

    @RequestMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping("/user-profile")
    public String userProfile() {
        return "user-profile";
    }

    @RequestMapping("/template")
    public String template() {
        return "template";
    }

    private MyUserDetails getPrincipal() {
        MyUserDetails user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            user = ((MyUserDetails) principal);
        }
        return user;
    }

    private boolean contains(Collection<? extends GrantedAuthority> roles, String role) {
        for (GrantedAuthority r : roles) {
            if (role.equals(r.toString())) {
                return true;
            }
        }
        return false;
    }
}
