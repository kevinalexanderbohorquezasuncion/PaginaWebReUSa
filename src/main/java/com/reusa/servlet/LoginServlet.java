package com.reusa.servlet;

import com.reusa.conexion.Conexion;
import com.reusa.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/vistas/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String correo = req.getParameter("correo");
        String clave = req.getParameter("clave");
        String sql = "SELECT id, nombre, correo, clave FROM usuarios WHERE correo = ?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, correo == null ? "" : correo.trim().toLowerCase());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && clave != null && BCrypt.checkpw(clave, rs.getString("clave"))) {
                    req.getSession().setAttribute("usuario",
                        new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("correo")));
                    resp.sendRedirect(req.getContextPath() + "/catalogo");
                } else {
                    req.setAttribute("error", "Correo o contraseña incorrectos.");
                    doGet(req, resp);
                }
            }
        } catch (SQLException ex) {
            req.setAttribute("error", "No se pudo conectar con la base de datos. Revisa la configuración.");
            doGet(req, resp);
        }
    }
}
