package person.sinomenium.Server;

public interface I_SubmitExamService {
    //查用户考核的评分
    Double selectScore(String examId);
}
