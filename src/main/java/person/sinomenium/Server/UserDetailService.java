package person.sinomenium.Server;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import person.sinomenium.DAO.UserDetailMap;
import person.sinomenium.Pojo.DTO.UserEditDTO;
import person.sinomenium.Pojo.Entity.User;
import person.sinomenium.Pojo.Entity.UserDetail;
import person.sinomenium.Pojo.VO.UserShowVO;
import person.sinomenium.Static.Constant.MessageConstant;
import person.sinomenium.Static.Tool.FormatCheck;
import person.sinomenium.Static.Tool.ThreadContext;

@Service
public class UserDetailService implements I_UserDetailService{
    @Autowired
    private UserDetailMap userDetailMap;

    //个人信息展示
    public UserShowVO showUserDetail() {
        //根据用户id查询个人详细信息
        UserDetail userDetail = userDetailMap.selectByUserId(ThreadContext.getCurrentId());
        //如果查询失败
        if(userDetail == null){
            ThreadContext.addError(400, MessageConstant.USER_NOT_FOUND);
            return null;
        }
        //封装返回对象
        UserShowVO userShowVO = new UserShowVO();
        BeanUtil.copyProperties(userDetail,userShowVO);
        return userShowVO;
    }

    //个人信息编辑
    public void editUserDetail(UserEditDTO dto){
        String studentId = dto.getStudentId();
        String phone = dto.getPhone();
        //检验学号格式
        if(! FormatCheck.studentIdCheck(studentId)){
            ThreadContext.addError(400,MessageConstant.STUDENT_ID_FORMAT_WRONG);
            return;
        }
        //检验手机号格式
        if(! FormatCheck.phoneCheck(phone)){
            ThreadContext.addError(400,MessageConstant.PHONE_FORMAT_WRONG);
            return;
        }
        //获取要更新的用户信息表id
        Integer id = userDetailMap.selectIdByUserId(ThreadContext.getCurrentId());
        //封装数据库对象
        UserDetail userDetail = new UserDetail();
        BeanUtil.copyProperties(dto,userDetail);
        userDetail.setId(id);
        //更新详细信息表
        userDetailMap.updateById(userDetail);

    }

}
