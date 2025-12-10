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
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Título
        String titleText = SessionManager.getInstance().isAdmin() ? 
            "All Reservations (Admin View)" : "My Reservations";
        JLabel titleLabel = new JLabel(titleText);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(41, 128, 185));
        
        // Configurar columnas según tipo de usuario
        String[] columnNames = SessionManager.getInstance().isAdmin() ?
            new String[]{"Client Name", "Email", "Event Date", "Venue", "Total Price", "Status"} :
            new String[]{"Client Name", "Email", "Event Date", "Venue", "Total Price", "Status", "Action"};
        
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo la columna de acción es "editable" (para el botón)
                return !SessionManager.getInstance().isAdmin() && column == 6;
            }
        };
        
        JTable table = new JTable(tableModel);
        
        // Configurar fuente y tamaño de filas
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(40);
        table.setSelectionBackground(new Color(52, 152, 219));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(189, 195, 199));
        
        // Configurar HEADER con colores visibles
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setReorderingAllowed(false);

        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString());
                label.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label.setBackground(new Color(52, 73, 94));
                label.setForeground(Color.WHITE);
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
        table.getColumnModel().getColumn(5).setPreferredWidth(100); // Status
        
        if (!SessionManager.getInstance().isAdmin()) {
            table.getColumnModel().getColumn(6).setPreferredWidth(120); // Action
            
            // Agregar botón de pago en la columna de acción
            table.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
            table.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), this, tableModel));
        }
        
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
        
        // Solo admin puede limpiar datos
        if (SessionManager.getInstance().isAdmin()) {
            JButton clearButton = new JButton("Clear All Data");
            clearButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
            clearButton.setPreferredSize(new Dimension(150, 45));
            clearButton.setBackground(new Color(231, 76, 60));
            clearButton.setForeground(Color.WHITE);
            clearButton.setFocusPainted(false);
            clearButton.setBorderPainted(false);
            clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            clearButton.addActionListener(e -> clearAllData(tableModel));
            buttonPanel.add(clearButton);
        }
        
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
            
            List<Reservation> reservations;
            
            // Si es admin, mostrar todas; si es cliente, solo las suyas
            if (SessionManager.getInstance().isAdmin()) {
                reservations = service.getAllReservations();
            } else {
                String userEmail = SessionManager.getInstance().getCurrentUser().getEmail();
                reservations = service.getReservationsByUser(userEmail);
            }
            
            if (reservations.isEmpty()) {
                Object[] emptyRow = SessionManager.getInstance().isAdmin() ?
                    new Object[]{"No reservations found", "", "", "", "", ""} :
                    new Object[]{"No reservations found", "", "", "", "", "", ""};
                tableModel.addRow(emptyRow);
            } else {
                for (Reservation reservation : reservations) {
                    String status = reservation.isPaid() ? "PAID" : "PENDING";
                    
                    if (SessionManager.getInstance().isAdmin()) {
                        tableModel.addRow(new Object[]{
                            reservation.getUser().getName(),
                            reservation.getUser().getEmail(),
                            reservation.getDate().toString(),
                            reservation.getVenue().getName(),
                            "$" + String.format("%.2f", reservation.getTotalPrice()),
                            status
                        });
                    } else {
                        tableModel.addRow(new Object[]{
                            reservation.getUser().getName(),
                            reservation.getUser().getEmail(),
                            reservation.getDate().toString(),
                            reservation.getVenue().getName(),
                            "$" + String.format("%.2f", reservation.getTotalPrice()),
                            status,
                            "Pay Now" // Botón
                        });
                    }
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
                Object[] emptyRow = new Object[]{"No reservations found", "", "", "", "", ""};
                tableModel.addRow(emptyRow);
                JOptionPane.showMessageDialog(this,
                    "All reservations have been deleted successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (SecurityException | java.io.IOError ex) {  // ✅ Multicatch
                JOptionPane.showMessageDialog(this,
                    "Error deleting file: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Renderer para el botón
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            String status = (String) table.getValueAt(row, 5);
            
            if ("PAID".equals(status)) {
                setText("✓ Paid");
                setBackground(new Color(46, 204, 113));
                setEnabled(false);
            } else {
                setText("Pay Now");
                setBackground(new Color(52, 152, 219));
                setEnabled(true);
            }
            
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
            setFocusPainted(false);
            setBorderPainted(false);
            return this;
        }
    }
    
    // Editor para el botón
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private final JFrame parentFrame;
        private final DefaultTableModel tableModel;
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox, JFrame parent, DefaultTableModel model) {
            super(checkBox);
            this.parentFrame = parent;
            this.tableModel = model;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            
            currentRow = row;
            String status = (String) table.getValueAt(row, 5);
            
            if ("PAID".equals(status)) {
                label = "✓ Paid";
                button.setBackground(new Color(46, 204, 113));
                button.setEnabled(false);
            } else {
                label = "Pay Now";
                button.setBackground(new Color(52, 152, 219));
                button.setEnabled(true);
            }
            
            button.setText(label);
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Segoe UI", Font.BOLD, 12));
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                try {
                    // Obtener datos de la reservación
                    String email = (String) tableModel.getValueAt(currentRow, 1);
                    String dateStr = (String) tableModel.getValueAt(currentRow, 2);
                    String venue = (String) tableModel.getValueAt(currentRow, 3);
                    
                    // Buscar la reservación
                    ReservationRepository repo = new ReservationRepository();
                    ReservationService service = new ReservationService(repo);
                    List<Reservation> reservations = service.getReservationsByUser(email);
                    
                    Reservation selectedReservation = null;
                    for (Reservation r : reservations) {
                        if (r.getDate().toString().equals(dateStr) && 
                            r.getVenue().getName().equals(venue)) {
                            selectedReservation = r;
                            break;
                        }
                    }
                    
                    if (selectedReservation != null && !selectedReservation.isPaid()) {
                        // Abrir ventana de pago
                        new PaymentFrame(parentFrame, selectedReservation, tableModel, currentRow).setVisible(true);
                    }
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(button,
                        "Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}