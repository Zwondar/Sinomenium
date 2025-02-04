package person.sinomenium.Server;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.cron.timingwheel.SystemTimer;
import cn.hutool.cron.timingwheel.TimerTask;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import person.sinomenium.Static.Constant.MessageConstant;
import person.sinomenium.Static.Constant.EmailLoginConstant;
import person.sinomenium.Static.Tool.FormatCheck;
import person.sinomenium.Static.Tool.SessionFactory;
import person.sinomenium.Static.Tool.ThreadContext;

import static cn.hutool.core.date.DateTime.now;
@Slf4j
@Service
public class MailService implements I_MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailProperties mailProperties;
    /**
     * 给前端传输过来的邮箱发送邮件
     */
    @Override
    public void sendCodeMail(String emailTo, HttpSession session) {
        //校验邮箱格式
        if(!FormatCheck.QQEmailCheck(emailTo))
            ThreadContext.addError(400,MessageConstant.EMAIL_FORMAT_WRONG);
        //生成验证码
        String code = RandomUtil.randomNumbers(4);
        //存放验证码和邮箱到session中，用于登录或注册比对
        log.info("session:{}",session);
        session.setAttribute(EmailLoginConstant.email,emailTo);
        session.setAttribute(EmailLoginConstant.code,code);
        //加入Factory中
        SessionFactory.addSession(emailTo,session);
        SystemTimer systemTimer = new SystemTimer();
        systemTimer.start();
        //60s移除session中的验证码
        systemTimer.addTask(new TimerTask(()->{
            session.removeAttribute(EmailLoginConstant.code);
            SessionFactory.addSession(emailTo,session);
            },60*1000));
        //创建简单文本邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        //设置邮件信息主题
        message.setSubject("青藤招新程序");
        //设置邮件文本内容
        message.setText("正在注册或登录的邮箱：" + emailTo + "\n您收到的验证码为" + code + "\n验证码有效期为五分钟，请尽快使用，如非本人操作，请注意账号安全");
        //设置收件人
        message.setTo(emailTo);
        //设置发件人
        message.setFrom(mailProperties.getUsername());
        //设置邮件日期
        message.setSentDate(now());
        //发送邮件
        javaMailSender.send(message);
    }



}
