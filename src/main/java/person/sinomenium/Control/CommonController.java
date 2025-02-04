package person.sinomenium.Control;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import person.sinomenium.Pojo.VO.ResultVO;
import person.sinomenium.Server.I_CommonService;

/**
 * 通用接口
 */
@RestController
@RequestMapping("/qingteng-recruitment/user/common")
@Slf4j
public class CommonController {
    @Autowired
    private I_CommonService commonService;

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public ResultVO upload(MultipartFile file) {//参数名与body中的名称保持一致实现映射
        log.info("文件上传：{}", file);
        if(file == null)
            return ResultVO.error(400,"文件不能为空");
        String url = commonService.upload(file);
        //返回url给前端
        return ResultVO.success(url,"文件上传成功");
    }

}
