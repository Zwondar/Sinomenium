package person.sinomenium.Pojo.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * 后端统一返回结果
 * 负责将单个VO对象或VO对象数组统一装载到data中返回给前端（将VO对象或数组传入success（）方法中）
 * 不需要返回data对象的调用无参success（）方法即可
 * @param <T>
 */
@Data
public class ResultVO<T> implements Serializable {

    private Integer code; //响应码
    private T data; //数据
    private String message;//信息

    public static <T> ResultVO<T> success() {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.code = 200;
        return resultVO;
    }

    public static <T> ResultVO<T> success(String message) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.code = 200;
        resultVO.message = message;
        return resultVO;
    }

    public static <T> ResultVO<T> success(T object) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.data = object;
        resultVO.code = 200;
        return resultVO;
    }

    public static <T> ResultVO<T> success(T object,String message) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.data = object;
        resultVO.code = 200;
        resultVO.message = message;
        return resultVO;
    }

    public static ResultVO<Integer> error(Integer code,String message) {
        ResultVO<Integer> resultVO = new ResultVO<>();
        resultVO.code = code;
        resultVO.message = message;
        return resultVO;
    }

}
