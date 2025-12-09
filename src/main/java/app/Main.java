package app;

import gui.WelcomeFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Establecer Look and Feel del sistema para que se vea nativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | 
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.err.println("Error al establecer Look and Feel: " + e.getMessage());
        }
        
        // Iniciar la interfaz gráfica en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            WelcomeFrame welcomeFrame = new WelcomeFrame();
            welcomeFrame.setVisible(true);
        });
    }
}