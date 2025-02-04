package person.sinomenium.Pojo.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

//管理端查询考核实体类
@Data
public class RootMidSubExam {
    private String answer;
    private Integer id;
}
