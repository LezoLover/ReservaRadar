package bd.java.classes;

public class HabRes
{
    private Integer id_hab_res;
    private Integer idf_habitacion;
    private Integer idf_reserva;

    public HabRes()
    {
        // Vacio
    }

    // Getters
    public Integer getId_hab_res()
    {
        return id_hab_res;
    }

    public Integer getIdf_habitacion()
    {
        return idf_habitacion;
    }

    public Integer getIdf_reserva()
    {
        return idf_reserva;
    }

    // Setters
    public void setId_hab_res(Integer id_hab_res)
    {
        this.id_hab_res = id_hab_res;
    }

    public void setIdf_habitacion(Integer idf_habitacion)
    {
        this.idf_habitacion = idf_habitacion;
    }

    public void setIdf_reserva(Integer idf_resserva)
    {
        this.idf_reserva = idf_resserva;
    }
}
