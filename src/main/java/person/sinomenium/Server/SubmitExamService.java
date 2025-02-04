package person.sinomenium.Server;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import person.sinomenium.DAO.SubmitExamMap;
import person.sinomenium.Pojo.Entity.ErrorResult;
import person.sinomenium.Pojo.Entity.SubmitExam;
import person.sinomenium.Static.Constant.MessageConstant;
import person.sinomenium.Static.Tool.ThreadContext;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmitExamService implements I_SubmitExamService{
    private final SubmitExamMap submitExamMap;

    /**
     * 查用户考核评分
     * @param examId
     * @return
     */
    @Override
    public Double selectScore(String examId) {
        LambdaQueryWrapper<SubmitExam> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SubmitExam::getUserId,ThreadContext.getCurrentId());
        wrapper.eq(SubmitExam::getExamId,Integer.valueOf(examId));
        List<SubmitExam> submitExams = submitExamMap.selectList(wrapper);
        if(submitExams == null ||submitExams.size()==0){
            ThreadContext.setErrorResult(new ErrorResult(400, MessageConstant.SUBMIT_EXAM_IS_NULL));
            return null;
        }
        return submitExams.get(0).getGrades();

    }
}
