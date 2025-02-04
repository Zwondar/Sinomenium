package person.sinomenium.Server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import person.sinomenium.DAO.FilesMap;
import person.sinomenium.DAO.UserMap;
import person.sinomenium.Pojo.Entity.Files;
import person.sinomenium.Pojo.Entity.User;
import person.sinomenium.Static.Constant.MessageConstant;
import person.sinomenium.Static.Constant.UserConstant;
import person.sinomenium.Static.Tool.ThreadContext;
import person.sinomenium.Utility.Exception.BaseException;
import person.sinomenium.Utility.Util.AliOSSUtilsUpload;

import java.io.IOException;

@Service
@Slf4j
public class CommonService implements I_CommonService{
    @Autowired
    private AliOSSUtilsUpload aliOssUtil;
    @Autowired
    private UserMap userMap;
    @Autowired
    private FilesMap filesMap;
    public String upload(MultipartFile file) {
        String url;
        //获取文件大小
        long size = file.getSize();
        log.info("文件名称：{}",file.getOriginalFilename());
        //获取当前用户id
        Integer userId = ThreadContext.getCurrentId();
        //获取用户容量（如果是管理员则容量为4g）
        Long component = userMap.getComponentById(userId);
        //如果是用户上传文件则进行容量判断
        if (ThreadContext.getAdmin().equals(UserConstant.USER)) {
            if(size > component) {
                //文件超过用户容量限制，直接抛异常终止，避免文件继续上传
                throw new BaseException(MessageConstant.OUT_OF_COMPONENT);
            }else {
                component -= size;
            }
        }
        //获取文件路径
        try {
            url = aliOssUtil.upload(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //封装文件实体
        Files files = Files.builder()
                .userId(userId)
                .size(size)
                .url(url)
                .build();
        //存储到文件表中
        filesMap.insert(files);
        //更新用户容量
        if(component != null) {
            User user = User.builder().id(userId).component(component).build();
            userMap.updateById(user);
        }
        //返回文件url
        return url;
    }
}
