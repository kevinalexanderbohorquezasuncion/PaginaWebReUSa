package com.reusa.servlet;

import com.reusa.conexion.Conexion;
import com.reusa.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;

@WebServlet("/publicar")
public class ArticuloServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (usuario(req) == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        req.getRequestDispatcher("/WEB-INF/vistas/publicar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Usuario u = usuario(req);
        if (u == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        req.setCharacterEncoding("UTF-8");
        String nombre = req.getParameter("nombre");
        String categoria = req.getParameter("categoria");
        String descripcion = req.getParameter("descripcion");
        String estado = req.getParameter("estado");
        try {
            BigDecimal precio = new BigDecimal(req.getParameter("precio"));
            if (nombre == null || nombre.isBlank() || categoria == null
                    || categoria.isBlank() || precio.signum() <= 0) throw new IllegalArgumentException();
            String sql = """
                INSERT INTO articulos(nombre,categoria,descripcion,precio,estado,vendedor_id)
                VALUES(?,?,?,?,?,?)
                """;
            try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setString(1, nombre.trim());
                ps.setString(2, categoria);
                ps.setString(3, descripcion == null ? "" : descripcion.trim());
                ps.setBigDecimal(4, precio);
                ps.setString(5, estado == null ? "Bueno" : estado);
                ps.setInt(6, u.getId());
                ps.executeUpdate();
            }
            resp.sendRedirect(req.getContextPath() + "/catalogo");
        } catch (IllegalArgumentException ex) {
            req.setAttribute("error", "Verifica los datos y escribe un precio válido mayor que cero.");
            doGet(req, resp);
        } catch (SQLException ex) {
            throw new ServletException("No se pudo guardar el artículo.", ex);
        }
    }
}
