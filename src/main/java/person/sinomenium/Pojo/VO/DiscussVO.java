package person.sinomenium.Pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import person.sinomenium.Pojo.Entity.Discuss;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//用于返回给前端信息的评论对象
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DiscussVO implements Serializable {
    //评论id
    private String id;
    //头像
    private String avatar;
    //姓名
    private String name;
    //创建时间
    private String createTime;
    //内容
    private String content;
    //回复评论数
    private Integer discussNum;

    private Boolean expanded;
    //是否展示回复评论窗口
    private Boolean replayVisible;
    //展示所有回复
    private Boolean showAllReplays;
    //逆天空数组
    private List<Discuss> replays=new ArrayList<>();
}
