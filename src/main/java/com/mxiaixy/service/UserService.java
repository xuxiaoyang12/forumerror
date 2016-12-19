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
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
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

    //限制用户操作频率
    private static Cache<String ,String > foundCache = CacheBuilder.newBuilder()
            .expireAfterAccess(60,TimeUnit.SECONDS)
            .build();
    //找回密码邮件验证缓存
    private static Cache<String ,String > emailCachs = CacheBuilder.newBuilder()
            .expireAfterAccess(6,TimeUnit.MINUTES)
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

    /**
     * 激活账户
     * @param token
     */
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

    /**
     * 用户找回密码
     * @param type  找回密码方式  电子邮件|手机号码
     * @param value  用户输入的值  电子邮件|手机号码
     * @param sessionId 当前用户的sessionid  用来限制用户重复提交表单
     */
    public void foundPassword(String type, String value, String sessionId) {

        //判断用户sessionID 是否是同一个用户
        logger.info("type值:::{}",type);
        if(foundCache.getIfPresent(sessionId)==null){
            //判断找回方式类型
            if("email".equals(type)){
                logger.info("用户找回方式电子邮件");
                //判断邮件是否存在
                User user = userDao.findByEmail(value);
                if(user!=null){

                    //创建子线程
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //向用户发送激活邮件
                            String uuid = UUID.randomUUID().toString();//随机产生一个值
                            String url = "http://localhost/found/active?_="+uuid;

                            String  subject = "<h3>用户找回密码邮件</h3>";
                            String   html  ="<p>请点击此链接 <a href="+url+">链接</a>找回密码 </p><p>如果不是本人操作请尽快修改密码 给您造成的不便尽情谅解！</p>";
                            //加入缓存

                            emailCachs.put(uuid,user.getUserName());
                            //发送邮件
                            EmailUtil.sendHtmlEmail(value,subject,html);
                        }

                    });
                    thread.start();


                }

            }else if("phone".equals(type)){

                logger.info("找回方式手机号码");
            }
            logger.info("用户输入的邮件不存在");
            //操作频率过快
            foundCache.put(sessionId,"::::::");
        }else{
            throw new ServiceException("操作频率过快请稍后再试！");
        }
    }


    /**
     * 验证token-->用户通过电子邮件重置密码
     * @param token  用户验证邮件里的token值
     */
    public User foundPasswordUserByToken(String token) {

        //获取缓存中的用户名
        String userName = emailCachs.getIfPresent(token);
        if(StringUtils.isEmpty(userName)){
            throw new ServiceException("验证邮件已过期或者错误");
        }else{
            //通过用户名获取用户数据
            User user = userDao.findByUserName(userName);
            if(user==null){
                throw new ServiceException("用户不存在");
            }else{
                //用户token值正确且用户存在 则返回用户数据
                return user;
            }
        }

    }

    /**
     * 重置密码-->忘记密码的用户
     * @param password  用户输入的新密码
     */
    public void resetPassword(String id,String token,String password) {
        //从缓存中获取到用户和token
        if(emailCachs.getIfPresent(token)==null){
            throw new ServiceException("token已过期或失效");
        }else{
            User user = userDao.findById(Integer.valueOf(id));
            if(user==null){
                throw new ServiceException("用户不存在");

            }else{
                user.setPassword(DigestUtils.md5Hex(Config.get("username_pwd_salt")+password));
                userDao.update(user);
                logger.info("{}重置了密码",user.getUserName());
                //清除缓存中的token
                emailCachs.invalidate(token);
            }
        }



    }
//    public void resetPassword(String password,User user){
//        //当前用户更改密码
//        user.setPassword(DigestUtils.md5Hex(Config.get("username_pwd_salt")+password));
//        try {
//            userDao.update(user);
//        }catch (Exception e){
//            throw new ServiceException("重置密码失败 请稍后重试");
//        }
//
//    }

    /**
     * 更改email
     * @param email  用户的新email
     */
    public void updateEmail(String email,User user) {

        user.setEmail(email);
        userDao.update(user);
        logger.info("{}邮件更改成功",user.getUserName());
    }


    /**
     * 重置用户头像
     * @param user 当前用户
     * @param filekey 新头像的名称
     */
    public void uploadAvatar(User user, String filekey) {

        user.setAvatar(filekey);
        userDao.update(user);
        logger.info("{}头像更改成功",user.getAvatar());

    }

    /**
     * 用户更改密码 登录用户
     * @param user
     * @param newPassword
     */
    public void updatePassword(User user, String newPassword) {

        user.setPassword(DigestUtils.md5Hex(Config.get("username_pwd_salt")+newPassword));
        userDao.update(user);
        logger.info("{}密码修改成功",user.getUserName());
    }
}
