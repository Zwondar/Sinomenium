package person.sinomenium.Pojo.Entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * 考核发布表
 * </p>
 *
 * @author ch
 * @since 2024-10-11
 */
@Data
@TableName("exam")
public class Exam implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 考核发布id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 发布者
     */
    private Integer userId;

    /**
     * 考核名称
     */
    private String name;

    /**
     * 考核文件url
     */
    private String file;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;


}
