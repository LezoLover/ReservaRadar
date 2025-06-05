--Rellenar Tablas
INSERT INTO tipo_habitacion (nombre, capacidad, precio_base) VALUES
('Doble', 2, 100.00),
('Triple', 3, 150.00),
('Suite', 2, 230.00),
('Presidencial', 2, 500.00);

INSERT INTO ciudad (nombre) VALUES 
('Aguascalientes'),
('Baja California'),
('Baja California Sur'),
('Campeche'),
('Coahuila'),
('Colima'),
('Chiapas'),
('Chihuahua'),
('Durango'),
('Distrito Federal'),
('Guanajuato'),
('Guerrero'),
('Hidalgo'),
('Jalisco'),
('México'),
('Michoacán'),
('Morelos'),
('Nayarit'),
('Nuevo León'),
('Oaxaca'),
('Puebla'),
('Querétaro'),
('Quintana Roo'),
('San Luis Potosí'),
('Sinaloa'),
('Sonora'),
('Tabasco'),
('Tamaulipas'),
('Tlaxcala'),
('Veracruz'),
('Yucatán'),
('Zacatecas');

------------------------- Rellenar hoteles ----------------------------------
-- (1) Aguascalientes
DO $$
DECLARE
   domicilio_id integer;
   hotel_id integer;
BEGIN
	-- Hotel 1
   INSERT INTO domicilio (calle, numero, colonia, codigo_postal, idf_ciudad) 
   VALUES ('Av. Independencia', 1703, 'Trojes de Kristal', '20118', 1)
   RETURNING id_domicilio INTO domicilio_id;

   INSERT INTO hotel (nombre, telefono, idf_domicilio) 
   VALUES ('Wyndham Garden', '449-993-3900', domicilio_id)
   RETURNING id_hotel INTO hotel_id;
   
   INSERT INTO habitacion (numero, idf_tipo_habitacion, idf_hotel) VALUES
	-- Dobles: 10
	(201, 1, hotel_id),
	(202, 1, hotel_id),
	(203, 1, hotel_id),
	(204, 1, hotel_id),
	(205, 1, hotel_id),
	(206, 1, hotel_id),
	(207, 1, hotel_id),
	(208, 1, hotel_id),
	(209, 1, hotel_id),
	(210, 1, hotel_id),
	-- Triples: 10
	(301, 2, hotel_id),
	(302, 2, hotel_id),
	(303, 2, hotel_id),
	(304, 2, hotel_id),
	(305, 2, hotel_id),
	(306, 2, hotel_id),
	(307, 2, hotel_id),
	(308, 2, hotel_id),
	(309, 2, hotel_id),
	(310, 2, hotel_id),
	-- Suite: 5
	(401, 3, hotel_id),
	(402, 3, hotel_id),
	(403, 3, hotel_id),
	(404, 3, hotel_id),
	(405, 3, hotel_id),
	-- Presidencial: 5
	(456, 4, hotel_id),
	(457, 4, hotel_id),
	(458, 4, hotel_id),
	(459, 4, hotel_id),
	(460, 4, hotel_id);
	
	-- Hotel 2
   INSERT INTO domicilio (calle, numero, colonia, codigo_postal, idf_ciudad) 
   VALUES ('Av. Adolfo López Mateos', 503, 'EL Llanito', '20240', 1)
   RETURNING id_domicilio INTO domicilio_id;

   INSERT INTO hotel (nombre, telefono, idf_domicilio) 
   VALUES ('Hotel Llanito', '449-497-5944', domicilio_id)
   RETURNING id_hotel INTO hotel_id;
   
   INSERT INTO habitacion (numero, idf_tipo_habitacion, idf_hotel) VALUES
	-- Dobles: 10
	(201, 1, hotel_id),
	(202, 1, hotel_id),
	(203, 1, hotel_id),
	(204, 1, hotel_id),
	(205, 1, hotel_id),
	(206, 1, hotel_id),
	(207, 1, hotel_id),
	(208, 1, hotel_id),
	(209, 1, hotel_id),
	(210, 1, hotel_id),
	-- Triples: 5
	(301, 2, hotel_id),
	(302, 2, hotel_id),
	(303, 2, hotel_id),
	(304, 2, hotel_id),
	(305, 2, hotel_id),
	-- Suite: 5
	(401, 3, hotel_id),
	(402, 3, hotel_id),
	(403, 3, hotel_id),
	(404, 3, hotel_id),
	(405, 3, hotel_id),
	-- Presidencial: 5
	(451, 4, hotel_id),
	(452, 4, hotel_id),
	(453, 4, hotel_id),
	(454, 4, hotel_id),
	(455, 4, hotel_id);
	
	-- Hotel 3
   INSERT INTO domicilio (calle, numero, colonia, codigo_postal, idf_ciudad) 
   VALUES ('Blvd. José María Chávez', 2100, 'Cd Industrial', '20290', 1)
   RETURNING id_domicilio INTO domicilio_id;

   INSERT INTO hotel (nombre, telefono, idf_domicilio) 
   VALUES ('Hotel Misión', '449-499-8546', domicilio_id)
   RETURNING id_hotel INTO hotel_id;
   
   INSERT INTO habitacion (numero, idf_tipo_habitacion, idf_hotel) VALUES
	-- Dobles: 5
	(201, 1, hotel_id),
	(202, 1, hotel_id),
	(203, 1, hotel_id),
	(204, 1, hotel_id),
	(205, 1, hotel_id),
	-- Triples: 5
	(301, 2, hotel_id),
	(302, 2, hotel_id),
	(303, 2, hotel_id),
	(304, 2, hotel_id),
	(305, 2, hotel_id),
	-- Suite: 5
	(401, 3, hotel_id),
	(402, 3, hotel_id),
	(403, 3, hotel_id),
	(404, 3, hotel_id),
	(405, 3, hotel_id),
	-- Presidencial: 5
	(451, 4, hotel_id),
	(452, 4, hotel_id),
	(453, 4, hotel_id),
	(454, 4, hotel_id),
	(455, 4, hotel_id);
END $$;
