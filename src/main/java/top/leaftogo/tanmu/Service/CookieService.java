package top.leaftogo.tanmu.Service;


import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CookieService {

    public String getCookieValueByName(HttpServletRequest request, String cookieName){
        Cookie[] cookies =  request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(cookieName)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public void writeCookie(HttpServletResponse response, String cookieName,String value,int timeout){
        Cookie cookie = new Cookie(cookieName,value);
        cookie.setPath("/");
        cookie.setMaxAge(timeout);    //秒
        response.addCookie(cookie);
    }
}
