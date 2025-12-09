package gui;

import data.*;
import domain.*;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import services.*;

public class ViewReservationsFrame extends JFrame {
    
    public ViewReservationsFrame() {
        setTitle("Prestige Events - View Reservations");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Título
        JLabel titleLabel = new JLabel("All Reservations");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(41, 128, 185));
        
        // Tabla de reservaciones con modelo no editable
        String[] columnNames = {"Client Name", "Email", "Event Date", "Venue", "Total Price"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(tableModel);
        
        // Configurar fuente y tamaño de filas
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(35);
        table.setSelectionBackground(new Color(52, 152, 219));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(189, 195, 199));
        
        // Configurar HEADER con colores visibles - FORZADO
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setReorderingAllowed(false);

        // IMPORTANTE: Usar un renderer personalizado para forzar los colores
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString());
                label.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label.setBackground(new Color(52, 73, 94));  // Azul oscuro
                label.setForeground(Color.WHITE);             // Texto blanco
                label.setOpaque(true);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 1, 
                    new Color(189, 195, 199)));
                return label;
            }
        });
        
        // Centrar contenido de las celdas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Ajustar ancho de columnas
        table.getColumnModel().getColumn(0).setPreferredWidth(150); // Name
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // Email
        table.getColumnModel().getColumn(2).setPreferredWidth(120); // Date
        table.getColumnModel().getColumn(3).setPreferredWidth(180); // Venue
        table.getColumnModel().getColumn(4).setPreferredWidth(120); // Price
        
        // Cargar datos
        loadReservations(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(new Color(245, 247, 250));
        
        // Botón de refrescar
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        refreshButton.setPreferredSize(new Dimension(130, 45));
        refreshButton.setBackground(new Color(52, 152, 219));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.addActionListener(e -> {
            tableModel.setRowCount(0);
            loadReservations(tableModel);
        });
        
        // Botón de limpiar datos
        JButton clearButton = new JButton("Clear All Data");
        clearButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        clearButton.setPreferredSize(new Dimension(150, 45));
        clearButton.setBackground(new Color(231, 76, 60));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setBorderPainted(false);
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.addActionListener(e -> clearAllData(tableModel));
        
        // Botón de cerrar
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        closeButton.setPreferredSize(new Dimension(130, 45));
        closeButton.setBackground(new Color(149, 165, 166));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> dispose());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(closeButton);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void loadReservations(DefaultTableModel tableModel) {
        try {
            ReservationRepository repo = new ReservationRepository();
            ReservationService service = new ReservationService(repo);
            List<Reservation> reservations = service.getAllReservations();
            
            if (reservations.isEmpty()) {
                tableModel.addRow(new Object[]{"No reservations found", "", "", "", ""});
            } else {
                for (Reservation reservation : reservations) {
                    tableModel.addRow(new Object[]{
                        reservation.getUser().getName(),
                        reservation.getUser().getEmail(),
                        reservation.getDate().toString(),
                        reservation.getVenue().getName(),
                        "$" + String.format("%.2f", reservation.getTotalPrice())
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading reservations: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearAllData(DefaultTableModel tableModel) {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete ALL reservations?\nThis action cannot be undone.",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                java.io.File file = new java.io.File("reservations.dat");
                if (file.exists()) {
                    file.delete();
                }
                tableModel.setRowCount(0);
                tableModel.addRow(new Object[]{"No reservations found", "", "", "", ""});
                JOptionPane.showMessageDialog(this,
                    "All reservations have been deleted successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Error deleting file: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}