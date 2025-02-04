package person.sinomenium.Pojo.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author ch
 * @since 2024-10-12
 */
@Data
@Builder
@TableName("user")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 盐值
     */
    @TableField("salt")
    private String salt;

    /**
     * 电子邮件
     */
    @TableField("email")
    private String email;

    /**
     * 管理权限
     */
    @TableField("admin")
    private Integer admin;

    /**
     * 用户容量
     */
    @TableField("component")
    private Long component;

}
