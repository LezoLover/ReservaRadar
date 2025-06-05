package bd.java.classes;

public class TipoHabitacion
{
    private Integer id_tipo_habitacion;
    private String nombre;
    private Integer capacidad;
    private Integer precio_base;

    public TipoHabitacion()
    {
        // Vacio
    }

    // Getters
    public Integer getId_tipo_habitacion()
    {
        return id_tipo_habitacion;
    }

    public String getNombre()
    {
        return nombre;
    }

    public Integer getCapacidad()
    {
        return capacidad;
    }

    public Integer getPrecio_base()
    {
        return precio_base;
    }

    // Setters
    public void setId_tipo_habitacion(Integer id_tipo_habitacion)
    {
        this.id_tipo_habitacion = id_tipo_habitacion;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public void setCapacidad(Integer capacidad)
    {
        this.capacidad = capacidad;
    }

    public void setPrecio_base(Integer precio_base)
    {
        this.precio_base = precio_base;
    }
}
