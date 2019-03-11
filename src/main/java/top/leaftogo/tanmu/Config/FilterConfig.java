package top.leaftogo.tanmu.Config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import top.leaftogo.tanmu.Filter.*;

import javax.servlet.Filter;

@Configuration
public class FilterConfig {

//    @Bean
//    public Filter IdentityFilter(){
//        return new IdentityFilter();
//    }
//
//    @Bean
//    public FilterRegistrationBean IdentityRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new DelegatingFilterProxy("IdentityFilter"));
//        registration.addUrlPatterns("/login/redirect/*");
//        registration.setName("IdentityFilter");
//        registration.setOrder(3);
//        return registration;
//    }
//
//    @Bean
//    public Filter RedirectBackFilter(){
//        return new RedirectBackFilter();
//    }
//
//    @Bean
//    public FilterRegistrationBean RedirectBackFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new DelegatingFilterProxy("RedirectBackFilter"));
//        registration.addUrlPatterns("/login/redirect/*");
//        registration.setName("RedirectBackFilter");
//        registration.setOrder(2);
//        return registration;
//    }
//
//    @Bean
//    public Filter SessionFilter(){
//        return new SessionFilter();
//    }
//
//    @Bean
//    public FilterRegistrationBean SessionFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new DelegatingFilterProxy("SessionFilter"));
//        registration.addUrlPatterns("/*");
//        registration.setName("SessionFilter");
//        registration.setOrder(4);
//        return registration;
//    }

    //测试时为避免无法获取openid保存在session中，设置此过滤器。作用：为所有请求设置唯一的身份openid，即所有请求将默认为同一客户端发出。
    @Bean
    public Filter TestFilter(){
        return new TestFilter();
    }

    @Bean
    public FilterRegistrationBean TestFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("TestFilter"));
        registration.addUrlPatterns("/*");
        registration.setName("TestFilter");
        registration.setOrder(1);
        return registration;
    }

}
