CREATE TABLE IF NOT EXISTS usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(150) UNIQUE NOT NULL,
    clave VARCHAR(255) NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS articulos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    categoria VARCHAR(80) NOT NULL,
    descripcion TEXT,
    precio NUMERIC(10,2) NOT NULL CHECK (precio > 0),
    estado VARCHAR(50) NOT NULL DEFAULT 'Bueno',
    vendedor_id INTEGER NOT NULL REFERENCES usuarios(id),
    vendido BOOLEAN NOT NULL DEFAULT FALSE,
    fecha_publicacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS comentarios (
    id SERIAL PRIMARY KEY,
    articulo_id INTEGER NOT NULL REFERENCES articulos(id) ON DELETE CASCADE,
    usuario_id INTEGER NOT NULL REFERENCES usuarios(id),
    comentario TEXT NOT NULL CHECK (char_length(comentario) <= 2000),
    fecha_comentario TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
