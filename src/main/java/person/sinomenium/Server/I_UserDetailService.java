package person.sinomenium.Server;

import person.sinomenium.Pojo.DTO.UserEditDTO;
import person.sinomenium.Pojo.VO.UserShowVO;

public interface I_UserDetailService {
    //用户信息展示
    UserShowVO showUserDetail();
    //用户信息编辑
    void editUserDetail(UserEditDTO dto);
}
