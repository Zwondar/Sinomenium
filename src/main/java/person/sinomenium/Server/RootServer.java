package person.sinomenium.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Service;
import person.sinomenium.DAO.RootMap;
import person.sinomenium.DAO.SubmitExamMap;
import person.sinomenium.DAO.UserDetailMap;
import person.sinomenium.DAO.UserMap;
import person.sinomenium.Pojo.DTO.EditorResource;
import person.sinomenium.Pojo.DTO.RankDTO;
import person.sinomenium.Pojo.DTO.RootEditorExam;
import person.sinomenium.Pojo.DTO.RootGrades;
import person.sinomenium.Pojo.Entity.*;
import person.sinomenium.Pojo.VO.*;
import person.sinomenium.Static.Constant.MessageConstant;
import person.sinomenium.Static.Constant.UserConstant;
import person.sinomenium.Static.Tool.ThreadContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RootServer implements I_RootServer{

    @Autowired
    RootMap rootMap;
    @Autowired
    UserMap userMap;
    @Autowired
    UserDetailMap userDetailMap;
    @Autowired
    SubmitExamMap submitExamMap;
    @Autowired
    MailProperties mailProperties;


    //管理端查看用户信息列表功能服务实现类
    @Override
    public List<RootViewList> RootViewListServer() {
        List<UserDetail> userDetails=rootMap.getDataUserDetails();
        List<RootViewList> rvls=new ArrayList<>();
        for(int i=0;i<userDetails.size();i++)
        {
            if(rvls.size()<userDetails.size()) rvls.add(new RootViewList());
            rvls.get(i).setAverage(userDetails.get(i).getAverage());
            rvls.get(i).setUrl(userDetails.get(i).getAvatar());
            rvls.get(i).setName(userDetails.get(i).getName());
            rvls.get(i).setRanking(i+1);
            rvls.get(i).setClasses(userDetails.get(i).getClasses());
        }

         return rvls;
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
    //管理端查看考核服务实现类
    @Override
    public List<RootViewExam> RootViewExamServer(Integer id) {
        List<Integer> user_ids=rootMap.getDataUserExam(id);
        List<RootViewExam> rve=new ArrayList<>();
        int j=0;
        for(int i=0;i<user_ids.size();i++) {
            UserDetail userDetails = rootMap.getDataExamUserDetail(user_ids.get(i));
            RootMidSubExam rootMidSubExam = rootMap.getDataRootMidSubExam(user_ids.get(i), id);
            if (rootMap.getGradesd(user_ids.get(i), id) != 0) System.out.println("分数不为0，跳过");
            else {
                if (rve.size() < user_ids.size()) rve.add(new RootViewExam());
                rve.get(j).setClasses(userDetails.getClasses());
                rve.get(j).setUserID(userDetails.getUserId());
                rve.get(j).setName(userDetails.getName());
                rve.get(j).setAvatarUrl(userDetails.getAvatar());
                rve.get(j).setAnswer(rootMidSubExam.getAnswer());
                rve.get(j).setID(rootMidSubExam.getId());
                j++;
            }
        }
        return rve;
    }
    //管理端编辑考核服务实现类
    @Override
    public void RootEditorExam(RootEditorExam rootEditorExam) {
       Integer user_id= ThreadContext.getCurrentId();
       rootEditorExam.setUser_id(user_id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        rootEditorExam.setBegin_time(LocalDateTime.parse(rootEditorExam.getBeginTime(),formatter));
        rootEditorExam.setEnd_time(LocalDateTime.parse(rootEditorExam.getEndTime(),formatter));
        if(rootMap.IsExistExamId(rootEditorExam)==null)
       rootMap.setDataExam(rootEditorExam);
     else
      rootMap.UpdateDataExam(rootEditorExam);
    }
    //管理端删除考核服务实现类
    @Override
    public void RootDeleteExamServer(Integer id) {
        if(rootMap.IsExistExam(id)==null){

            ThreadContext.addError(400,MessageConstant.EXAM_IS_NULL);
            return;
        }
        rootMap.DeleteDataExam(id);
        if(rootMap.IsExistSubExam(id)==null){
            ThreadContext.addError(400,MessageConstant.SUBMIT_EXAM_IS_NULL);
            return;
        }else{
            rootMap.DeleteDataSubExam(id);
        }
    }
//管理端展示资源服务实现类
    @Override
    public List<RootViewAssets> RootViewAssetsServer() {
        List<Resource> resources=rootMap.getDataResource();
        List<RootViewAssets> rootViewAssets=new ArrayList<>();
        for(int i=0;i<resources.size();i++)
        {
            if(rootViewAssets.size()<resources.size()) rootViewAssets.add(new RootViewAssets());
            rootViewAssets.get(i).setId(resources.get(i).getId());
            rootViewAssets.get(i).setName(resources.get(i).getName());
            rootViewAssets.get(i).setFileUrl(resources.get(i).getFile());
        }
        return rootViewAssets;
    }
//管理端编辑资源服务实现类
    @Override
    public void RootEditorResourceServer(EditorResource editorResource) {
        Integer user_id=ThreadContext.getCurrentId();
        UserDetail userDetail=rootMap.getAloneUserDetail(user_id);
        String user_name=userDetail.getName();
        editorResource.setUser_name(user_name);
        editorResource.setUser_id(user_id);
        if(rootMap.IsExistResource(editorResource.getId())==null)
        rootMap.setDataResource(editorResource);
        else
         rootMap.upDateResource(editorResource);
    }
//管理端删除资源实现类
    @Override
    public void RootDeleteSource(Integer id) {
        if(rootMap.IsExistResource(id)==null)
        {
            ThreadContext.addError(404,MessageConstant.RESOURCE_IS_NULL);
        }
        rootMap.DeleteResource(id);
    }
    @Override
    public List<RootViewNullGrade> RootViewNullGradeServer(int exam_id) {
        List <RootViewNullGrade>rvng= new ArrayList<>();
        List<Integer> user_ids=rootMap.getNullGradeUserId(exam_id);
        if(user_ids.size()==0)
        return null;
        else
        {
            List<String> answers=rootMap.getNullGradeAnswer(exam_id);
            for(int i=0;i<user_ids.size();i++)
            {
                UserDetail userDetail=rootMap.getDataExamUserDetail(user_ids.get(i));
                String name=userDetail.getName();
                String avatar=userDetail.getAvatar();
                String classes=userDetail.getClasses();
                String answer=answers.get(i);
                Integer user_id=user_ids.get(i);
                RootViewNullGrade rootViewNullGrade=new RootViewNullGrade();
                rootViewNullGrade.setAvatar(avatar);
                rootViewNullGrade.setName(name);
                rootViewNullGrade.setId(user_id);
                rootViewNullGrade.setClasses(classes);
                rootViewNullGrade.setFileUrl(answer);
                rvng.add(rootViewNullGrade);
            }
           return  rvng;
        }

    }


    /**
     * 管理员评分
     */
    public void score(RankDTO rankDTO) {
        //获取考核信息
        Integer examId = rankDTO.getExamId();
        Double grades = rankDTO.getGrades();
        //封装提交考核对象
        SubmitExam submitExam = SubmitExam.builder()
                .id(examId)
                .grades(grades)
                .build();
        //更改评分
        submitExamMap.updateById(submitExam);
        //获取用户详细信息并更新平均分
        UserDetail userDetail = userDetailMap.selectUserDetailByExamId(examId);
        List<Double> gradesList = submitExamMap.selectList(userDetail.getUserId());

        //计算新平均分
        if(gradesList != null&& !gradesList.isEmpty()) {
            double total = 0.0;
            for (Double grade : gradesList) {
                total += grade;
            }
            double average = total / gradesList.size();
            userDetail.setAverage(average);
        }
        userDetailMap.updateById(userDetail);
    }

    //添加管理员
    public AdminsVO addAdmin(String email) {
        System.out.println(email);
        //查询用户是否存在
        User user = userMap.getByEmail(email);
        if (user == null){
            ThreadContext.addError(400,MessageConstant.USER_NOT_FOUND);
            return null;
        }
        //增加管理权限
        if (user.getAdmin().equals(UserConstant.USER))
            user.setAdmin(UserConstant.ADMIN);
        else {
            ThreadContext.addError(400,MessageConstant.ADMIN_EXISITED);
            return null;
        }
        //增加上传容量
        user.setComponent(UserConstant.ADMIN_COMPONENT);//4gb
        //修改数据库
        userMap.updateById(user);
        //返回渲染数据
        UserDetail userDetail = userDetailMap.selectByUserId(user.getId());
        return AdminsVO.builder().email(user.getEmail())
                .name(userDetail.getName())
                .studentId(userDetail.getStudentId())
                .build();
    }

    //删除管理员
    public void deleteAdmin(String email){
        //查询用户是否存在
        User user = userMap.getByEmail(email);
        if (user == null){
            ThreadContext.addError(400,MessageConstant.USER_NOT_FOUND);
            return;
        }
        //撤销管理权限
        if(user.getAdmin().equals(UserConstant.ADMIN))
            user.setAdmin(UserConstant.USER);
        //还原上传容量
        user.setComponent(UserConstant.USER_COMPONENT);//100mb
        //修改数据库
        userMap.updateById(user);
    }

    //查询所有管理员
    public List<AdminsVO> selectAdmins() {
        return rootMap.selectAllAdmins();
    }
}
