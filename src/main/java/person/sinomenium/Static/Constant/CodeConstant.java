package person.sinomenium.Static.Constant;

//各错误码对应错误，仅作参考，可选择性使用
public class CodeConstant {
    //客户端错误
    public static final Integer Bad_Request = 400;//服务器无法理解请求格式，不应重复发送此请求
    public static final Integer Unauthorized = 401;//请求需要用户的身份认证
    public static final Integer Forbidden = 404;//请求失败，请求需要的资源未在服务器上被发现
    public static final Integer Not_Found = 405;//请求行中指定的方法不允许应用于请求的资源
    public static final Integer Too_Many_Requests = 429;//用户在给定的时间内发送了太多请求

    //服务端错误
    public static final Integer Internal_Server_Error = 500;// 服务器遇到了不知道如何处理的情况
    public static final Integer Not_Implemented = 501;//服务器不支持请求的功能
    public static final Integer Bad_Gateway = 502;//作为网关或代理工作的服务器从上游服务器收到了无效响应
    public static final Integer Service_Unavailable = 503;//临时服务器维护或过载，无法调用请求

}
