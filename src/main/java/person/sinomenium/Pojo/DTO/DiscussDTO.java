package person.sinomenium.Pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussDTO implements Serializable {
    //考核id
    private Integer examId;
    //评论id
    private Integer discussId;
    //内容
    private String content;
}
