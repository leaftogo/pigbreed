package top.leaftogo.tanmu.Filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import top.leaftogo.tanmu.Service.ControllerService.UserService;
import top.leaftogo.tanmu.Service.WeiXinService.IdentityService;
import top.leaftogo.tanmu.Service.ToolService.ToolService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class RedirectBackFilter implements Filter {

    @Autowired
    IdentityService identityService;
    @Autowired
    UserService userService;
    @Autowired
    ToolService toolService;
    private static final Logger logger = LoggerFactory.getLogger(RedirectBackFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    //userService.register(openid);//用户注册
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if(request.getParameter("oauthstatus") == null){
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            String openid = request.getParameter("openid");
            if (openid == null) {
                //openid不合法，请求拦截
                log.info("openid为null");
            } else {
                //搜索数据库，查看openid是否已经注册
                if (userService.hasOpenid(openid)) {
                    //已经注册
                    HttpSession session = request.getSession();
                    session.setAttribute("openid", openid);
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    log.error("openid验证失败,openid为"+openid);

                }
            }
        }
    }

    @Override
    public void destroy() {

    }
}
