package filter;


import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class LoginFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
	    HttpServletResponse hsr = (HttpServletResponse) res;
	    HttpServletRequest request = (HttpServletRequest) req;

	    HttpSession session = request.getSession();
		String login = (String)session.getAttribute("userName");
		if( login==null ){
			hsr.sendRedirect("login.jsp");
		}
	    chain.doFilter(req, res);
	}
}