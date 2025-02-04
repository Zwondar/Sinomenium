package person.sinomenium.Pojo.DTO;

import lombok.Data;

@Data

//管理员注册：邮箱 密码 密钥
public class RootRegisterDTO {
    //邮箱
    private String email;
    //密码
    private String password;
    //再次输入密码
    private String passwords;
    //管理员密钥
    private String adminKey;
}
