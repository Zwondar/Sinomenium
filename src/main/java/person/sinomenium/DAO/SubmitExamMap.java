package person.sinomenium.DAO;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import person.sinomenium.Pojo.Entity.SubmitExam;

import java.util.List;

@Mapper
public interface SubmitExamMap extends BaseMapper<SubmitExam> {
    @Select("select grades from submit_exam where user_id = #{userId}")
    List<Double> selectList(Integer userId);
}
