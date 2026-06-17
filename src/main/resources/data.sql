-- ============================
-- LIMPIEZA (opcional)
-- ============================
DELETE
FROM pedido_producto;
DELETE
FROM pedido;
DELETE
FROM producto;
DELETE
FROM categoria;
DELETE
FROM terminal;

-- ============================
-- CATEGORIAS
-- ============================
INSERT INTO categoria (id, nombre)
VALUES (1, 'Bebidas'),
       (2, 'Comida'),
       (3, 'Postres');

-- ============================
-- PRODUCTOS
-- ============================
INSERT INTO producto (id, nombre, precio, stock, activo, categoria_id)
VALUES (1, 'Coca-Cola', 2.50, 95, true, 1),
       (2, 'Agua', 1.50, 200, true, 1),
       (3, 'Hamburguesa', 8.99, 47, true, 2),
       (4, 'Pizza', 10.99, 39, true, 2),
       (5, 'Tarta de queso', 4.50, 29, true, 3),
       (6, 'Cerveza', 3.00, 80, true, 1),
       (7, 'Ensalada', 6.50, 25, true, 2),
       (8, 'Brownie', 3.75, 20, false, 3);
-- producto inactivo

-- ============================
-- TERMINALES
-- ============================
INSERT INTO terminal (id, nombre)
VALUES (1, 'Caja 1'),
       (2, 'Caja 2');

-- ============================
-- PEDIDOS
-- ============================
INSERT INTO pedido (id, codigo, terminal_id, precio_total, hora_pedido, estado)
VALUES
    (1, 'PED-A1B2C', 1, 11.49, '2026-04-10 12:00:00', 'CREADO'),
    (2, 'PED-D4E5F', 2, 15.49, '2026-04-10 13:00:00', 'EN_PREPARACION'),
    (3, 'PED-G7H8I', 1, 3.00, '2026-04-10 14:00:00', 'ENTREGADO');

-- ============================
-- PEDIDO_PRODUCTO
-- ============================
-- Pedido 1 → Coca-Cola + Hamburguesa
INSERT INTO pedido_producto (id, pedido_id, producto_id, cantidad, precio_unidad)
VALUES (1, 1, 1, 1, 2.50),
       (2, 1, 3, 1, 8.99);

-- Pedido 2 → Pizza + Tarta
INSERT INTO pedido_producto (id, pedido_id, producto_id, cantidad, precio_unidad)
VALUES (3, 2, 4, 1, 10.99),
       (4, 2, 5, 1, 4.50);

-- Pedido 3 → Agua x2
INSERT INTO pedido_producto (id, pedido_id, producto_id, cantidad, precio_unidad)
VALUES (5, 3, 2, 2, 1.50);