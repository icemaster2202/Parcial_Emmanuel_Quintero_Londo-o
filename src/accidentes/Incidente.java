package accidentes;

import java.util.Date;

public class Incidente {
    private long codigo;
    private String tipoIncidente;
    private Date fechaIncidente;
    private String telefono;

    // Relación: pertenece a un Dueño (0..n desde Dueño a 1 Incidente)
    private Dueno dueno;

    public Incidente() {}

    public Incidente(long codigo, String tipoIncidente, Date fechaIncidente, String telefono) {
        this.codigo = codigo;
        this.tipoIncidente = tipoIncidente;
        this.fechaIncidente = fechaIncidente;
        this.telefono = telefono;
    }

    // Getters/Setters
    public long getCodigo() { return codigo; }
    public void setCodigo(long codigo) { this.codigo = codigo; }

    public String getTipoIncidente() { return tipoIncidente; }
    public void setTipoIncidente(String tipoIncidente) { this.tipoIncidente = tipoIncidente; }

    public Date getFechaIncidente() { return fechaIncidente; }
    public void setFechaIncidente(Date fechaIncidente) { this.fechaIncidente = fechaIncidente; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Dueno getDueno() { return dueno; }
    public void setDueno(Dueno dueno) { this.dueno = dueno; }

    @Override
    public String toString() {
        return "Incidente{codigo=" + codigo + ", tipo='" + tipoIncidente + "', fecha=" + fechaIncidente + "}";
    }
}
