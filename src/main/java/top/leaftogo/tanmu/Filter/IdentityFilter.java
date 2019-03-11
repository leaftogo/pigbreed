package top.leaftogo.tanmu.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import top.leaftogo.tanmu.Service.WeiXinService.IdentityService;
import top.leaftogo.tanmu.Service.RedisService;
import top.leaftogo.tanmu.Service.ToolService.ToolService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class IdentityFilter implements Filter {

    @Autowired
    IdentityService identityService;
    @Autowired
    RedisService redisService;
    @Autowired
    ToolService toolService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        if(session.getAttribute("openid") == null){
            StringBuilder builder = new StringBuilder();
            String url = request.getRequestURI();
            String paramStr = request.getQueryString();
            if(paramStr == null){

            }else{
                url += "&"+paramStr;
            }
            builder.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                    "xxx&redirect_uri=xxx&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
            builder.append("");
            builder.append(url);
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect(builder.toString());
        }else{
//            String openid = (String) session.getAttribute("openid");
//            long timeStamp = toolService.getTimeStamp();
//            String sessionId = session.getId();
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
