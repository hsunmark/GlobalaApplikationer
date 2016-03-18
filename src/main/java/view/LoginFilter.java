package view;

import view.RecruitmentManager;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Adam on 2016-03-16.
 */
public class LoginFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        RecruitmentManager manager = (RecruitmentManager)((HttpServletRequest)request)
                .getSession().getAttribute("recruitmentManager");
        String contextPath = ((HttpServletRequest)request).getContextPath();

        if (manager == null || !manager.getLoginSuccess()) {
            ((HttpServletResponse)response).sendRedirect(contextPath + "/index.xhtml");
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

}
