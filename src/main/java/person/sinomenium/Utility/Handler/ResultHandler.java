package person.sinomenium.Utility.Handler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import person.sinomenium.Pojo.VO.ResultVO;
import person.sinomenium.Pojo.Entity.ErrorResult;
import person.sinomenium.Static.Tool.ThreadContext;

import java.nio.charset.StandardCharsets;

//全局Result返回处理类
//使用说明：业务逻辑中需要返回错误信息时调用ThreadContext的addError方法，并保证业务逻辑正常返回（有返回值返回null，否则直接返回即可）
/**
 * 注意事项
 * 1、仅当请求的返回值能被标注为@ResponseBody（例如自定义的VO对象）才能自动包装，若返回值单一请自行调用Result的success有参方法
 * 2、无需传输data数据时建议使用Result的success无参方法
 * 3、该类不影响已主动使用ResultVO封装的返回值（除非添加并成功调用了addError（）），业务判断逻辑较少时也可直接return null，自己调用Result的error方法
 */

@RestControllerAdvice
@Slf4j
public class ResultHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ErrorResult result = ThreadContext.getErrorResult();
        log.info("返回错误信息：{}", result);
        ThreadContext.removeAll();
        //需要返回错误信息
        if(result != null) {
            return ResultVO.error(result.getErrorCode(), result.getErrorMessage());
        }
        //返回数据已被封装则直接返回
        if (body instanceof ResultVO<?>) {
            return body;
        }
        //错误信息为空且未主动封装，返回success有参方法
        return ResultVO.success(body);
    }

}
