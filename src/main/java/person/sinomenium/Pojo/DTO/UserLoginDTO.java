package person.sinomenium.Pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
//用于接收前端的用户登录信息
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO implements Serializable {
    //用户名
    private String email;
    //密码
    private String password;
}
