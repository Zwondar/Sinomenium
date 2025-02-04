package person.sinomenium.Server;

import org.springframework.web.multipart.MultipartFile;

public interface I_CommonService {
    //上传文件
    String upload(MultipartFile file);
}
