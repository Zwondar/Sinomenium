package person.sinomenium.Server;

import person.sinomenium.Pojo.DTO.*;
import person.sinomenium.Pojo.VO.*;
import java.util.List;
public interface I_RootServer {
    //管理端查看用户信息列表功能服务接口
    public List<RootViewList> RootViewListServer();
    //管理端考核功能:展示服务接口
    public List<RootAllExam> RootAllExamServer();
    //管理端考核功能：排名服务接口
    public List<RootViewRank> RootViewRankServer(Integer id);
    //管理端查看考核服务接口
    public List<RootViewExam> RootViewExamServer(Integer id);
    //管理端编辑考核服务接口
    public void RootEditorExam(RootEditorExam rootEditorExam);
    //管理端删除考核服务接口
    public void RootDeleteExamServer(Integer id);
    //管理端展示资源服务接口
    public List<RootViewAssets> RootViewAssetsServer();
    //管理端编辑资源服务接口
    public void RootEditorResourceServer(EditorResource editorResource);
    //管理端删除资源
public void RootDeleteSource(Integer id);

public List<RootViewNullGrade> RootViewNullGradeServer(int exam_id);
    //管理员评分功能
    void score(RankDTO rankDTO);
    //添加管理员
    AdminsVO addAdmin(String email);
    //删除管理员
    void deleteAdmin(String email);
    //查询所有管理员
    List<AdminsVO> selectAdmins();
}
