package person.sinomenium.Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

//错误信息类，非数据库表
@Data
@AllArgsConstructor
public class ErrorResult {
    public Integer errorCode;
    public String errorMessage;
}

