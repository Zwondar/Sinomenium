package person.sinomenium.Pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
//用于返回给前端信息的数据对象
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVO implements Serializable {
    //jwt令牌
    private String token;
    //权限：用于前端判断跳转路由
    private Integer admin;
}
