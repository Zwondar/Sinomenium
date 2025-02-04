package person.sinomenium.Control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import person.sinomenium.Pojo.DTO.DiscussDTO;
import person.sinomenium.Pojo.DTO.SelectDiscussDTO;
import person.sinomenium.Pojo.VO.Discuss_Select_VO;
import person.sinomenium.Pojo.VO.ResultVO;
import person.sinomenium.Server.I_DiscussService;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/qingteng-recruitment/user/discuss")
/**
 * 评论接口
 */
public class DiscussControl {

    private final I_DiscussService discussService;

    /**
     * 查看评论
     */
    @PostMapping("/select")
    public Discuss_Select_VO page_query_comment(@RequestBody SelectDiscussDTO selectDiscussDTO){
        log.info("end_time: {}",selectDiscussDTO.getEnd_time());
        return discussService.page_query_comment(Integer.valueOf(selectDiscussDTO.getExam_id()),Long.valueOf(selectDiscussDTO.getEnd_time()),Integer.valueOf(selectDiscussDTO.getDiscuss_id()));
    }

    /**
     * 发布评论
     */
    @PostMapping("/save")
    public ResultVO save_comment(@RequestBody DiscussDTO discussDTO){
        discussService.save(discussDTO);
        return ResultVO.success("发布成功");
    }
}
