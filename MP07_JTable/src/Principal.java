import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.border.CompoundBorder;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Principal extends JFrame  {

	private JPanel contentPane;
	private JTable table;
	public DatosCiudades intercambioDatos;
	private DefaultTableModel dtm;
	private VentanaPreguntas vp ;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		
		intercambioDatos = new DatosCiudades();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		table = new JTable();
		table.setColumnSelectionAllowed(true);
		table.setFillsViewportHeight(true);
		table.setBorder(new CompoundBorder());
		//Para poder seleccionar
        table.setCellSelectionEnabled(true);
		// Column Names 
        String[] nombreColumnas = { "Nombre", "Comarca", "Num hab","Metros Cuadrados" };
        dtm = new DefaultTableModel(nombreColumnas,0);
        
        //ListSelectionModel contendr� las filas seleccionadas
        ListSelectionModel select= table.getSelectionModel();  
        //solo podremos seleccionar una fila
        select.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        //A�adimos un listener para actuar cuando se selecciona
        select.addListSelectionListener(new ListSelectionListener() {  
        	//Es llamado cuando cambia la selecci�n
          public void valueChanged(ListSelectionEvent e) {  
            String Data = null;  
            int[] row = table.getSelectedRows();  
            int[] columns = table.getSelectedColumns();  
            for (int i = 0; i < row.length; i++) {  
            	System.out.println("La fila es "+i+" y tiene "+columns.length);
              for (int j = 0; j < columns.length; j++) {  
                Data = (String) table.getValueAt(row[i], columns[j]);  
                System.out.println("Table element selected is: " + Data);  
              } }  
              
          }       
        });  
        
        Vector<String> v = new Vector<String>();
        v.add("bcn");
        v.add("catalu�a");
        v.add("1000");
        v.add("2000");
        dtm.addRow(v);
		table.setModel(dtm);
		
		 v = new Vector<String>();
        v.add("mad");
        v.add("Madrid");
        v.add("1000");
        v.add("2000");
        dtm.addRow(v);
		
		v = new Vector<String>();
        v.add("VAL");
        v.add("Comunidad valenciana");
        v.add("1000");
        v.add("2000");
        dtm.addRow(v);
		
				
		JScrollPane sp = new JScrollPane(table); 
		
		
		JButton btnNueva = new JButton("NUEVA");
		//btnNueva.addActionListener(this);
		btnNueva.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				vp.setVisible(true);
			}
			
		});
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] row = table.getSelectedRows(); 
				System.out.println("Numero de filas seleccionadas: "+row.length);
				for(int i=row.length-1;i>=0;i--) {
					dtm.removeRow(row[i]);
				}
				/*int sel=table.getSelectedRow();
				dtm.removeRow(sel);*/
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(sp, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(btnNueva)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnEliminar)
					.addGap(260))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(sp, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNueva)
						.addComponent(btnEliminar))
					.addContainerGap(55, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
		//A�ado un listener a VentanaPreguntas para hacer algo cuando se cierre esta ventana
		vp = new VentanaPreguntas(this, "Introduzca datos de la ciudad...");
		//Si el Jdialog lo cierro con dispose.
		vp.addWindowListener( new WindowAdapter() {
			    @Override
		        public void windowClosed(WindowEvent e) {
		            System.out.println("Cierre de ventana");
		            intercambioDatos=vp.devolverDatos();
		            System.out.println("Nombre="+intercambioDatos.nombre);
		            //A�adimos la ciudad a la tabla
		            anadirCiudad();
		        }
		});
		
		
		//esta opcion se activa con vp.setVisible(flase);
		/*vp.addWindowListener( new WindowAdapter() {
	        public void windowDeactivated(WindowEvent e) {
		    	if( !vp.isVisible()) {
		    		System.out.println("ventana desactivada e invisible");
		    		intercambioDatos=vp.devolverDatos();
		            System.out.println("Nombre="+intercambioDatos.nombre);
		    	}
		    }
	    });*/
		
	}

	
	private void anadirCiudad() {
		//datos est�n en intercambioDatos
		Vector<String> v = new Vector<String>();
		
        v.add(intercambioDatos.nombre);
        v.add(intercambioDatos.comarca);
        v.add(String.valueOf(intercambioDatos.habitantes));
        v.add( String.valueOf(intercambioDatos.metro2));
        dtm.addRow(v);
	}
}

