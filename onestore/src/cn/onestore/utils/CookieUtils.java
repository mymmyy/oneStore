package cn.onestore.utils;

import javax.servlet.http.Cookie;

/**
 * Created by 明柯 on 2017/4/17.
 */
public class CookieUtils {
    //通过request.getCookie 得到的是数组，所以这里用数组接收cookie参数
    public static Cookie getCookieByName(Cookie[] cookie,String cookieName){
        //判断数组中是否有值，没有则返回null
        if(cookie == null || cookie.length == 0){
            return null;
        }
        //对比找到对应cookie的内容
        for (Cookie c:cookie) {
            if(c.getName().equals(cookieName)){
                return c;
            }
        }
        return  null;
    }
}
