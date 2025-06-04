package main;

import interfaz.InterfazClientes;
import interfaz.InterfazGrafica;
// import vista.MenuPrincipal;

public class MainApp {
    public static void main(String[] args) {
    	new SetupDatos().DatosPrueba();
    	/*MenuPrincipal menu = new MenuPrincipal();
        menu.iniciar();*/
    	javax.swing.SwingUtilities.invokeLater(() -> {
            new InterfazGrafica().setVisible(true);
        });
        javax.swing.SwingUtilities.invokeLater(() -> {
        new InterfazClientes().setVisible(true);
    	});
    }
}
