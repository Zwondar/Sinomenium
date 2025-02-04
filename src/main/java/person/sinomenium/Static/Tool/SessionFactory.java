package person.sinomenium.Static.Tool;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionFactory {
    private static Map<String, HttpSession> sessionMap=new ConcurrentHashMap<>();



    public static void addSession(String key, HttpSession session) {
        sessionMap.put(key, session);
    }

    public static HttpSession getSession(String key) {
        return sessionMap.get(key);
    }

    public static void removeSession(String key) {
        HttpSession session = getSession(key);
        session.invalidate();
        sessionMap.put(key, session);
    }
}
