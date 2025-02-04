package person.sinomenium.DAO;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import person.sinomenium.Pojo.DTO.UserAnswer;
import person.sinomenium.Pojo.Entity.SubmitExam;
import person.sinomenium.Pojo.Entity.User;
import person.sinomenium.Pojo.VO.UserViewResourse;

import java.util.List;

//用户数据库交互类
@Mapper
public interface UserMap extends BaseMapper<User> {
    //邮箱登录返回用户信息
    @Select(("select * from user where email = #{email}"))
    User getByEmail(String email);
    @Select("select file from exam where id=#{id}")
    String getDataExamFileUrl(Integer id);
    @Insert("insert into submit_exam (exam_id, user_id, answer,update_time) VALUE (#{exam_id},#{user_id},#{fileUrl},#{time})")
    void setDataSubExam(UserAnswer userAnswer);
    @Select("select id from submit_exam where exam_id=#{exam_id} and user_id=#{user_id}")
    Integer IsExistSubmitExam(UserAnswer userAnswer);
    @Update("update submit_exam set answer=#{fileUrl},update_time=#{time},grades=0,count=#{count} where exam_id=#{exam_id} and user_id=#{user_id}")
    void upDataSubmitExam(UserAnswer userAnswer);
    @Select("select grades from submit_exam where exam_id=#{id} and user_id=#{user_id}")
    Double getSubExamGrade(Integer id,Integer user_id);
    @Select("select * from resource")
    List<UserViewResourse> getDataResource();
    @Select("select file from resource where id=#{id}")
    String getDataResourceFileUrl(Integer id);
    //获取用户剩余容量
    @Select("select component from user where id = #{id}")
    Long getComponentById(Integer id);

    @Select("select count from submit_exam where user_id=#{user_id} and exam_id=#{exam_id}")
    Integer getSubmitExam(Integer user_id,Integer exam_id);

}
