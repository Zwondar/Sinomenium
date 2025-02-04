package person.sinomenium.Server;
import jakarta.servlet.http.HttpSession;


public interface I_MailService {
    //发送验证码邮件
    void sendCodeMail(String email, HttpSession session);

}
