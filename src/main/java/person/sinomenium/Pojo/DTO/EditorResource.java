package person.sinomenium.Pojo.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
//管理端编辑资源实体类
@Data
public class EditorResource {
    private Integer id;
    private String name;
    private String user_name;
    private Integer user_id;
    private MultipartFile file;
    private String fileUrl;
}
