package person.sinomenium.Pojo.VO;

import lombok.Data;

/*
管理员查看用户信息列表功能的返回实体类
 */
@Data
public class RootViewList {
    private Integer ranking;//排名
    private String url;//图片地址
    private String classes;//班级
    private String name;//姓名
    private Double average;//平均分
}
