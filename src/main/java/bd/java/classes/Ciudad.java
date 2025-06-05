package bd.java.classes;

public class Ciudad
{
    private Integer id_ciudad;
    private String nombre;

    public Ciudad()
    {
        // Vacio
    }

    // Getters
    public Integer getId_ciudad()
    {
        return id_ciudad;
    }

    public String getNombre()
    {
        return nombre;
    }

    // Setters
    public void setId_ciudad(int id_ciudad)
    {
        this.id_ciudad = id_ciudad;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
}
