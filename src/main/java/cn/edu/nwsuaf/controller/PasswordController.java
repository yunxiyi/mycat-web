package cn.edu.nwsuaf.controller;

import cn.edu.nwsuaf.model.User;
import cn.edu.nwsuaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by huangrongchao on 2017/4/17.
 */
@Controller
public class PasswordController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/i_forget_password")
    @ResponseBody
    public Map forgetPass(HttpServletRequest request, String email) {
        User user = userService.selectByEmail(email);
        Map map = new HashMap<String, String>();
        String msg = "";
        if (user == null) {              //用户名不存在
            msg = "用户名不存在,你不会忘记用户名了吧?";
            map.put("msg", msg);
            return map;
        }
        try {
            String secretKey = UUID.randomUUID().toString();  //密钥
            Timestamp outDate = new Timestamp(System.currentTimeMillis() + 30 * 60 * 1000);//30分钟后过期
            long date = outDate.getTime() / 1000 * 1000;                  //忽略毫秒数
            user.setValidateCode(secretKey);
            user.setRegisterDate(outDate);
            userService.updateByPrimaryKeySelective(user);    //保存到数据库
            String key = user.getUsername() + "$" + date + "$" + secretKey;
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            String digitalSignature = String.valueOf(md5.digest(key.getBytes()));                 //数字签名

            String emailTitle = "有方云密码找回";
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
            String resetPassHref = basePath + "user/reset_password?sid=" + digitalSignature + "&userName=" + user.getUsername();
            String emailContent = "请勿回复本邮件.点击下面的链接,重设密码<br/><a href=" + resetPassHref + " target='_BLANK'>点击我重新设置密码</a>" +
                    "<br/>tips:本邮件超过30分钟,链接将会失效，需要重新申请'找回密码'" + key + "\t" + digitalSignature;
            System.out.print(resetPassHref);
            //   MailSender.getInstatnce().sendHtmlMail(emailTitle,emailContent,user.getEmail());
            msg = "操作成功,已经发送找回密码链接到您邮箱。请在30分钟内重置密码";
            //logInfo(request,userName,"申请找回密码");
        } catch (Exception e) {
            e.printStackTrace();
            msg = "邮箱不存在？未知错误,联系管理员吧。";
        }
        map.put("msg", msg);
        return map;
    }

    @RequestMapping(value = "/user/reset_password", method = RequestMethod.GET)
    public ModelAndView checkResetLink(String sid, String userName) {
        ModelAndView model = new ModelAndView("error");
        String msg = "";
        if (sid.equals("") || userName.equals("")) {
            msg = "链接不完整,请重新生成";
            model.addObject("msg", msg);
            //logInfo(userName,"找回密码链接失效");
            return model;
        }
        User user = userService.selectByUserName(userName);
        if (user == null) {
            msg = "链接错误,无法找到匹配用户,请重新申请找回密码.";
            model.addObject("msg", msg);
            // logInfo(userName,"找回密码链接失效");
            return model;
        }
        Date outDate = user.getRegisterDate();
        if (outDate.getTime() <= System.currentTimeMillis()) {         //表示已经过期
            msg = "链接已经过期,请重新申请找回密码.";
            model.addObject("msg", msg);
            // logInfo(userName,"找回密码链接失效");
            return model;
        }
        String key = user.getUsername() + "$" + outDate.getTime() / 1000 * 1000 + "$" + user.getValidateCode();          //数字签名
        String digitalSignature = "";//MD5.MD5Encode(key);
        System.out.println(key + "\t" + digitalSignature);
        if (!digitalSignature.equals(sid)) {
            msg = "链接不正确,是否已经过期了?重新申请吧";
            model.addObject("msg", msg);
            //    logInfo(userName,"找回密码链接失效");
            return model;
        }
        model.setViewName("user/reset_password");  //返回到修改密码的界面
        model.addObject("userName", userName);
        return model;
    }
}
