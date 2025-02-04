package person.sinomenium.Control;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import person.sinomenium.Pojo.DTO.UserAnswer;
import person.sinomenium.Pojo.DTO.UserEmailLoginDTO;
import person.sinomenium.Pojo.DTO.UserLoginDTO;
import person.sinomenium.Pojo.DTO.UserRegisterDTO;
import person.sinomenium.Pojo.Entity.User;
import person.sinomenium.Pojo.VO.*;
import person.sinomenium.Server.I_RootServer;
import person.sinomenium.Server.I_SubmitExamService;
import person.sinomenium.Utility.Properties.JwtProperties;
import person.sinomenium.Server.I_UserServer;
import person.sinomenium.Server.MailService;
import person.sinomenium.Utility.Util.JwtUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户端接口
 */
@RestController
@RequestMapping("/qingteng-recruitment/user")
@Slf4j
public class UserControl {
    @Autowired
    private I_UserServer userService;

    @Autowired
    private MailService mailService;
    @Autowired
    private JwtProperties jwtProperties;
    @Resource
    private I_SubmitExamService submitExamService;
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ResultVO<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);
        //比对数据库信息
        User user = userService.login(userLoginDTO);
        if(user == null)
            return null;
        //登录成功后，生成jwt令牌并返回
        UserLoginVO userLoginVO = createJwt(user);
        return ResultVO.success(userLoginVO,"登录成功");
    }


    /**
     * 用户端考核功能:展示
     */
    @PostMapping("/display_exam")
    public ResultVO<List<RootAllExam>> rootAllExamControl()
    {
        List<RootAllExam> rae=userService.RootAllExamServer();
        return ResultVO.success(rae,"考核资源展示成功");
    }

@PostMapping("/getCount")
public ResultVO userGetCount(Integer exam_id)
{
    Integer count =userService.userGetCount(exam_id);
    return ResultVO.success(count);
}

    /**
     * 管理端考核功能：排名
     */
    @PostMapping("/examine_ranking")
    public ResultVO<List<RootViewRank>> rootViewRankControl(Integer id)
    {
        List<RootViewRank> rvr=userService.RootViewRankServer(id);

        return ResultVO.success(rvr,"考核资源排名展示成功");
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public ResultVO<Object> register(@RequestBody UserRegisterDTO userRegisterDTO){
        log.info("用户注册：{}",userRegisterDTO);
        userService.register(userRegisterDTO);
        return ResultVO.success("用户注册成功");
    }

    /**
     * 申请发送验证码邮件
     */
    @PostMapping("/sendEmail")
    public ResultVO<Object> sendCodeEmail(@RequestBody UserRegisterDTO dto, HttpSession session){
        log.info("邮箱：{}申请发送验证码邮件",dto.getEmail());
        mailService.sendCodeMail(dto.getEmail(),session);
        return ResultVO.success("验证码已发送");
    }

    /**
     * QQ邮箱登录
     */
    @PostMapping("/emailLogin")
    public ResultVO<UserLoginVO> emailLogin(@RequestBody UserEmailLoginDTO userEmailLoginDTO){
        log.info("用户邮箱登录：{}",userEmailLoginDTO);
        //比对数据库信息
        User user = userService.emailLogin(userEmailLoginDTO);
        if(user == null)
            return null;
        //登录成功后，生成jwt令牌并返回VO对象
        return ResultVO.success(createJwt(user),"登录成功");
    }

    /**
     * 用户端查看我的评分
     */
    @PostMapping("/select_score/{id}")
    public ResultVO<Double> examine_score(@PathVariable("id") String examId){
        return ResultVO.success(submitExamService.selectScore(examId));
    }

    /**
     * 私有
     * 生成jwt并返回VO对象
     */
    private UserLoginVO createJwt(User user){
        //存放用户信息作为唯一标识
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", user.getId());
        claims.put("admin",user.getAdmin());
        //生成JWT令牌
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);
        //封装返回对象
        return UserLoginVO.builder()
                .token(token)
                .admin(user.getAdmin())
                .build();
    }

    /**
     * 用户端考核下载
     */
    @PostMapping("/examine_download")
    public ResultVO<String> UserDownloadExam(Integer id)
    {
        String link=userService.UserDownloadExamServer(id);
        return ResultVO.success(link,"Url返回成功");
    }

    /**
     * 用户端上传答案
     */
    @PostMapping("/examine_upload")
    public ResultVO UserUploadAnswer(@RequestBody UserAnswer userAnswer){
        userService.UserUploadAnswerServer(userAnswer);
        return ResultVO.success("答案上传成功");
    }

    /**
     * 用户端查看考核成绩
     */
    @PostMapping("/examine_score")
    public ResultVO UserViewGrade(Integer id)
    {
        Double grade=userService.UserViewGradeServer(id);
        return ResultVO.success(grade,"查看分数成功");
    }

    /**
     * 用户端展示资源
     */
    @PostMapping("/display_resource")
    public ResultVO<List<UserViewResourse>> UserViewResourseControl()
    {
       List<UserViewResourse> uvr= userService.UserViewResourseServer();
        return ResultVO.success(uvr,"资源返回成功");
    }

    /**
     * 用户端下载资源
     */
    @PostMapping("/resource_download")
    public ResultVO<String> UserDownloadResource(Integer id)
    {
        String fileUrl=userService.UserDownloadResourceServer(id);
        return ResultVO.success(fileUrl,"资源下载成功");
    }
}

