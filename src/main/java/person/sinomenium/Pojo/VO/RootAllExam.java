package person.sinomenium.Pojo.VO;

import lombok.Data;
//管理端考核功能:展示：实体类
@Data
public class RootAllExam {
    private Integer id;//考核id
    private String name;//第xx次考核
    private String start_date;//开始时间
    private String end_time;//结束时间
    private String fileUrl;
    private Integer count;
}
