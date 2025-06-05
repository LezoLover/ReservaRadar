-- Crear Tablas
DROP TABLE IF EXISTS ciudad CASCADE;
DROP TABLE IF EXISTS domicilio CASCADE;
DROP TABLE IF EXISTS hotel CASCADE;
DROP TABLE IF EXISTS huesped CASCADE;
DROP TABLE IF EXISTS reserva CASCADE;
DROP TABLE IF EXISTS pago CASCADE;
DROP TABLE IF EXISTS tipo_habitacion CASCADE;
DROP TABLE IF EXISTS habitacion CASCADE;
DROP TABLE IF EXISTS hab_res CASCADE;

CREATE TABLE ciudad (
	id_ciudad serial NOT NULL,
	nombre varchar(30) NOT NULL,
	PRIMARY KEY (id_ciudad)
);

CREATE SEQUENCE domicilio_id_seq START WITH 101;
CREATE TABLE domicilio (
	id_domicilio integer DEFAULT nextval('domicilio_id_seq') NOT NULL,
	calle varchar(30) NOT NULL,
	numero integer NOT NULL,
	colonia varchar(30) NOT NULL,
	codigo_postal varchar(10) NOT NULL,
	idf_ciudad integer NOT NULL,
	PRIMARY KEY (id_domicilio),
	FOREIGN KEY (idf_ciudad) REFERENCES ciudad (id_ciudad)
);
ALTER SEQUENCE domicilio_id_seq OWNED BY domicilio.id_domicilio;

CREATE SEQUENCE hotel_id_seq START WITH 201;
CREATE TABLE hotel (
	id_hotel integer DEFAULT nextval('hotel_id_seq') NOT NULL,
	nombre varchar(30) NOT NULL,
	telefono varchar(12) NOT NULL,
	idf_domicilio integer NOT NULL,
	PRIMARY KEY (id_hotel),
	FOREIGN KEY (idf_domicilio) REFERENCES domicilio (id_domicilio)
);
ALTER SEQUENCE hotel_id_seq OWNED BY hotel.id_hotel;

CREATE SEQUENCE huesped_id_seq START WITH 101;
CREATE TABLE huesped (
	id_huesped integer DEFAULT nextval('huesped_id_seq') NOT NULL,
	nombre varchar(30) NOT NULL,
	apellido varchar(30) NOT NULL,
	telefono varchar(12) NOT NULL,
	correo varchar(80) NOT NULL,
	idf_domicilio integer NOT NULL,
	PRIMARY KEY (id_huesped),
	FOREIGN KEY (idf_domicilio) REFERENCES domicilio (id_domicilio)
);
ALTER SEQUENCE huesped_id_seq OWNED BY huesped.id_huesped;


CREATE SEQUENCE reserva_id_seq START WITH 201;
CREATE TABLE reserva (
	id_reserva integer DEFAULT nextval('reserva_id_seq') NOT NULL,
	fecha_llegada date NOT NULL,
	fecha_salida date NOT NULL,
	num_huespedes integer NOT NULL,
	idf_hotel integer NOT NULL,
	idf_huesped integer NOT NULL,
	PRIMARY KEY (id_reserva),
	FOREIGN KEY (idf_hotel) REFERENCES hotel (id_hotel),
	FOREIGN KEY (idf_huesped) REFERENCES huesped (id_huesped)
);
ALTER SEQUENCE reserva_id_seq OWNED BY reserva.id_reserva;

CREATE SEQUENCE pago_id_seq START WITH 101;
CREATE TABLE pago (
	id_pago integer DEFAULT nextval('pago_id_seq') NOT NULL,
	cantidad decimal NOT NULL,
	metodo_pago varchar(30) NOT NULL,
	fecha date NOT NULL,
	idf_reserva integer NOT NULL,
	PRIMARY KEY (id_pago),
	FOREIGN KEY (idf_reserva) REFERENCES reserva (id_reserva)
);
ALTER SEQUENCE pago_id_seq OWNED BY pago.id_pago;

CREATE TABLE tipo_habitacion (
	id_tipo_habitacion serial NOT NULL,
	nombre varchar(30) NOT NULL,
	capacidad integer NOT NULL,
	precio_base decimal NOT NULL,
	PRIMARY KEY (id_tipo_habitacion)
);

CREATE SEQUENCE habitacion_id_seq START WITH 1001;
CREATE TABLE habitacion (
	id_habitacion integer DEFAULT nextval('habitacion_id_seq') NOT NULL,
	numero integer NOT NULL,
	idf_tipo_habitacion integer NOT NULL,
	idf_hotel integer NOT NULL,
	PRIMARY KEY (id_habitacion),
	FOREIGN KEY (idf_tipo_habitacion) REFERENCES tipo_habitacion (id_tipo_habitacion),
	FOREIGN KEY (idf_hotel) REFERENCES hotel (id_hotel)
);
ALTER SEQUENCE habitacion_id_seq OWNED BY habitacion.id_habitacion;

CREATE TABLE hab_res (
	id_hab_res serial NOT NULL,
	idf_habitacion integer NOT NULL,
	idf_reserva integer NOT NULL,
	PRIMARY KEY (id_hab_res),
	FOREIGN KEY (idf_habitacion) REFERENCES habitacion (id_habitacion),
	FOREIGN KEY (idf_reserva) REFERENCES reserva (id_reserva)
);
