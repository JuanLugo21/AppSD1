import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Producto implements Serializable {
    private String nombre;
    private double precio;

    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }
}

class Orden implements Serializable {
    private Producto producto;
    private String estado;

    public Orden(Producto producto) {
        this.producto = producto;
        this.estado = "Pendiente";
    }

    public Producto getProducto() {
        return producto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

class GestorArchivos {
    public static void guardarOrdenes(List<Orden> ordenes, String archivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(new ArrayList<>(ordenes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Orden> cargarOrdenes(String archivo) {
        List<Orden> ordenes = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            ordenes = (List<Orden>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ordenes;
    }
}
