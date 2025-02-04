package person.sinomenium.Pojo.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.sql.Timestamp;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author ch
 * @since 2024-10-08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("discuss")
public class Discuss implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 评论id
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
     * 回复评论的id
     */
    @TableField("discuss_id")
    private Integer discussId;

    /**
     * 冗余字段——姓名
     */
    @TableField("name")
    private String name;

    /**
     * 冗余字段——头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 评论内容
     */
    @TableField("content")
    private String content;

    /**
     * 创建日期和时间
     */
    @TableField("create_time")
    private Timestamp createTime;


}
