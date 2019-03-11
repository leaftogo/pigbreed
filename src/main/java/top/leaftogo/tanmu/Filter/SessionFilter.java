package top.leaftogo.tanmu.Filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import top.leaftogo.tanmu.Service.WeiXinService.IdentityService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class SessionFilter implements Filter {


    @Autowired
    IdentityService identityService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        if(session.getAttribute("openid") == null){
            log.info("sessionFilter拦截");
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json");
            response.getWriter().write("{\"error_code\":\"1003\",\"error_msg\":\"请重新访问微信平台入口\"");
        }else {
            filterChain.doFilter(servletRequest, servletResponse);//放行
        }
    }

    @Override
    public void destroy() {

    }
}
