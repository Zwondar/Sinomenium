package person.sinomenium.Pojo.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
//管理端编辑考核实体类
@Data
public class RootEditorExam {
    private Integer id;
    private Integer user_id;
    private String name;
    private String beginTime;
    private String endTime;
    private LocalDateTime begin_time;
    private LocalDateTime end_time;
    private String fileUrl;
}
