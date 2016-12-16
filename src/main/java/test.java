import com.mxiaixy.dao.UserDao;
import com.mxiaixy.entity.User;
import com.mxiaixy.util.Config;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * Created by Mxia on 2016/12/15.
 */
public class test {


    public static void main(String[] args) {
//        UserDao userDao = new UserDao();
//         User user =   userDao.findByEmail("www.mxia@xyang.con");
//        System.out.println(user.getEmail());
//        System.out.println(user.getUserName());
//        HtmlEmail htmlEmail = new HtmlEmail();//获取邮件对象
//
//        htmlEmail.setHostName(Config.get("HostName"));
//        htmlEmail.setAuthentication(Config.get("sendEmailName"),Config.get("sendEmailpwd"));
//        htmlEmail.setCharset(Config.get("setCharset"));
//        htmlEmail.setStartTLSEnabled(true);
//
//
//        try {
//            htmlEmail.setFrom(Config.get("setForm"));
//            htmlEmail.setSubject("fgdg");
//            htmlEmail.setHtmlMsg("sfddsfd");
//            htmlEmail.addTo("1334909533@qq.com");
//
//            htmlEmail.send();
//        }catch(EmailException e){
//            e.printStackTrace();
//            throw new RuntimeException("邮件发送异常");
//
//        }
//
        HtmlEmail htmlEmail = new HtmlEmail();

        htmlEmail.setHostName("smtp.126.com");
        //htmlEmail.setSmtpPort(Integer.valueOf(Config.get("email_port")));
        htmlEmail.setAuthentication("xuxiaoyang12","wojiushimima");

        htmlEmail.setStartTLSEnabled(true);

        try {
            htmlEmail.setFrom("xuxiaoyang12@126.com");
            //System.out.print(Config.get("email_frommail"));
            htmlEmail.setSubject("dffff");
            htmlEmail.setHtmlMsg("fsdfsdf");
            htmlEmail.addTo("1334909533@qq.com");

            htmlEmail.send();
        } catch (EmailException ex) {
            // logger.error("向{}发送邮件失败");
            throw new RuntimeException("发送邮件失败:" );
        }

    }
}
