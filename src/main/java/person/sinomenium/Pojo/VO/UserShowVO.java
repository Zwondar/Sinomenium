package person.sinomenium.Pojo.VO;

import lombok.Data;

@Data
public class UserShowVO {
    //姓名
    private String name;
    //头像
    private String avatar;
    //班级
    private String classes;
    //学号
    private String studentId;
    //qq
    private String qq;
    //号码
    private String phone;
    //学习方向
    private String direction;
    //优势
    private String advantage;
}
