package person.sinomenium.Utility.Handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import person.sinomenium.Pojo.VO.ResultVO;
import person.sinomenium.Static.Constant.MessageConstant;
import person.sinomenium.Utility.Exception.BaseException;
import person.sinomenium.Utility.Exception.LoginCheckException;

/**
 * 全局异常处理器，处理项目中抛出的所有异常并将错误信息返回给前端
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 捕获未处理异常
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResultVO<Integer> unknownExceptionHandler(RuntimeException e){
        e.printStackTrace();
        return ResultVO.error(500,MessageConstant.UNKNOWN_ERROR);
    }

    /**
     * 捕获已处理异常
     */
    @ExceptionHandler(value = BaseException.class)
    public ResultVO<Integer> knownExceptionHandler(BaseException exception){
        if (exception instanceof LoginCheckException)
            return ResultVO.error(401,exception.getMessage());
        return ResultVO.error(500,exception.getMessage());
    }
}
