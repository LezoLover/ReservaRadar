package bd.java.classes;

public class Huesped
{
    public static Integer id_huesped_static = -1;
    private Integer id_huesped;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private Integer idf_domicilio;

    public Huesped()
    {
        // Vacio
    }

    // Getters
    public Integer getId_huesped()
    {
        return id_huesped;
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getApellido()
    {
        return apellido;
    }

    public String getTelefono()
    {
        return telefono;
    }

    public String getCorreo()
    {
        return correo;
    }

    public Integer getIdf_domicilio()
    {
        return idf_domicilio;
    }

    // Setters
    public void setId_huesped(Integer id_huesped)
    {
        this.id_huesped = id_huesped;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public void setApellido(String apellido)
    {
        this.apellido = apellido;
    }

    public void setTelefono(String telefono)
    {
        this.telefono = telefono;
    }

    public void setCorreo(String correo)
    {
        this.correo = correo;
    }

    public void setIdf_domicilio(Integer idf_domicilio)
    {
        this.idf_domicilio = idf_domicilio;
    }
}
