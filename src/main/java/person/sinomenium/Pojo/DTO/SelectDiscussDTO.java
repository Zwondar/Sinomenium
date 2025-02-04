package person.sinomenium.Pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectDiscussDTO implements Serializable {
    //考核id
    private Integer exam_id;
    //最后评论时间
    private Long end_time;
    //评论id
    private Integer discuss_id;
}
