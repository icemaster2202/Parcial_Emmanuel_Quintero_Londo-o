package accidentes;

import java.util.ArrayList;

public class Dueno {
    private long cedula;
    private String nombre;
    private String apellido;
    private String telefono;

    // Relaciones
    private ArrayList<Carro> carros;          // 1..n (muchos-a-muchos con Carro)
    private ArrayList<Incidente> incidentes;  // 1..n

    public Dueno() {
        this.carros = new ArrayList<>();
        this.incidentes = new ArrayList<>();
    }

    public Dueno(long cedula, String nombre, String apellido, String telefono) {
        this();
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    // Getters/Setters
    public long getCedula() { return cedula; }
    public void setCedula(long cedula) { this.cedula = cedula; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public ArrayList<Carro> getCarros() { return carros; }
    public ArrayList<Incidente> getIncidentes() { return incidentes; }

    // Asociación bidireccional con Carro
    public void vincularCarro(Carro carro) {
        if (carro == null) return;
        if (!this.carros.contains(carro)) {
            this.carros.add(carro);
        }
        if (!carro.getDuenos().contains(this)) {
            carro.vincularDueno(this);
        }
    }

    public void desvincularCarro(Carro carro) {
        if (carro == null) return;
        this.carros.remove(carro);
        carro.getDuenos().remove(this);
    }

    // Incidentes
    public void agregarIncidente(Incidente incidente) {
        if (incidente == null) return;
        if (!this.incidentes.contains(incidente)) {
            this.incidentes.add(incidente);
            incidente.setDueno(this);
        }
    }

    public String mostrarIncidentes() {
        StringBuilder sb = new StringBuilder();
        for (Incidente i : incidentes) {
            sb.append(i.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Dueno{cedula=" + cedula + ", nombre='" + nombre + " " + apellido + "'}";
    }
}
