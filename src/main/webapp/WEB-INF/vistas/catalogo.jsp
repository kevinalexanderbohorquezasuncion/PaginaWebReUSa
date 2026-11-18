<%@ include file="plantilla.jspf" %>
<%@ page import="java.util.List,com.reusa.modelo.Articulo" %>
<!DOCTYPE html><html lang="es"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
<title>ReUsa | Catálogo</title><link rel="stylesheet" href="<%=request.getContextPath()%>/css/estilos.css"></head><body>
<header class="topbar"><a class="brand" href="<%=request.getContextPath()%>/">ReUsa<span>.</span></a>
<nav><a href="<%=request.getContextPath()%>/catalogo">Explorar</a>
<% if(usuarioActual != null) { %><a href="<%=request.getContextPath()%>/publicar">Publicar producto</a><span>Hola, <%= esc(usuarioActual.getNombre()) %></span><a href="<%=request.getContextPath()%>/logout">Salir</a><% } else { %><a href="<%=request.getContextPath()%>/login">Iniciar sesión</a><a class="nav-button" href="<%=request.getContextPath()%>/registro">Crear cuenta</a><% } %></nav></header>
<main class="container"><section class="hero"><p class="eyebrow">DALE UNA SEGUNDA VIDA</p><h1>Encuentra algo que<br><em>merece otra oportunidad.</em></h1><p>Compra, vende y reutiliza productos en tu comunidad.</p>
<form class="search" action="<%=request.getContextPath()%>/catalogo"><input name="buscar" placeholder="Buscar productos o categorías..." value="<%= esc((String)request.getAttribute("buscar")) %>"><button>Buscar</button></form></section>
<div class="section-head"><div><p class="eyebrow">MERCADO CIRCULAR</p><h2>Productos recientes</h2></div><span><%= ((List<?>)request.getAttribute("articulos")).size() %> publicaciones</span></div>
<div class="products"><% for(Articulo a : (List<Articulo>)request.getAttribute("articulos")) { %>
<article class="product-card"><div class="product-placeholder">♻</div><div class="product-info"><span class="category"><%=esc(a.getCategoria())%></span><h3><%=esc(a.getNombre())%></h3><p class="description"><%=esc(a.getDescripcion())%></p><div class="price-row"><strong>$<%=a.getPrecio()%></strong><span><%=a.isVendido()?"Vendido":"Disponible"%></span></div><p class="seller">Publicado por <%=esc(a.getVendedor())%></p><a class="detail-link" href="<%=request.getContextPath()%>/producto?id=<%=a.getId()%>">Ver producto y comentarios →</a></div></article>
<% } %></div>
<% if(((List<?>)request.getAttribute("articulos")).isEmpty()) { %><div class="empty">Todavía no hay productos publicados. ¡Sé el primero en publicar!</div><% } %>
</main><footer>ReUsa · Un mercado para dar nuevas oportunidades a los objetos.</footer></body></html>
<%!
private String esc(String s) {
 if(s == null) return "";
 return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;").replace("'","&#x27;");
}
%>
