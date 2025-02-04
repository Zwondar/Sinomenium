package person.sinomenium.Pojo.VO;

import lombok.Builder;
import lombok.Data;
//查询所有管理员返回数据
@Data
@Builder
public class AdminsVO {
    //姓名
    private String name;
    //学号
    private String studentId;
    //邮箱
    private String email;
}
