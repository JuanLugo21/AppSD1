import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AplicacionComprador extends JFrame {
    private List<Producto> productos;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JButton btnComprar;

    public AplicacionComprador() {
        setTitle("Aplicación del Comprador");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inicializarProductos();
        inicializarComponentes();
    }

    private void inicializarProductos() {
        productos = new ArrayList<>();
        productos.add(new Producto("Producto 1", 100.0));
        productos.add(new Producto("Producto 2", 200.0));
        productos.add(new Producto("Producto 3", 300.0));
    }

    private void inicializarComponentes() {
        String[] columnas = {"Nombre", "Precio"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaProductos = new JTable(modeloTabla);
        actualizarTabla();

        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        add(scrollPane, BorderLayout.CENTER);

        btnComprar = new JButton("Comprar");
        btnComprar.addActionListener(e -> realizarCompra());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnComprar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Producto producto : productos) {
            modeloTabla.addRow(new Object[]{producto.getNombre(), producto.getPrecio()});
        }
    }

    private void realizarCompra() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada != -1) {
            Producto productoSeleccionado = productos.get(filaSeleccionada);
            Orden nuevaOrden = new Orden(productoSeleccionado);

            List<Orden> ordenes = GestorArchivos.cargarOrdenes("ordenes.dat");
            ordenes.add(nuevaOrden);
            GestorArchivos.guardarOrdenes(ordenes, "ordenes.dat");

            JOptionPane.showMessageDialog(this, "Compra realizada con éxito");
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AplicacionComprador app = new AplicacionComprador();
            app.setVisible(true);
        });
    }
}
