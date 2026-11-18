package com.reusa.servlet;

import com.reusa.conexion.Conexion;
import com.reusa.modelo.Articulo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/producto")
public class DetalleServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id;
        try { id = Integer.parseInt(req.getParameter("id")); }
        catch (Exception ex) { resp.sendRedirect(req.getContextPath() + "/catalogo"); return; }

        String sql = """
            SELECT a.*, u.nombre AS vendedor FROM articulos a
            JOIN usuarios u ON u.id=a.vendedor_id WHERE a.id=?
            """;
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) { resp.sendRedirect(req.getContextPath() + "/catalogo"); return; }
                req.setAttribute("articulo", new Articulo(
                    rs.getInt("id"), rs.getString("nombre"), rs.getString("categoria"),
                    rs.getString("descripcion"), rs.getBigDecimal("precio"), rs.getString("estado"),
                    rs.getInt("vendedor_id"), rs.getString("vendedor"), rs.getBoolean("vendido"),
                    rs.getTimestamp("fecha_publicacion")));
            }
            try (PreparedStatement ps2 = cn.prepareStatement("""
                    SELECT c.comentario, c.fecha_comentario, u.nombre
                    FROM comentarios c JOIN usuarios u ON u.id=c.usuario_id
                    WHERE c.articulo_id=? ORDER BY c.fecha_comentario DESC
                    """)) {
                ps2.setInt(1, id);
                req.setAttribute("comentarios", ps2.executeQuery());
                req.getRequestDispatcher("/WEB-INF/vistas/producto.jsp").forward(req, resp);
            }
        } catch (SQLException ex) {
            throw new ServletException("No se pudo cargar el producto.", ex);
        }
    }
}
