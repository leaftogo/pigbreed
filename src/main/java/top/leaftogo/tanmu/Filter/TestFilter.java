package top.leaftogo.tanmu.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import top.leaftogo.tanmu.Service.ControllerService.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class TestFilter implements Filter {

    @Autowired
    UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        if(session.getAttribute("openid") == null){
            String openid = "test_openid:for_test-used_only";
            session.setAttribute("openid",openid);
            userService.register(openid);//用户注册
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
