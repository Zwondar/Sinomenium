package person.sinomenium.Control;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import person.sinomenium.Pojo.DTO.*;
import person.sinomenium.Pojo.VO.*;
import person.sinomenium.Pojo.DTO.RankDTO;
import person.sinomenium.Pojo.DTO.RootEditorExam;
import person.sinomenium.Pojo.VO.RootAllExam;
import person.sinomenium.Pojo.VO.RootViewExam;
import person.sinomenium.Pojo.VO.RootViewList;
import person.sinomenium.Pojo.VO.RootViewRank;
import person.sinomenium.Pojo.VO.ResultVO;
import person.sinomenium.Server.I_RootServer;

import java.util.List;

/**
 * 管理员接口
 */
@RestController
@RequestMapping("/qingteng-recruitment/root")
@Slf4j
public class RootControl {
    @Autowired
    I_RootServer i_rootServer;

    /**
     * 管理端查看用户信息列表功能
     */
    @PostMapping("/select_user")
    public ResultVO<List<RootViewList>> RootViewListControl()
    {

        List<RootViewList> rvl=i_rootServer.RootViewListServer();

        return ResultVO.success(rvl,"返回成功");
    }

    /**
     * 管理端考核功能:展示
     */
    @PostMapping("/display_exam")
    public ResultVO<List<RootAllExam>> rootAllExamControl()
    {
        List<RootAllExam> rae=i_rootServer.RootAllExamServer();
        return ResultVO.success(rae,"考核资源展示成功");
    }

    /**
     * 管理端考核功能：排名
     */
    @PostMapping("/examine_ranking")
    public ResultVO<List<RootViewRank>> rootViewRankControl(Integer id)
    {
        List<RootViewRank> rvr=i_rootServer.RootViewRankServer(id);

        return ResultVO.success(rvr,"考核资源排名展示成功");
    }

    /**
     * 管理端查看考核
     * id：查看的考核id
     */
    @PostMapping("/select_exam")
    public ResultVO<List<RootViewExam>> rootViewExamControl(Integer id)
    {

    List<RootViewExam> rve=i_rootServer.RootViewExamServer(id);

    return ResultVO.success(rve,"考核成功返回");
    }

    /**
     * 管理端编辑考核
     */
    @PostMapping("/edit_exam")
    public ResultVO rootEditorExamControl(@RequestBody RootEditorExam rootEditorExam) {
        log.info("管理员编辑考核信息：{}",rootEditorExam);
      i_rootServer.RootEditorExam(rootEditorExam);

      return ResultVO.success("考核编辑成功");
    }
    /**
     * 管理端删除考核
     */
    @PostMapping("/examine_delete")
    public ResultVO rootDeleteExam(Integer id)
    {
        System.out.println(id);
    i_rootServer.RootDeleteExamServer(id);
    return ResultVO.success("考核删除成功");
    }

    /**
     * 管理员展示资源
     */
    @PostMapping("/display_resource")
    public ResultVO<List<RootViewAssets>> RootViewAssetsControl()
    {
        List<RootViewAssets> rootViewAssets = i_rootServer.RootViewAssetsServer();

        return ResultVO.success(rootViewAssets,"资源展示成功");
    }

    /**
     * 管理员编辑资源
     */
    @PostMapping("/resource_edit")
    public ResultVO RootEditorResource(@RequestBody EditorResource editorResource){
       i_rootServer.RootEditorResourceServer(editorResource);
       return ResultVO.success("资源编辑成功");
    }

    /**
     * 管理员删除资源
     */
    @PostMapping("/resource_delete")
    public ResultVO RootDeleteResource(Integer id)
    {
     i_rootServer.RootDeleteSource(id);
     return ResultVO.success("资源删除成功");
    }

    @PostMapping("/scored_select")
    public ResultVO<List<RootViewNullGrade>> RootViewNullGrade(int exam_id)
    {
        List<RootViewNullGrade> rvng=i_rootServer.RootViewNullGradeServer(exam_id);
        return ResultVO.success(rvng);
    }


    /**
     * 管理员评分功能
     */
    @PostMapping("/score")
    public ResultVO<Object> score(@RequestBody RankDTO rankDTO){
        i_rootServer.score(rankDTO);
        return ResultVO.success("评分成功");
    }

    /**
     * 超级管理员添加管理员
     */
    @PostMapping("/add_administrator")
    public ResultVO<AdminsVO> addAdmin(String email){
        AdminsVO adminsVO = i_rootServer.addAdmin(email);
        return ResultVO.success(adminsVO,"添加成功");
    }

    /**
     * 超级管理员删除管理员
     */
    @PostMapping("/delete_administrator")
    public ResultVO<Object> deleteAdmin(String email){
        i_rootServer.deleteAdmin(email);
        return ResultVO.success("删除成功");
    }

    /**
     * 超级管理员查询管理员
     */
    @PostMapping("/select_administrator")
    public ResultVO<List<AdminsVO>> selectAdmin(){
        List<AdminsVO> adminsVOS = i_rootServer.selectAdmins();
        return ResultVO.success(adminsVOS);
    }
}
