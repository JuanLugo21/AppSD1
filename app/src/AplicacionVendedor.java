import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AplicacionVendedor extends JFrame {
    private List<Orden> ordenes;
    private JTable tablaOrdenes;
    private DefaultTableModel modeloTabla;

    public AplicacionVendedor() {
        setTitle("Aplicación del Vendedor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inicializarComponentes();
        iniciarActualizacionPeriodica();
    }

    private void inicializarComponentes() {
        String[] columnas = {"Producto", "Estado", "Enviar", "Entregado", "Cancelar"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaOrdenes = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaOrdenes);
        add(scrollPane, BorderLayout.CENTER);

        actualizarTabla();
    }

    private void actualizarTabla() {
        ordenes = GestorArchivos.cargarOrdenes("ordenes.dat");
        modeloTabla.setRowCount(0);
        for (int i = 0; i < ordenes.size(); i++) {
            Orden orden = ordenes.get(i);
            JButton btnEnviar = new JButton("Enviar");
            JButton btnEntregado = new JButton("Entregado");
            JButton btnCancelar = new JButton("Cancelar");

            final int index = i;
            btnEnviar.addActionListener(e -> enviarOrden(index, btnEnviar, btnEntregado, btnCancelar));
            btnEntregado.addActionListener(e -> entregarOrden(index, btnEnviar, btnEntregado, btnCancelar));
            btnCancelar.addActionListener(e -> cancelarOrden(index, btnEnviar, btnEntregado, btnCancelar));

            btnEntregado.setEnabled(false);

            modeloTabla.addRow(new Object[]{
                    orden.getProducto().getNombre(),
                    orden.getEstado(),
                    btnEnviar,
                    btnEntregado,
                    btnCancelar
            });
        }
    }

    private void enviarOrden(int index, JButton btnEnviar, JButton btnEntregado, JButton btnCancelar) {
        ordenes.get(index).setEstado("Enviado");
        btnEnviar.setEnabled(false);
        btnEntregado.setEnabled(true);
        guardarYActualizar();
    }

    private void entregarOrden(int index, JButton btnEnviar, JButton btnEntregado, JButton btnCancelar) {
        ordenes.get(index).setEstado("Entregado");
        btnEntregado.setEnabled(false);
        btnCancelar.setEnabled(false);
        guardarYActualizar();
    }

    private void cancelarOrden(int index, JButton btnEnviar, JButton btnEntregado, JButton btnCancelar) {
        ordenes.get(index).setEstado("Cancelado");
        btnEnviar.setEnabled(false);
        btnEntregado.setEnabled(false);
        btnCancelar.setEnabled(false);
        guardarYActualizar();
    }

    private void guardarYActualizar() {
        GestorArchivos.guardarOrdenes(ordenes, "ordenes.dat");
        actualizarTabla();
    }

    private void iniciarActualizacionPeriodica() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> actualizarTabla());
            }
        }, 0, 5000); // Actualiza cada 5 segundos
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AplicacionVendedor app = new AplicacionVendedor();
            app.setVisible(true);
        });
    }
}