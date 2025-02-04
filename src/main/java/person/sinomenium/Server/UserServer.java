package person.sinomenium.Server;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import person.sinomenium.DAO.RootMap;
import person.sinomenium.Pojo.DTO.*;
import person.sinomenium.DAO.UserDetailMap;
import person.sinomenium.Pojo.Entity.Exam;
import person.sinomenium.Pojo.Entity.SubmitExam;
import person.sinomenium.Pojo.Entity.UserDetail;
import person.sinomenium.Pojo.VO.RootAllExam;
import person.sinomenium.Pojo.VO.RootViewRank;
import person.sinomenium.Pojo.VO.UserViewResourse;
import person.sinomenium.Static.Constant.EmailLoginConstant;
import person.sinomenium.Static.Constant.MessageConstant;
import person.sinomenium.DAO.UserMap;
import person.sinomenium.Pojo.Entity.User;
import person.sinomenium.Static.Constant.UserConstant;
import person.sinomenium.Static.Tool.FormatCheck;
import person.sinomenium.Static.Tool.SessionFactory;
import person.sinomenium.Static.Tool.ThreadContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServer implements I_UserServer{
    @Autowired
    private UserMap userMapper;
    @Autowired
    RootMap rootMap;
    @Autowired
    private UserDetailMap userDetailMap;

    /**
     * 用户账密登录（管理员复用）
     */
    public User login(UserLoginDTO userLoginDTO) {
        String email = userLoginDTO.getEmail();
        String password = userLoginDTO.getPassword();
        //校验邮箱格式
        if(!FormatCheck.QQEmailCheck(email)) {
            ThreadContext.addError(400, MessageConstant.EMAIL_FORMAT_WRONG);
            return null;
        }
        //校验密码格式
        if(!FormatCheck.pwdCheck(password)) {
            ThreadContext.addError(400, MessageConstant.PASSWORD_FORMAT_WRONG);
            return null;
        }
        //根据邮箱账号获取用户信息
        User user = userMapper.getByEmail(email);
        //用户不存在
        if(user == null) {
            ThreadContext.addError(400, MessageConstant.USER_NOT_FOUND);
            return null;
        }
        //获取用户盐值
        String salt = user.getSalt();
        //当前密码加盐进行加密比对
        String md5Hex = DigestUtil.md5Hex(password + salt);
        //密码不正确
        if(!md5Hex.equals(user.getPassword())) {
            ThreadContext.addError(400, MessageConstant.PASSWORD_WRONG);
            return null;
        }
        //返回用户信息
        return user;
    }

    /**
     * 邮箱登录
     */
    public User emailLogin(UserEmailLoginDTO dto){
        String userEmail = dto.getEmail();
        HttpSession session = SessionFactory.getSession(userEmail);
        String email = (String) session.getAttribute(EmailLoginConstant.email);
        String code = (String) session.getAttribute(EmailLoginConstant.code);

        //校验邮箱格式
        if(!FormatCheck.QQEmailCheck(userEmail)) {
            ThreadContext.addError(400, MessageConstant.EMAIL_FORMAT_WRONG);
            return null;
        }
        //获取用户信息
        User user = userMapper.getByEmail(userEmail);
        //用户不存在
        if(user == null) {
            ThreadContext.addError(400, MessageConstant.USER_NOT_FOUND);
            return null;
        }
        //校验验证码
        if (session.getAttribute(EmailLoginConstant.code) == null || !dto.getCode().equals(code)) {
            ThreadContext.addError(400, MessageConstant.CODE_WRONG);
            return null;
        }
        //校验邮箱是否对应验证码
        if(!userEmail.equals(email)) {
            ThreadContext.addError(400, MessageConstant.EMAIL_CODE_WRONG);
            return null;
        }
        //关闭session，以防验证码滥用
        SessionFactory.removeSession(userEmail);
        //返回用户信息
        return user;
    }

    //用户注册
    public void register(UserRegisterDTO userRegisterDTO) {

        String email = userRegisterDTO.getEmail();
        String code = userRegisterDTO.getCode();
        String password = userRegisterDTO.getPassword();
        //校验邮箱格式
        if(!FormatCheck.QQEmailCheck(email)) {
            ThreadContext.addError(400, MessageConstant.EMAIL_FORMAT_WRONG);
            return;
        }
        //校验密码格式
        if(!FormatCheck.pwdCheck(password)) {
            ThreadContext.addError(400, MessageConstant.PASSWORD_FORMAT_WRONG);
            return;
        }
        //校验两个密码
        if(!password.equals(userRegisterDTO.getPasswords())) {
            ThreadContext.addError(400, MessageConstant.PASSWORD_INCONSISTENT);
            return;
        }
        //根据用户名查询用户信息
        User user = userMapper.getByEmail(email);
        //用户已存在则抛出异常
        if(user != null) {
            ThreadContext.addError(400,MessageConstant.USER_EXISTED);
            return;
        }
        //校验验证码
        //获取session
        HttpSession session = SessionFactory.getSession(email);
        System.out.println(session.getAttribute(EmailLoginConstant.code));
        if(!code.equals(session.getAttribute(EmailLoginConstant.code))||session.getAttribute(EmailLoginConstant.code) == null) {
            ThreadContext.addError(400,MessageConstant.CODE_WRONG);
            return;
        }

        //密码加密
        //盐
        String salt = RandomUtil.randomString(4);
        //加盐并md5加密后存储在数据库中的密码
        String passwordDB = DigestUtil.md5Hex(userRegisterDTO.getPassword() + salt);

        //用户上传文件的初始容量（100mb）
        Long component = UserConstant.USER_COMPONENT;
        //将用户信息添加到数据库中
        User userDB = User.builder()
                .email(userRegisterDTO.getEmail())
                .password(passwordDB)
                .salt(salt)
                .component(component)
                .admin(UserConstant.USER)
                .build();
        userMapper.insert(userDB);
        //建立用户详细信息表
        UserDetail userDetail = UserDetail.builder().userId(userDB.getId()).build();
        userDetailMap.insert(userDetail);
        //移除session，以防验证码被继续用于其他接口
        SessionFactory.removeSession(email);
    }
//用户端下载考核服务实现类
    @Override
    public String UserDownloadExamServer(Integer id) {
       String link= userMapper.getDataExamFileUrl(id);

        return link;
    }
//用户端答案上传服务实现类
    @Override
    public void UserUploadAnswerServer(UserAnswer userAnswer) {
        LocalDateTime time=LocalDateTime.now();
        Integer user_id=ThreadContext.getCurrentId();
        System.out.println(user_id);
        System.out.println(userAnswer.getExam_id());
        userAnswer.setUser_id(user_id);
        userAnswer.setTime(time);
        if(userMapper.IsExistSubmitExam(userAnswer)==null) userMapper.setDataSubExam(userAnswer);
        else
        {
            Integer count=userMapper.getSubmitExam(userAnswer.getUser_id(),userAnswer.getExam_id());
            count--;
            userAnswer.setCount(count);
            userMapper.upDataSubmitExam(userAnswer);
        }
    }
//用户端查看自我评分服务实现类
    @Override
    public Double UserViewGradeServer(Integer id) {
        Integer user_id=ThreadContext.getCurrentId();
        Double grade= userMapper.getSubExamGrade(id,user_id);
        return grade;
    }

    @Override
    public List<UserViewResourse> UserViewResourseServer() {
        List <UserViewResourse> uvr=userMapper.getDataResource();
        return uvr;
    }

    @Override
    public String UserDownloadResourceServer(Integer id) {
       String fileUrl= userMapper.getDataResourceFileUrl(id);
        return fileUrl;
    }

    //管理端考核功能:展示服务实现类
    @Override
    public List<RootAllExam> RootAllExamServer() {
        List<Exam> exams=rootMap.getDataExam();
        List<RootAllExam> raes=new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for(int i=0;i<exams.size();i++)
        {
            if(raes.size()<exams.size()) raes.add(new RootAllExam());
            raes.get(i).setId(exams.get(i).getId());
            raes.get(i).setName(exams.get(i).getName());
            raes.get(i).setStart_date(exams.get(i).getStartTime().format(formatter));
            raes.get(i).setEnd_time(exams.get(i).getEndTime().format(formatter));
            System.out.println(exams.get(i).getFile());
            raes.get(i).setFileUrl(exams.get(i).getFile());
        }
        return raes;
    }
    //管理端考核功能：排名服务实现类
    @Override
    public List<RootViewRank> RootViewRankServer(Integer exam_id) {
        List<RootGrades> grades=rootMap.getGrade(exam_id);

        List<RootViewRank> rvr=new ArrayList<>();
        for(int i=0;i<grades.size();i++)
        {
            if(rvr.size()<grades.size()) rvr.add(new RootViewRank());
            Integer user_id=grades.get(i).getUserId();
            UserDetail userDetails=rootMap.getDataExamUserDetail(user_id);
            rvr.get(i).setRanking(i+1);
            rvr.get(i).setUrl(userDetails.getAvatar());
            rvr.get(i).setClasses(userDetails.getClasses());
            rvr.get(i).setName(userDetails.getName());
            rvr.get(i).setAverage(grades.get(i).getGrades());
            rvr.get(i).setAnswerUrl(grades.get(i).getAnswer());
        }

        return rvr;
    }

    @Override
    public Integer userGetCount(Integer exam_id) {
        Integer user_id=ThreadContext.getCurrentId();
        Integer count =userMapper.getSubmitExam(user_id,exam_id);
        System.out.println(count);
        System.out.println(user_id);
        System.out.println(exam_id);
        return count;
    }
}
