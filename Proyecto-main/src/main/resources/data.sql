-- Insertar roles en la tabla 'rol'
INSERT INTO rol (nombre) VALUES ('ROLE_ADMINISTRADOR');
INSERT INTO rol (nombre) VALUES ('ROLE_VENDEDOR');
INSERT INTO rol (nombre) VALUES ('ROLE_CLIENTE');
INSERT INTO rol (nombre) VALUES ('ROLE_PROVEEDOR');

-- Insertar un usuario administrador en la tabla 'usuarios'
INSERT INTO usuarios (email, password, nombre, apellido)
VALUES ('admin@ferreteria.com', '$2a$10$XURP2XyvD.mfMnoGZ2ARrO8iD5D39eG/ZjM2bOQJ5zP.bM0d2ZTXO', 'Administrador', 'Apellido');

-- Asignar el rol ROLE_ADMINISTRADOR al usuario administrador en la tabla 'usuarios_roles'
INSERT INTO usuarios_roles (usuario_id, rol_id)
VALUES ((SELECT id FROM usuarios WHERE email = 'admin@ferreteria.com'),
        (SELECT id FROM rol WHERE nombre = 'ROLE_ADMINISTRADOR'));