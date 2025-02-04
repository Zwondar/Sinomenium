package person.sinomenium.Pojo.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * 考核发布表
 * </p>
 *
 * @author ch
 * @since 2024-10-12
 */
@Data
@TableName("resource")
public class Resource implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 资源id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 发布者id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 冗余字段——发布者姓名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 资源名称
     */
    @TableField("name")
    private String name;

    /**
     * 资源文件
     */
    @TableField("file")
    private String file;

}
