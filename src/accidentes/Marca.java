package accidentes;

import java.util.ArrayList;
import java.util.Objects;

public class Marca {
    private String nombre;
    private String pais;
    private ArrayList<Carro> carros;

    // Constructor vacío
    public Marca() {
        this.carros = new ArrayList<>();
    }

    // Constructor con atributos básicos
    public Marca(String nombre, String pais) {
        this.nombre = nombre;
        this.pais = pais;
        this.carros = new ArrayList<>();
    }

    // Getters/Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public ArrayList<Carro> getCarros() { return carros; }

    // Métodos de ayuda
    public void agregarCarro(Carro carro) {
        if (carro == null) return;
        if (!carros.contains(carro)) {
            carros.add(carro);
        }
        if (carro.getMarca() != this) {
            carro.setMarca(this);
        }
    }

    public void removerCarro(Carro carro) {
        if (carro == null) return;
        carros.remove(carro);
        if (carro.getMarca() == this) {
            carro.setMarca(null);
        }
    }

    @Override
    public String toString() {
        return "Marca{nombre='" + nombre + "', pais='" + pais + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Marca)) return false;
        Marca marca = (Marca) o;
        return Objects.equals(nombre, marca.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}
