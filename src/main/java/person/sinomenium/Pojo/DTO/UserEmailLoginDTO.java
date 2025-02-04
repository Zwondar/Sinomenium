package person.sinomenium.Pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
//用于接收前端的用户邮箱登录信息
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEmailLoginDTO implements Serializable {
    //邮箱
    private String email;
    //验证码
    private String code;
}
