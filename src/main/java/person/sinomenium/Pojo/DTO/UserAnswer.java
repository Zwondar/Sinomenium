package person.sinomenium.Pojo.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;

@Data
public class UserAnswer {
    private Integer user_id;
    private Integer exam_id;
    private String fileUrl;
    private LocalDateTime time;
    private Integer count;
}
