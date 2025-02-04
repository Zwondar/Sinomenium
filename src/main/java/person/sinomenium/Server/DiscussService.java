package person.sinomenium.Server;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import person.sinomenium.DAO.DiscussMap;
import person.sinomenium.DAO.UserDetailMap;
import person.sinomenium.Pojo.DTO.DiscussDTO;
import person.sinomenium.Pojo.Entity.Discuss;
import person.sinomenium.Pojo.Entity.UserDetail;
import person.sinomenium.Pojo.VO.DiscussVO;
import person.sinomenium.Pojo.VO.Discuss_Select_VO;import person.sinomenium.Static.Constant.MessageConstant;
import person.sinomenium.Static.Tool.ThreadContext;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscussService implements I_DiscussService{

    private final DiscussMap discussMap;
    private final UserDetailMap userDetailMap;

    //查看评论
    @Override
    public Discuss_Select_VO page_query_comment(Integer examId, long end_time, Integer discuss_id) {
        //时间戳转类型
        Timestamp endTime = new Timestamp(end_time);
        //分页查10条数据
        List<Discuss> discussList=discussMap.page_query(examId,endTime,discuss_id);
        //判断是否返回空集合
        if(discussList == null || discussList.size()==0){
            return Discuss_Select_VO.builder()
                    .discussVOList(Collections.emptyList())
                    .endTime(String.valueOf(end_time))
                    .build();
        }
        //实体转VO
        List<DiscussVO> discussVOList=new ArrayList<>();
        discussList.stream().forEach(s->{
            DiscussVO discussVO=new DiscussVO();
            discussVO.setName(s.getName());
            discussVO.setContent(s.getContent());
            discussVO.setId(s.getId().toString());
            Timestamp createTime = s.getCreateTime();
            String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime);
            discussVO.setCreateTime(str);
            discussVO.setAvatar(s.getAvatar());
            discussVO.setExpanded(false);
            discussVO.setReplayVisible(false);
            discussVO.setShowAllReplays(false);
            discussVO.setDiscussNum(discussMap.selectDiscussNum(s.getId(),examId));
            discussVOList.add(discussVO);

        });
        //LocalDateTime转时间戳
        Timestamp endDiscussTime = discussList.get(discussList.size()-1).getCreateTime();
        long epochMilli = endDiscussTime.toInstant().toEpochMilli();
        //返回
        return Discuss_Select_VO.builder()
                .discussVOList(discussVOList)
                .endTime(String.valueOf(epochMilli))
                .build();
    }

    //发布评论
    @Override
    public void save(DiscussDTO discussDTO) {
        String content = discussDTO.getContent();
        //判断内容是否为空
        if(content==null || content.length()==0){
            ThreadContext.addError(400, MessageConstant.CONTENT_IS_NULL);
            return;
        }
        Discuss discuss = new Discuss();
        //select当前用户详细信息
        Integer userId =ThreadContext.getCurrentId();
        LambdaQueryWrapper<UserDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDetail::getUserId,userId);
        List<UserDetail> userDetails = userDetailMap.selectList(wrapper);
        if(userDetails == null ||userDetails.size()==0){
            ThreadContext.addError(400,MessageConstant.USER_NOT_FOUND);
            return;
        }
        UserDetail userDetail = userDetails.get(0);
        //DTO转实体
        BeanUtils.copyProperties(discussDTO,discuss);
        Timestamp timestamp = new Timestamp(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        discuss.setCreateTime(timestamp);
        discuss.setAvatar(userDetail.getAvatar());
        discuss.setName(userDetail.getName());
        discuss.setUserId(userId);
        //插入
        discussMap.insert(discuss);
    }
}
