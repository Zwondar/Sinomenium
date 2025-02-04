package person.sinomenium.DAO;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import person.sinomenium.Pojo.Entity.Discuss;

import java.sql.Timestamp;
import java.util.List;

//评论数据库交互类
@Mapper
public interface DiscussMap extends BaseMapper<Discuss> {
    //查询指定考核的所有一级评论
    @Select("select * from discuss where exam_id=#{examId} and discuss_id=#{discuss_id} and create_time < #{end_time} order by create_time desc limit 0,10")
    List<Discuss> page_query(Integer examId, Timestamp end_time , Integer discuss_id);

    //查回复评论数
    @Select("select count(1) from discuss where discuss_id=#{id} and exam_id=#{examId}")
    Integer selectDiscussNum(Integer id, Integer examId);
}
