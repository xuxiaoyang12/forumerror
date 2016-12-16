package com.mxiaixy.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mxiaixy.dao.LoginDao;
import com.mxiaixy.dao.UserDao;
import com.mxiaixy.entity.Login;
import com.mxiaixy.entity.User;
import com.mxiaixy.exception.ServiceException;
import com.mxiaixy.util.Config;
import com.mxiaixy.util.EmailUtil;
import com.mxiaixy.web.user.LoginServlet;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 用户 服务逻辑类
 * Created by Mxia on 2016/12/15.
 */
public class UserService {

    private  UserDao userDao = new UserDao();
    private  Logger logger = LoggerFactory.getLogger(UserService.class);
    private Properties properties = new Properties();//获取config.properies 的对象

    //发送激活邮件的token缓存   设置时间长度设置时间类型  创建缓存
    private static Cache<String ,String > cache = CacheBuilder.newBuilder()
            .expireAfterAccess(6, TimeUnit.HOURS)
            .build();


    /**
     * 校验用户名是否被占用
     * @param userName
     * @return
     */
   public  Boolean  findByUserName(String userName){

        //如果有保留名 或特殊的名字 （名人或者其他的名字）在config.properise中配置 查找
        //获取字符串
        String name = Config.get("no_signuo_names");
        //将获取到的字符串转换成list数组
        List<String> nameList = Arrays.asList(name.split(","));

        //判断数组中是否存在name
        if(nameList.contains(userName)){
            return false;
        }
        //如果包含则返回false表示已被占用 入股偶不包含 则返回true 表示未被占用
        User user =  userDao.findByUserName(userName);
       if(user==null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 校验email是否被占用
     * @param email
     * @return
     */
    public   User findByEmail(String email) {


        User user=  userDao.findByEmail(email);

        logger.info("电子邮件{}",user);
        return user;
    }

    /**
     * 保存用户注册信息
     * @param userName
     * @param password
     * @param email
     * @param phone
     */
    public void save(String userName, String password, String email, String phone) {

        User user = new User();
        user.setUserName(userName);
        //md5加密
        user.setPassword(DigestUtils.md5Hex(Config.get("username_pwd_salt")+password));
        user.setEmail(email);
        user.setPhone(phone);
        user.setAvatar(User.DEFAULT_AVATAR_NAME);
        user.setState(User.USERSTATE_UNACTIVE);

        userDao.save(user);

        //创建子线程  异步运行电子邮件
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {

                //保存成功后发送电子邮件

                //随机出一串字符串
                String uuid = UUID.randomUUID().toString();
                //生成发送邮件的url\
                String url = "http://localhost/user/active?_="+uuid;

                //加入缓存
                cache.put(uuid,userName);

                //设置邮件主题
                String title = "<h3>亲爱的"+user.getUserName()+"</h3></br>";
                //设置邮件内容
                String content = "<p>请点击<a href="+url+">链接</a>以激活您的账户哦</p>";
                //发送邮件
                EmailUtil.sendHtmlEmail(user.getEmail(),title,content);
            }
        });
        //运行子线程
        th.start();
    }

    /**
     * 判断密码和账号是否匹配
     * @param userName
     * @param password
     * @return
     */
    public User findByNameAndPwd(String userName, String password,String ip) {

        User user = userDao.findByUserName(userName);
        String pwd = DigestUtils.md5Hex(Config.get("username_pwd_salt")+password);
        logger.info("账号已查询 密码{}",pwd);
        if(user!=null){
            if(user.getPassword().equals(pwd)){
                if(user.getState().equals(User.USERSTATE_UNACTIVE)){
                    throw new ServiceException("账号未激活,请激活后再登录");
                }else if(user.getState().equals(User.USERSTATE_ACTIVE)){
                    //记录用户日志
                    Login login = new Login();
                    login.setIp(ip);
                    login.setT_user_id(user.getId());
                    //把用户日志保存
                    LoginDao loginDao = new LoginDao();
                    loginDao.save(login);
                    logger.info("用户日志保存成功");
                    return user;
                }else{
                    throw new ServiceException("账号以被冻结");
                }
            }else{
                throw new ServiceException("账号或密码错误");
            }
        }else{
            throw new ServiceException("账号或密码错误");
        }
    }

    //激活账户
    public void activeAccount(String token) {

        //通过token后获取用户名
        String userName = cache.getIfPresent(token);

        if(userName==null){
           throw new ServiceException("token已过期或者错误");
        }else{
            User user = userDao.findByUserName(userName);
            if(user==null){
                throw new ServiceException("用户不存在");
            }else{
                //如果存在 则激活账号
                user.setState(user.USERSTATE_ACTIVE);
                userDao.update(user);

                //清空token缓存
                cache.invalidate("token");
            }
        }

    }
}
