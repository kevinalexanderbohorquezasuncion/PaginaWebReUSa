package com.reusa.servlet;

import com.reusa.conexion.Conexion;
import com.reusa.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.sql.*;

@WebServlet("/registro")
public class RegistroServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/vistas/registro.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String nombre = req.getParameter("nombre");
        String correo = req.getParameter("correo");
        String clave = req.getParameter("clave");

        if (nombre == null || correo == null || clave == null
                || nombre.isBlank() || correo.isBlank() || clave.length() < 8) {
            req.setAttribute("error", "Completa todos los campos. La contraseña debe tener al menos 8 caracteres.");
            doGet(req, resp);
            return;
        }

        String sql = "INSERT INTO usuarios(nombre, correo, clave) VALUES (?, ?, ?) RETURNING id";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nombre.trim());
            ps.setString(2, correo.trim().toLowerCase());
            ps.setString(3, BCrypt.hashpw(clave, BCrypt.gensalt(12)));
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                req.getSession().setAttribute("usuario",
                    new Usuario(rs.getInt(1), nombre.trim(), correo.trim().toLowerCase()));
            }
            resp.sendRedirect(req.getContextPath() + "/catalogo");
        } catch (SQLException ex) {
            req.setAttribute("error", "No se pudo crear la cuenta. Comprueba que el correo no esté registrado.");
            doGet(req, resp);
        }
    }
}
