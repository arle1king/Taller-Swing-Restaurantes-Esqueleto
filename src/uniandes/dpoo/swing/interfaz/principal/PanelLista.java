package uniandes.dpoo.swing.interfaz.principal;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import uniandes.dpoo.swing.mundo.Restaurante;

@SuppressWarnings("serial")
public class PanelLista extends JPanel implements ListSelectionListener
{
    /**
     * El widget donde se muestra la lista de restaurantes
     */
    private JList<String> listaDeRestaurantes;

    /**
     * El modelo de datos que se muestra en la lista de restaurantes
     */
    private DefaultListModel<String> dataModel;

    /**
     * La ventana principal que contiene a este panel
     */
    private VentanaPrincipal ventanaPrincipal;

    public PanelLista(VentanaPrincipal ventanaPrincipal)
    {
        this.ventanaPrincipal = ventanaPrincipal;
        setBorder(new TitledBorder("Restaurantes"));
        setLayout(new BorderLayout());

        // Crea la lista con un modelo de datos en el que puede haber sólo nombres de restaurantes
        dataModel = new DefaultListModel<>();
        listaDeRestaurantes = new JList<>(dataModel);
        listaDeRestaurantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaDeRestaurantes.addListSelectionListener(this);

        // Crear un panel con barras de desplazamiento para la lista
        JScrollPane scroll = new JScrollPane(listaDeRestaurantes);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scroll);
    }

    /**
     * Actualiza la lista de restaurantes que se muestran en la lista.
     * 
     * Para esto, lo que se modifica es el model (y no el JList)
     * @param nombres
     */
    public void actualizarRestaurantes(List<String> nombres)
    {
        dataModel.clear();
        for (String nombre : nombres)
        {
            dataModel.addElement(nombre);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        // Revisa cuál es el nombre del restaurante seleccionado actualmente
        String nombreSeleccionado = listaDeRestaurantes.getSelectedValue();

        // Busca el restaurante correspondiente en la lista completa
        Restaurante seleccionado = ventanaPrincipal.getRestaurantes(true).stream()
            .filter(r -> r.getNombre().equals(nombreSeleccionado))
            .findFirst()
            .orElse(null);

        // Le envía la ventana principal el restaurante seleccionado para que se actualice el resto de la interfaz
        this.ventanaPrincipal.cambiarRestauranteSeleccionado(seleccionado);
    }

    /**
     * Cambia el restaurante seleccionado en la lista
     * @param nombre
     */
    public void seleccionarRestaurante(String nombre)
    {
        listaDeRestaurantes.setSelectedValue(nombre, true);
    }
}
