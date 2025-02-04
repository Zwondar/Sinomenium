package person.sinomenium.Pojo.VO;

import lombok.Data;
//管理端查看考核实体类
@Data
public class RootViewExam {
    private Integer userID;
    private String classes;
    private Integer ID;
    private String name;

    private String answer;
    private String avatarUrl;
}
