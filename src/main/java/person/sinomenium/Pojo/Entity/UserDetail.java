package person.sinomenium.Pojo.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户详情表
 * </p>
 *
 * @author ch
 * @since 2024-10-12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_detail")
public class UserDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户详情id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关联用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 班级
     */
    @TableField("classes")
    private String classes;

    /**
     * 学号
     */
    @TableField("student_id")
    private String studentId;

    /**
     * qq
     */
    @TableField("QQ")
    private String qq;

    /**
     * 号码
     */
    @TableField("phone")
    private String phone;

    /**
     * 学习方向
     */
    @TableField("direction")
    private String direction;

    /**
     * 优势
     */
    @TableField("advantage")
    private String advantage;

    /**
     * 排名
     */
    @TableField("ranking")
    private Integer ranking;

    /**
     * 考核平均分
     */
    @TableField("average")
    private Double average;


}
