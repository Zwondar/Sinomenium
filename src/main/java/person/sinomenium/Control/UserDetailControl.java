package person.sinomenium.Control;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import person.sinomenium.Pojo.DTO.UserEditDTO;
import person.sinomenium.Pojo.VO.ResultVO;
import person.sinomenium.Pojo.VO.UserShowVO;
import person.sinomenium.Server.UserDetailService;

/**
 * 用户端接口
 */
@RestController
@RequestMapping("/qingteng-recruitment/user/detail")
@Slf4j
public class UserDetailControl {
    @Autowired
    private UserDetailService userDetailService;

    /**
     * 用户个人信息展示
     */
    @PostMapping("/show")
    public ResultVO<UserShowVO> showUserDetail(){
        log.info("用户申请个人信息展示");
        UserShowVO userShowVO = userDetailService.showUserDetail();
        return ResultVO.success(userShowVO);
    }

    /**
     * 用户个人信息编辑
     */
    @PostMapping("/edit")
    public ResultVO<Object> editUserDetail(@RequestBody UserEditDTO userEditDTO){
        log.info("用户信息编辑：{}", userEditDTO);
        userDetailService.editUserDetail(userEditDTO);
        return ResultVO.success("编辑成功");
    }
}
