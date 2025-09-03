import accidentes.*;
import java.util.*;

public class Main {

    private static final List<Marca> marcas = new ArrayList<>();
    private static final List<Dueno> duenos = new ArrayList<>();
    private static final List<Carro> carros = new ArrayList<>();
    private static final List<Incidente> incidentes = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Insertar dueno");
            System.out.println("2. Insertar marca");
            System.out.println("3. Insertar carro");
            System.out.println("4. Insertar incidente");
            System.out.println("5. Marca mas vendida");
            System.out.println("6. Marca con mas incidentes");
            System.out.println("7. Pais mas comun");
            System.out.println("8. Incidentes por dueno");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            opcion = leerEntero(sc);

            switch (opcion) {
                case 1 -> insertarDueno(sc);
                case 2 -> insertarMarca(sc);
                case 3 -> insertarCarro(sc);
                case 4 -> insertarIncidente(sc);
                case 5 -> consultaMarcaMasVendida();
                case 6 -> consultaMarcaConMasIncidentes();
                case 7 -> consultaPaisMasComun();
                case 8 -> consultaIncidentesPorDueno();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion invalida");
            }
        } while (opcion != 0);

        sc.close();
    }

    // Inserciones

    private static void insertarDueno(Scanner sc) {
        Dueno d = new Dueno();
        System.out.print("Cedula: ");
        d.setCedula(leerLong(sc));
        System.out.print("Nombre: ");
        d.setNombre(sc.nextLine().trim());
        System.out.print("Apellido: ");
        d.setApellido(sc.nextLine().trim());
        System.out.print("Telefono: ");
        d.setTelefono(sc.nextLine().trim());

        duenos.add(d);
        System.out.println("Dueno agregado: " + d.getNombre());
    }

    private static void insertarMarca(Scanner sc) {
        Marca m = new Marca();
        System.out.print("Nombre marca: ");
        m.setNombre(sc.nextLine().trim());
        System.out.print("Pais de origen: ");
        m.setPais(sc.nextLine().trim());

        marcas.add(m);
        System.out.println("Marca agregada: " + m.getNombre());
    }

    private static void insertarCarro(Scanner sc) {
        if (marcas.isEmpty() || duenos.isEmpty()) {
            System.out.println("Debes tener al menos 1 marca y 1 dueno.");
            return;
        }

        Carro c = new Carro();
        System.out.print("Placa: ");
        c.setPlaca(sc.nextLine().trim());
        System.out.print("Modelo: ");
        c.setModelo(sc.nextLine().trim());
        System.out.print("Anio de lanzamiento: ");
        c.setAnioLanzamiento(leerEntero(sc));

        // Seleccionar marca
        Marca marca = seleccionarMarca(sc);
        if (marca == null) return;
        c.setMarca(marca);
        if (!marca.getCarros().contains(c)) {
            marca.getCarros().add(c);
        }

        // Seleccionar varios dueños
        System.out.print("¿Cuántos dueños tiene este carro?: ");
        int cantidad = leerEntero(sc);

        for (int i = 0; i < cantidad; i++) {
            System.out.println("\nSelecciona el dueño #" + (i + 1) + ":");
            Dueno dueno = seleccionarDueno(sc);
            if (dueno == null) {
                System.out.println("Dueño inválido. Se omite.");
                continue;
            }
            if (!c.getDuenos().contains(dueno)) {
                c.getDuenos().add(dueno);   // añadimos al carro
            }
            if (!dueno.getCarros().contains(c)) {
                dueno.getCarros().add(c);   // añadimos al dueño
            }
        }

        carros.add(c);
        System.out.println("Carro agregado: " + c.getPlaca() + " con " + c.getDuenos().size() + " dueño(s).");
    }

    private static void insertarIncidente(Scanner sc) {
        if (duenos.isEmpty()) {
            System.out.println("No hay duenos.");
            return;
        }

        // Seleccionar dueño
        Dueno dueno = seleccionarDueno(sc);
        if (dueno == null) return;

        if (dueno.getCarros().isEmpty()) {
            System.out.println("Este dueno no tiene carros, no se puede registrar incidente.");
            return;
        }

        // Seleccionar carro del dueño
        System.out.println("Selecciona el carro para el incidente:");
        for (int i = 0; i < dueno.getCarros().size(); i++) {
            Carro c = dueno.getCarros().get(i);
            System.out.println((i + 1) + ". " + c.getPlaca() + " (" + c.getModelo() + ")");
        }
        int idxCarro = leerEntero(sc) - 1;
        if (idxCarro < 0 || idxCarro >= dueno.getCarros().size()) {
            System.out.println("Carro inválido.");
            return;
        }
        Carro carro = dueno.getCarros().get(idxCarro);

        // Crear incidente
        Incidente inc = new Incidente();
        System.out.print("Codigo del incidente: ");
        inc.setCodigo(leerLong(sc));
        System.out.print("Tipo de incidente: ");
        inc.setTipoIncidente(sc.nextLine().trim());
        inc.setFechaIncidente(new Date());
        System.out.print("Telefono: ");
        inc.setTelefono(sc.nextLine().trim());

        // Asociar
        inc.setDueno(dueno);
        dueno.getIncidentes().add(inc);



        incidentes.add(inc);

        System.out.println("Incidente agregado a " + dueno.getNombre() +" sobre el carro " + carro.getPlaca());
    }


    // Consultas

    private static void consultaMarcaMasVendida() {
        if (carros.isEmpty()) {
            System.out.println("No hay carros.");
            return;
        }
        Map<Marca, Integer> conteo = new HashMap<>();
        for (Carro c : carros) {
            Marca m = c.getMarca();
            conteo.put(m, conteo.getOrDefault(m, 0) + 1);
        }
        Marca top = conteo.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(null);

        System.out.println("Marca mas vendida: " + top.getNombre());
    }

    private static void consultaMarcaConMasIncidentes() {
        if (incidentes.isEmpty()) {
            System.out.println("No hay incidentes.");
            return;
        }
        Map<Marca, Integer> conteo = new HashMap<>();
        for (Incidente inc : incidentes) {
            Dueno d = inc.getDueno();
            for (Carro c : d.getCarros()) {
                Marca m = c.getMarca();
                conteo.put(m, conteo.getOrDefault(m, 0) + 1);
            }
        }
        Marca top = conteo.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(null);

        System.out.println("Marca con mas incidentes: " + top.getNombre());
    }

    private static void consultaPaisMasComun() {
        if (carros.isEmpty()) {
            System.out.println("No hay carros.");
            return;
        }
        Map<String, Integer> conteo = new HashMap<>();
        for (Carro c : carros) {
            String pais = c.getMarca().getPais();
            conteo.put(pais, conteo.getOrDefault(pais, 0) + 1);
        }
        Map.Entry<String, Integer> top = conteo.entrySet().stream()
                .max(Map.Entry.comparingByValue()).orElse(null);

        System.out.println("Pais mas comun: " + top.getKey() + " (" + top.getValue() + " carros)");
    }

    private static void consultaIncidentesPorDueno() {
        for (Dueno d : duenos) {
            System.out.println("\nDueno: " + d.getNombre());
            if (d.getIncidentes().isEmpty()) {
                System.out.println("  (Sin incidentes)");
            } else {
                for (Incidente i : d.getIncidentes()) {
                    System.out.println("  - " + i.getTipoIncidente());
                }
            }
        }
    }

    // Helpers

    private static Marca seleccionarMarca(Scanner sc) {
        for (int i = 0; i < marcas.size(); i++) {
            System.out.println((i + 1) + ". " + marcas.get(i).getNombre());
        }
        int idx = leerEntero(sc) - 1;
        return (idx >= 0 && idx < marcas.size()) ? marcas.get(idx) : null;
    }

    private static Dueno seleccionarDueno(Scanner sc) {
        for (int i = 0; i < duenos.size(); i++) {
            System.out.println((i + 1) + ". " + duenos.get(i).getNombre());
        }
        int idx = leerEntero(sc) - 1;
        return (idx >= 0 && idx < duenos.size()) ? duenos.get(idx) : null;
    }

    private static int leerEntero(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.print("Ingrese un numero valido: ");
            }
        }
    }

    private static long leerLong(Scanner sc) {
        while (true) {
            try {
                return Long.parseLong(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.print("Ingrese un numero valido: ");
            }
        }
    }
}
