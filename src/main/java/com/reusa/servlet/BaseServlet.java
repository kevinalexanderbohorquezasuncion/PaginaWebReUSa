package com.reusa.servlet;

import com.reusa.modelo.Usuario;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public abstract class BaseServlet extends HttpServlet {
    protected Usuario usuario(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null ? null : (Usuario) session.getAttribute("usuario");
    }

    protected void flash(HttpServletRequest request, String mensaje) {
        request.getSession().setAttribute("mensaje", mensaje);
    }

    protected void exigirSesion(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (usuario(request) == null) {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
