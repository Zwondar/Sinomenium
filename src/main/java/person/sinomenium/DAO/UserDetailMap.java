package person.sinomenium.DAO;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import person.sinomenium.Pojo.Entity.User;
import person.sinomenium.Pojo.Entity.UserDetail;

@Mapper
public interface UserDetailMap extends BaseMapper<UserDetail> {
    //根据用户id查找对应用户详细表
    @Select("select * from user_detail where user_id = #{userId}")
    UserDetail selectByUserId(Integer userId);
    //根据用户id查找用户详细表id
    @Select("select id from user_detail where user_id = #{userId}")
    Integer selectIdByUserId(Integer userId);
    //根据提交考核的id查找对应用户细节表
    @Select("select * from user_detail where id = (select id from user_detail where user_id = (select user_id from submit_exam where submit_exam.id = #{examId}))")
    UserDetail selectUserDetailByExamId(Integer examId);
}
