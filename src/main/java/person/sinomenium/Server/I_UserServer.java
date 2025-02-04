package person.sinomenium.Server;

import person.sinomenium.Pojo.DTO.UserAnswer;
import person.sinomenium.Pojo.DTO.UserEmailLoginDTO;
import person.sinomenium.Pojo.DTO.UserLoginDTO;
import person.sinomenium.Pojo.DTO.UserRegisterDTO;
import person.sinomenium.Pojo.Entity.User;
import person.sinomenium.Pojo.VO.RootAllExam;
import person.sinomenium.Pojo.VO.RootViewRank;
import person.sinomenium.Pojo.VO.UserViewResourse;

import java.util.List;
public interface I_UserServer {
    //账密登录
    User login(UserLoginDTO userLoginDTO);
    //邮箱登录
    User emailLogin(UserEmailLoginDTO dto);
    //用户注册
    void register(UserRegisterDTO userRegisterDTO);
    //用户端下载考核服务接口
    String UserDownloadExamServer(Integer id);
    //用户端上传答案服务接口
    void UserUploadAnswerServer(UserAnswer userAnswer);
    //用户端查看自我评分服务接口
    Double UserViewGradeServer(Integer id);
    List<UserViewResourse> UserViewResourseServer();
    //用户端资源下载服务接口
    String UserDownloadResourceServer(Integer id);
    //管理端考核功能:展示服务接口
    public List<RootAllExam> RootAllExamServer();
    //管理端考核功能：排名服务接口
    public List<RootViewRank> RootViewRankServer(Integer id);
    public Integer userGetCount(Integer exam_id);
}
