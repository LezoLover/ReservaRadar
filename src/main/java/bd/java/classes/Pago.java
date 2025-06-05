package bd.java.classes;

import java.sql.Date;

public class Pago
{
    private Integer id_pago;
    private Double cantidad;
    private String metodo_pago;
    private Date fecha;
    private Integer idf_reserva;

    public Pago()
    {
        // Vacío
    }

    // Getters
    public Integer getId_pago()
    {
        return id_pago;
    }

    public Double getCantidad()
    {
        return cantidad;
    }

    public String getMetodo_pago()
    {
        return metodo_pago;
    }

    public Date getFecha()
    {
        return fecha;
    }

    public Integer getIdf_reserva()
    {
        return idf_reserva;
    }

    // Setters
    public void setId_pago(Integer id_pago)
    {
        this.id_pago = id_pago;
    }

    public void setCantidad(Double cantidad)
    {
        this.cantidad = cantidad;
    }

    public void setMetodo_pago(String metodo_pago)
    {
        this.metodo_pago = metodo_pago;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    public void setIdf_reserva(Integer idf_reserva)
    {
        this.idf_reserva = idf_reserva;
    }
}
