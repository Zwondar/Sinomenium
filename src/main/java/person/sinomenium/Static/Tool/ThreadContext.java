package person.sinomenium.Static.Tool;

import person.sinomenium.Pojo.Entity.ErrorResult;

//将用户信息隔离在不同线程中，当前线程仅可以访问自己存储的用户信息（也可作为容器使用）
public class ThreadContext {

    public static ThreadLocal<Integer> userId = new ThreadLocal<>();
    public static ThreadLocal<ErrorResult> errorResult = new ThreadLocal<>();
    public static ThreadLocal<Integer> isAdmin = new ThreadLocal<>();
    public static void setCurrentId(Integer id) {
        userId.set(id);
    }
    public static void setErrorResult(ErrorResult result) {
        errorResult.set(result);
    }
    public static void setAdmin(Integer admin) {
        isAdmin.set(admin);
    }
    public static Integer getCurrentId() {
        return userId.get();
    }
    public static ErrorResult getErrorResult() {
        return errorResult.get();
    }
    public static Integer getAdmin() {
        return isAdmin.get();
    }

    public static void removeAll() {
        userId.remove();
        errorResult.remove();
        isAdmin.remove();
    }
    //用于收集异常信息并封装存入threadLocal中，最后由响应统一封装类使用
    public static void addError(Integer code,String message){
        setErrorResult(new ErrorResult(code,message));
    }

}
