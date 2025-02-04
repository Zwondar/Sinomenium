package person.sinomenium.Pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Discuss_Select_VO implements Serializable {
    //最后一条评论的时间戳
    private String endTime;
    //返回评论的信息的集合
    private List<DiscussVO> discussVOList=new ArrayList<>();
}
