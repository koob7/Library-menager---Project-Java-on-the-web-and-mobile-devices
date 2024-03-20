package pl.polsl.lab.filters;

import java.io.IOException;
import java.util.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.*;


@WebFilter(filterName = "SecondsFilter", urlPatterns = {"/*"},
        initParams = {@WebInitParam(name = "mood", value = "awake")})
public class SecondsFilter implements Filter {

    String mood = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        mood = filterConfig.getInitParameter("mood");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        
        Calendar cal = GregorianCalendar.getInstance();
        switch (cal.get(Calendar.SECOND)/10) {
            case 0:
                mood = "sleepy";
                break;
            case 1:
                mood = "hungry";
                break;
            case 2:
                mood = "alert";
                break;
            case 3:
                mood = "in need of coffee";
                break;
            case 4:
                mood = "thoughtful";
                break;
            default:
                mood = "lethargic";
        }
        request.setAttribute("mood", mood);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
