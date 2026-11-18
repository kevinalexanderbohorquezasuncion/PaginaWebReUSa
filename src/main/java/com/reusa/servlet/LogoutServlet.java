package com.reusa.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getSession(false) != null) req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
