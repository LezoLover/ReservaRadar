package bd.java.classes;

import java.sql.Date;

public class Reserva
{
    private Integer id_reserva;
    private Date fecha_llegada;
    private Date fecha_salida;
    private Integer num_huespedes;
    private Integer idf_hotel;
    private Integer idf_huesped;

    public Reserva()
    {
        // Vacío
    }

    // Getters
    public Integer getId_reserva()
    {
        return id_reserva;
    }

    public Date getFecha_llegada()
    {
        return fecha_llegada;
    }

    public Date getFecha_salida()
    {
        return fecha_salida;
    }

    public Integer getNum_huespedes()
    {
        return num_huespedes;
    }

    public Integer getIdf_hotel()
    {
        return idf_hotel;
    }

    public Integer getIdf_huesped()
    {
        return idf_huesped;
    }

    // Setters
    public void setId_reserva(Integer id_reserva)
    {
        this.id_reserva = id_reserva;
    }

    public void setFecha_llegada(Date fecha_llegada)
    {
        this.fecha_llegada = fecha_llegada;
    }

    public void setFecha_salida(Date fecha_salida)
    {
        this.fecha_salida = fecha_salida;
    }

    public void setNum_huespedes(Integer num_huespedes)
    {
        this.num_huespedes = num_huespedes;
    }

    public void setIdf_hotel(Integer idf_hotel)
    {
        this.idf_hotel = idf_hotel;
    }

    public void setIdf_huesped(Integer idf_huesped)
    {
        this.idf_huesped = idf_huesped;
    }
}
