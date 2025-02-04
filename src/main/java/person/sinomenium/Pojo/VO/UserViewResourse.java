package person.sinomenium.Pojo.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class UserViewResourse {
    private String name;
    private Integer id;
    private String file;
}
