package bd.java.classes;

public class Hotel
{
    public static Integer id_hotel_static = -1;
    private Integer id_hotel;
    private String nombre;
    private String telefono;
    private Integer idf_domicilio;

    public Hotel()
    {
        // Vacío
    }

    // Getters
    public Integer getId_hotel()
    {
        return id_hotel;
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getTelefono()
    {
        return telefono;
    }

    public Integer getIdf_domicilio()
    {
        return idf_domicilio;
    }

    // Setters
    public void setId_hotel(Integer id_hotel)
    {
        this.id_hotel = id_hotel;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono)
    {
        this.telefono = telefono;
    }

    public void setIdf_domicilio(Integer idf_domicilio)
    {
        this.idf_domicilio = idf_domicilio;
    }
}
