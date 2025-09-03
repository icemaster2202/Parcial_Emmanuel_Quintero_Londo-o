package accidentes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Carro {
    private String placa;
    private String modelo;
    private int anioLanzamiento;

    // Relaciones
    private Marca marca;                              // 1
    private ArrayList<Comentario> comentarios;        // 1..n (composición)
    private ArrayList<Dueno> duenos;                  // 1..n (muchos-a-muchos)

    // Constructor vacío
    public Carro() {
        this.comentarios = new ArrayList<>();
        this.duenos = new ArrayList<>();
    }

    // Constructor con atributos básicos
    public Carro(String placa, String modelo, int anioLanzamiento) {
        this.placa = placa;
        this.modelo = modelo;
        this.anioLanzamiento = anioLanzamiento;
        this.comentarios = new ArrayList<>();
        this.duenos = new ArrayList<>();
    }

    // Getters/Setters básicos
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAnioLanzamiento() { return anioLanzamiento; }
    public void setAnioLanzamiento(int anioLanzamiento) { this.anioLanzamiento = anioLanzamiento; }

    public Marca getMarca() { return marca; }
    public void setMarca(Marca marca) {
        // mantener consistencia bidireccional
        if (this.marca == marca) return;
        if (this.marca != null) {
            this.marca.removerCarro(this);
        }
        this.marca = marca;
        if (marca != null && !marca.getCarros().contains(this)) {
            marca.agregarCarro(this);
        }
    }

    public ArrayList<Comentario> getComentarios() { return comentarios; }
    public ArrayList<Dueno> getDuenos() { return duenos; }

    // Métodos de ayuda
    public void agregarComentario(String descripcion, Date fecha) {
        Comentario c = new Comentario();
        c.setDescripcion(descripcion);
        c.setFechaComentario(fecha);
        c.setCarro(this); // composición: comentario pertenece al carro
        this.comentarios.add(c);
    }

    public void agregarComentario(Comentario comentario) {
        if (comentario == null) return;
        comentario.setCarro(this);
        this.comentarios.add(comentario);
    }

    public void eliminarComentario(Comentario comentario) {
        if (comentario == null) return;
        this.comentarios.remove(comentario);
        comentario.setCarro(null);
    }

    public void vincularDueno(Dueno dueno) {
        if (dueno == null) return;
        if (!this.duenos.contains(dueno)) {
            this.duenos.add(dueno);
        }
        if (!dueno.getCarros().contains(this)) {
            dueno.vincularCarro(this);
        }
    }

    public void desvincularDueno(Dueno dueno) {
        if (dueno == null) return;
        this.duenos.remove(dueno);
        dueno.getCarros().remove(this);
    }

    // Utilidad para mostrar comentarios del carro
    public String mostrarComentariosCarro() {
        StringBuilder sb = new StringBuilder();
        for (Comentario c : comentarios) {
            sb.append("- ").append(c.getDescripcion()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Carro{placa='" + placa + "', modelo='" + modelo + "', anio=" + anioLanzamiento + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carro)) return false;
        Carro carro = (Carro) o;
        return Objects.equals(placa, carro.placa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placa);
    }
}
