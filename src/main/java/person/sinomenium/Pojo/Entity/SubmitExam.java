package person.sinomenium.Pojo.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;


/**
 * <p>
 * 考核提交表
 * </p>
 *
 * @author ch
 * @since 2024-10-08
 */
@Data
@Builder
@TableName("submit_exam")
public class SubmitExam implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 考核提交id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 对应考核id
     */
    @TableField("exam_id")
    private Integer examId;

    /**
     * 所属用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 答案文档url
     */
    @TableField("answer")
    private String answer;

    /**
     * 考核分数
     */
    @TableField("grades")
    private Double grades;
    /**
     * 最后操作时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


}
