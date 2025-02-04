package person.sinomenium.Utility.Properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//将yml文件中jwt的属性配置到类中方便使用
@Component
@Data
@ConfigurationProperties("sinomenium.jwt")
public class JwtProperties {
    /**
     * 用户生成jwt令牌
     */
    //密钥
    private String userSecretKey;
    //过期时间
    private Long userTtl;
    //令牌名称（需要与前端的参数名保持一致，一般使用token或Authorization）
    private String userTokenName;
}
