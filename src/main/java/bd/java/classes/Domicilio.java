package bd.java.classes;

public class Domicilio
{
    private Integer id_domicilio;
    private String calle;
    private Integer numero;
    private String colonia;
    private String codigo_postal;
    private Integer idf_ciudad;

    public Domicilio()
    {
        // Vacio
    }

    // Getters
    public Integer getId_Domicilio()
    {
        return id_domicilio;
    }

    public String getCalle()
    {
        return calle;
    }

    public Integer getNumero()
    {
        return numero;
    }

    public String getColonia()
    {
        return colonia;
    }

    public String getCodigo_postal()
    {
        return codigo_postal;
    }

    public Integer getIdf_Ciudad()
    {
        return idf_ciudad;
    }

    // Setters
    public void setId_Domicilio(int id_domicilio)
    {
        this.id_domicilio = id_domicilio;
    }

    public void setCalle(String calle)
    {
        this.calle = calle;
    }

    public void setNumero(int numero)
    {
        this.numero = numero;
    }

    public void setColonia(String colonia)
    {
        this.colonia = colonia;
    }

    public void setCodigo_postal(String codigo_postal)
    {
        this.codigo_postal = codigo_postal;
    }

    public void setIdf_Ciudad(int idf_ciudad)
    {
        this.idf_ciudad = idf_ciudad;
    }
}
