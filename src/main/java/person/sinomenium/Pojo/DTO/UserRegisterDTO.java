package person.sinomenium.Pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

//用于接收前端的用户注册信息
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO implements Serializable {
    //邮箱
    private String email;
    //密码
    private String password;
    //二次校验密码
    private String passwords;
    //验证码
    private String code;
}
