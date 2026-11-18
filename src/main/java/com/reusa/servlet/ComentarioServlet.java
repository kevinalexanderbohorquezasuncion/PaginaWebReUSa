package com.reusa.servlet;

import com.reusa.conexion.Conexion;
import com.reusa.modelo.Usuario;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/comentar")
public class ComentarioServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Usuario u = usuario(req);
        if (u == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        req.setCharacterEncoding("UTF-8");
        int articuloId;
        try { articuloId = Integer.parseInt(req.getParameter("articuloId")); }
        catch (Exception ex) { resp.sendRedirect(req.getContextPath() + "/catalogo"); return; }
        String comentario = req.getParameter("comentario");
        if (comentario != null && !comentario.isBlank() && comentario.length() <= 2000) {
            try (Connection cn = Conexion.conectar();
                 PreparedStatement ps = cn.prepareStatement(
                    "INSERT INTO comentarios(articulo_id,usuario_id,comentario) " +
                    "SELECT ?, ?, ? WHERE EXISTS (SELECT 1 FROM articulos WHERE id=?)")) {
                ps.setInt(1, articuloId);
                ps.setInt(2, u.getId());
                ps.setString(3, comentario.trim());
                ps.setInt(4, articuloId);
                ps.executeUpdate();
            } catch (SQLException ignored) { }
        }
        resp.sendRedirect(req.getContextPath() + "/producto?id=" + articuloId);
    }
}
