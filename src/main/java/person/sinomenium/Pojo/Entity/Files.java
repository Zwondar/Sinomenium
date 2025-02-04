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
 * 文件表
 * </p>
 *
 * @author ch
 * @since 2024-10-15
 */
@Data
@Builder
@TableName("files")
public class Files implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 上传者的id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 文件路径
     */
    @TableField("url")
    private String url;

    /**
     * 文件大小
     */
    @TableField("size")
    private Long size;


}
