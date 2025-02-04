package person.sinomenium.Static.Tool;


import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//校验格式工具类
public class FormatCheck {

    /**
     * 校验邮箱
     */
    public static boolean QQEmailCheck(String email){
        //定义正则表达式
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        //生成编译对象
        Pattern compile = Pattern.compile(regex);
        //生成校验对象
        Matcher matcher = compile.matcher(email);
        //返回比对结果
        return matcher.matches();
    }
    /**
     * 校验学号：60 + 2000~今年 + 四位随机数字 样例：6020151016 6020245859
     */
    public static boolean studentIdCheck(String id){
        LocalDate date = LocalDate.now();
        String year = String.valueOf(date.getYear());
        String regex = "^602" + range(year.charAt(1)) + range(year.charAt(2)) + range(year.charAt(3)) + "\\d{4}$";
        return Pattern.compile(regex).matcher(id).matches();
    }
    //正则表达式无法直接表示范围，自定义拼装动态范围
    private static String range(char c){
        return "[0-" + c + "]";
    }
    /**
     * 校验密码格式：由字母和数字组成，8~16位
     */
    public static boolean pwdCheck(String pwd){
        String regex = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,16}$";
        return Pattern.compile(regex).matcher(pwd).matches();
    }
    /**
     * 检验手机号格式：1开头，第二位数字为3~9，剩余9位数字随机
     */
    public static boolean phoneCheck(String phone){
        String regex = "^1[3-9]\\d{9}$";
        return Pattern.compile(regex).matcher(phone).matches();
    }
}
