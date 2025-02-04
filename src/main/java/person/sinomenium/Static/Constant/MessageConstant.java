package person.sinomenium.Static.Constant;

//统一管理各异常类的异常信息
public class MessageConstant {
    //用户类
    public static final String USER_NOT_FOUND = "用户不存在";
    public static final String USER_EXISTED = "用户已存在";
    //管理员类
    public static final String ADMIN_EXISITED = "管理员已存在";
    //密码类
    public static final String PASSWORD_WRONG = "密码错误";
    public static final String PASSWORD_INCONSISTENT = "前后密码不一致";
    //验证码类
    public static final String CODE_WRONG = "验证码错误";
    public static final String CODE_EXISTED = "验证码未过期，请勿频繁发送";
    //邮箱类
    public static final String EMAIL_CODE_WRONG = "邮箱与验证码无关联";
    //密钥类
    public static final String ADMIN_KEY_ERROR = "密钥错误";
    //评论类
    public static final String CONTENT_IS_NULL = "评论不能为空";
    //考核类
    public static final String EXAM_IS_NULL="考核不存在";
    //文件上传类
    public static final String FILE_OVERLOAD = "文件过大";
    public static final String OUT_OF_COMPONENT = "用户容量不足";
    //运行时异常类
    public static final String UNKNOWN_ERROR = "未知错误，请联系管理员处理";
    //格式错误类
    public static final String PASSWORD_FORMAT_WRONG = "密码格式错误";
    public static final String EMAIL_FORMAT_WRONG = "邮箱格式错误";
    public static final String PHONE_FORMAT_WRONG = "号码格式错误";
    public static final String STUDENT_ID_FORMAT_WRONG = "学号格式错误";
    public static final String SUBMIT_EXAM_IS_NULL="考核未提交";
    //资源类
    public static final String RESOURCE_IS_NULL="资源不存在";

}
