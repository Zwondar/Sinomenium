package person.sinomenium.DAO;

import org.apache.ibatis.annotations.*;
import person.sinomenium.Pojo.DTO.EditorResource;
import person.sinomenium.Pojo.DTO.RootEditorExam;
import person.sinomenium.Pojo.DTO.RootGrades;
import person.sinomenium.Pojo.Entity.*;
import person.sinomenium.Pojo.VO.AdminsVO;
import person.sinomenium.Pojo.VO.RootMidSubExam;
import java.util.List;

//管理员数据库交互类
@Mapper
public interface RootMap {
//查询user表中的所有字段(可复用)
@Select("select * from user ")
List<User> getDataUser();
//查询user_detail表中的所有字段(可复用)
@Select("select * from user_detail")
    List<UserDetail> getDataUserDetail();
@Select("select * from user_detail order by average desc ")
List<UserDetail> getDataUserDetails();
@Select("select grades,user_id,answer from submit_exam where exam_id=#{exam_id} order by grades desc")
    List<RootGrades> getGrade(Integer exam_id);
//查询exam表中全部字段(可复用)
@Select("select * from exam ")
    List<Exam> getDataExam();
//查询sub_exam表中的所有用户id
@Select("select user_id from submit_exam where exam_id=#{id}")
    List<Integer> getDataUserExam(Integer id);
//查询所有sub——exam表中所有用户信息
@Select("select * from user_detail where user_id=#{user_id}")
    UserDetail getDataExamUserDetail(Integer user_id);
//查询考核文件地址与最后操作时间
@Select("select answer,id from submit_exam where user_id=#{user_id} and exam_id=#{exam_id}")
    RootMidSubExam getDataRootMidSubExam(Integer user_id,Integer exam_id);
//编辑考核信息
@Insert("insert into exam (user_id, name, file, start_time, end_time) VALUE (#{user_id},#{name},#{fileUrl},#{begin_time},#{end_time})")
    void setDataExam(RootEditorExam rootEditorExam);
@Select("select id from exam where id=#{id}")
    Integer IsExistExamId(RootEditorExam rootEditorExam);
@Update("update exam set user_id=#{user_id},name=#{name},file=#{fileUrl},start_time=#{begin_time},end_time=#{end_time} where id=#{id}")
    void UpdateDataExam(RootEditorExam rootEditorExam);
@Delete("delete from exam where id = #{id}")
    void DeleteDataExam(Integer id);
@Delete("delete from submit_exam where exam_id = #{id}")
    void DeleteDataSubExam(Integer id);
@Select("select id from exam where id=#{id}")
    Integer IsExistExam(Integer id);
@Select("select id from submit_exam where exam_id=#{id}")
    Integer IsExistSubExam(Integer id);
@Select("select * from resource")
    List<Resource> getDataResource();
@Select("select id from resource where id=#{id}")
    Integer IsExistResource(Integer id);
@Insert("insert into resource (user_id,user_name, name, file) VALUE (#{user_id},#{user_name},#{name},#{fileUrl})")
    void setDataResource(EditorResource editorResource);
@Select("select * from user_detail where user_id=#{user_id}")
    UserDetail getAloneUserDetail(Integer user_id);
@Update("update resource set user_id=#{user_id},user_name=#{user_name},name=#{name},file=#{fileUrl} where id=#{id}")
    void upDateResource(EditorResource editorResource);
@Delete("delete from resource where id=#{id}")
    void DeleteResource(Integer id);
@Select("select user_id from submit_exam where exam_id=#{exam_id}")
    List<Integer> getNullGradeUserId(int exam_id);
@Select("select answer from submit_exam where exam_id=#{exam_id}")
    List<String> getNullGradeAnswer(int exam_id);
@Select("select d.name,d.student_id,u.email from user u JOIN user_detail d on u.id = d.user_id where u.admin = 1")
    List<AdminsVO> selectAllAdmins();
@Select("select grades from submit_exam where user_id=#{user_id} and exam_id=#{exam_id}")
    Double getGradesd(int user_id,int exam_id);
}
