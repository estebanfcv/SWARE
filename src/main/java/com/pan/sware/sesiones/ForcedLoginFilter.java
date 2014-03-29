package com.pan.sware.sesiones;

import com.pan.sware.TO.UsuarioTO;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ForcedLoginFilter implements Filter {

    private static synchronized boolean checarEstadoSesion(HttpSession session) {
        if (session == null) {
            return false;
        }
        UsuarioTO usuario = ManejadorSesiones.getUsuariosRegistrados().get(session.getId());
        if (usuario == null) {
            return false;
        }
        return true;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        boolean sesionValida = checarEstadoSesion(session);
        if (!sesionValida) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.xhtml");
            requestDispatcher.forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
