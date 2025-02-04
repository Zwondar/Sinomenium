package person.sinomenium.Server;

import person.sinomenium.Pojo.DTO.DiscussDTO;
import person.sinomenium.Pojo.VO.Discuss_Select_VO;

public interface I_DiscussService {
    //查看所有一级评论
    Discuss_Select_VO page_query_comment(Integer examId, long end_time, Integer discuss_id);
    //发布评论
    void save(DiscussDTO discussDTO);
}
