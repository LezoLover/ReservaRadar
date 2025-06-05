package bd.java.classes;

public class Habitacion
{
    private Integer id_habitacion;
    private Integer numero;
    private Integer idf_tipo_habitacion;
    private Integer idf_hotel;

    public Habitacion()
    {
        // Vacio
    }

    // Getters
    public Integer getId_habitacion()
    {
        return id_habitacion;
    }

    public Integer getNumero()
    {
        return numero;
    }

    public Integer getIdf_tipo_habitacion()
    {
        return idf_tipo_habitacion;
    }

    public Integer getIdf_hotel()
    {
        return idf_hotel;
    }

    // Setters
    public void setId_habitacion(Integer id_habitacion)
    {
        this.id_habitacion = id_habitacion;
    }

    public void setNumero(Integer numero)
    {
        this.numero = numero;
    }

    public void setIdf_tipo_habitacion(Integer idf_tipo_habitacion)
    {
        this.idf_tipo_habitacion = idf_tipo_habitacion;
    }

    public void setIdf_hotel(Integer idf_hotel)
    {
        this.idf_hotel = idf_hotel;
    }
}
