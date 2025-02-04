package person.sinomenium.Pojo.VO;

import lombok.Data;
//管理端考核功能：排名实体类
@Data
public class RootViewRank {
private Integer ranking;
private String url;
private String classes;
private String name;
private Double average;
private String answerUrl;
}
