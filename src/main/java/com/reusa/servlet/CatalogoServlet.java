package com.reusa.servlet;

import com.reusa.conexion.Conexion;
import com.reusa.modelo.Articulo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/catalogo")
public class CatalogoServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String buscar = req.getParameter("buscar");
        String sql = """
            SELECT a.*, u.nombre AS vendedor
            FROM articulos a JOIN usuarios u ON u.id = a.vendedor_id
            WHERE (? = '' OR a.nombre ILIKE ? OR a.categoria ILIKE ?)
            ORDER BY a.fecha_publicacion DESC
            """;
        List<Articulo> articulos = new ArrayList<>();
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            String termino = buscar == null ? "" : buscar.trim();
            ps.setString(1, termino);
            ps.setString(2, "%" + termino + "%");
            ps.setString(3, "%" + termino + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    articulos.add(new Articulo(
                        rs.getInt("id"), rs.getString("nombre"), rs.getString("categoria"),
                        rs.getString("descripcion"), rs.getBigDecimal("precio"),
                        rs.getString("estado"), rs.getInt("vendedor_id"),
                        rs.getString("vendedor"), rs.getBoolean("vendido"),
                        rs.getTimestamp("fecha_publicacion")));
                }
            }
            req.setAttribute("articulos", articulos);
            req.setAttribute("buscar", buscar == null ? "" : buscar);
            req.getRequestDispatcher("/WEB-INF/vistas/catalogo.jsp").forward(req, resp);
        } catch (SQLException ex) {
            throw new ServletException("Error al consultar el catálogo. Revisa PostgreSQL.", ex);
        }
    }
}
