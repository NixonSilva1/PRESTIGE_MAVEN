package gui;

import domain.Reservation;
import services.ReservationService;
import data.ReservationRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PaymentFrame extends JDialog {
    private Reservation reservation;
    private DefaultTableModel tableModel;
    private int tableRow;
    
    private JTextField cardNumberField;
    private JTextField cardHolderField;
    private JTextField expiryField;
    private JTextField cvvField;
    private JComboBox<String> paymentMethodCombo;
    private JPanel cardPanel;
    
    public PaymentFrame(JFrame parent, Reservation reservation, DefaultTableModel tableModel, int row) {
        super(parent, "Payment - Prestige Events", true);
        this.reservation = reservation;
        this.tableModel = tableModel;
        this.tableRow = row;
        
        setSize(600, 750);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Panel superior - Resumen de la reservación
        JPanel summaryPanel = createSummaryPanel();
        
        // Panel central - Métodos de pago
        JPanel paymentPanel = createPaymentPanel();
        
        // Panel inferior - Botones
        JPanel buttonPanel = createButtonPanel();
        
        mainPanel.add(summaryPanel, BorderLayout.NORTH);
        mainPanel.add(paymentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        
        JLabel titleLabel = new JLabel("Payment Summary");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(41, 128, 185));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel venueLabel = new JLabel("Venue: " + reservation.getVenue().getName());
        venueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        venueLabel.setForeground(new Color(52, 73, 94));
        venueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel dateLabel = new JLabel("Date: " + reservation.getDate().toString());
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        dateLabel.setForeground(new Color(52, 73, 94));
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel clientLabel = new JLabel("Client: " + reservation.getUser().getName());
        clientLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        clientLabel.setForeground(new Color(52, 73, 94));
        clientLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Separator
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        
        JLabel totalLabel = new JLabel("Total Amount: $" + String.format("%.2f", reservation.getTotalPrice()));
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        totalLabel.setForeground(new Color(46, 204, 113));
        totalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(venueLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(dateLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(clientLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(separator);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(totalLabel);
        
        return panel;
    }
    
    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Payment Method");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Selector de método de pago
        String[] paymentMethods = {"Credit/Debit Card", "PayPal", "Bank Transfer"};
        paymentMethodCombo = new JComboBox<>(paymentMethods);
        paymentMethodCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        paymentMethodCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        paymentMethodCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        paymentMethodCombo.addActionListener(e -> updatePaymentMethod());
        
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(paymentMethodCombo);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Panel de tarjeta (por defecto visible)
        cardPanel = createCardPanel();
        panel.add(cardPanel);
        
        return panel;
    }
    
    private JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Card Number
        JLabel cardNumberLabel = new JLabel("Card Number");
        cardNumberLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cardNumberLabel.setForeground(new Color(52, 73, 94));
        cardNumberLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        cardNumberField = new JTextField();
        cardNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cardNumberField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        cardNumberField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        // Card Holder Name
        JLabel cardHolderLabel = new JLabel("Card Holder Name");
        cardHolderLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cardHolderLabel.setForeground(new Color(52, 73, 94));
        cardHolderLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        cardHolderField = new JTextField();
        cardHolderField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cardHolderField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        cardHolderField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        // Expiry Date y CVV en la misma línea
        JPanel expiryPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        expiryPanel.setBackground(Color.WHITE);
        expiryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        
        // Expiry Date
        JPanel expiryColumn = new JPanel();
        expiryColumn.setLayout(new BoxLayout(expiryColumn, BoxLayout.Y_AXIS));
        expiryColumn.setBackground(Color.WHITE);
        
        JLabel expiryLabel = new JLabel("Expiry Date (MM/YY)");
        expiryLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        expiryLabel.setForeground(new Color(52, 73, 94));
        
        expiryField = new JTextField();
        expiryField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        expiryField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        expiryField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        expiryColumn.add(expiryLabel);
        expiryColumn.add(Box.createRigidArea(new Dimension(0, 5)));
        expiryColumn.add(expiryField);
        
        // CVV
        JPanel cvvColumn = new JPanel();
        cvvColumn.setLayout(new BoxLayout(cvvColumn, BoxLayout.Y_AXIS));
        cvvColumn.setBackground(Color.WHITE);
        
        JLabel cvvLabel = new JLabel("CVV");
        cvvLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cvvLabel.setForeground(new Color(52, 73, 94));
        
        cvvField = new JTextField();
        cvvField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cvvField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        cvvField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        cvvColumn.add(cvvLabel);
        cvvColumn.add(Box.createRigidArea(new Dimension(0, 5)));
        cvvColumn.add(cvvField);
        
        expiryPanel.add(expiryColumn);
        expiryPanel.add(cvvColumn);
        
        panel.add(cardNumberLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(cardNumberField);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(cardHolderLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(cardHolderField);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(expiryPanel);
        
        // Información de seguridad
        JLabel securityLabel = new JLabel("🔒 Your payment information is encrypted and secure");
        securityLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        securityLabel.setForeground(new Color(127, 140, 141));
        securityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(securityLabel);
        
        return panel;
    }
    
    private JPanel createPayPalPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(40, 30, 40, 30)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel iconLabel = new JLabel("PayPal");
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        iconLabel.setForeground(new Color(0, 48, 135));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel instructionLabel = new JLabel("You will be redirected to PayPal to complete your payment");
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        instructionLabel.setForeground(new Color(127, 140, 141));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel securityLabel = new JLabel("🔒 Secure payment via PayPal");
        securityLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        securityLabel.setForeground(new Color(127, 140, 141));
        securityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(instructionLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(securityLabel);
        
        return panel;
    }
    
    private JPanel createBankTransferPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("Bank Transfer Details");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String bankDetails = """
            <html>
            <b>Bank Name:</b> Prestige Bank International<br>
            <b>Account Name:</b> Prestige Events LLC<br>
            <b>Account Number:</b> 1234-5678-9012-3456<br>
            <b>Routing Number:</b> 987654321<br>
            <b>SWIFT Code:</b> PRSTUS33<br><br>
            <i>Please include your reservation details in the transfer notes.</i>
            </html>
            """;
        
        JLabel detailsLabel = new JLabel(bankDetails);
        detailsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        detailsLabel.setForeground(new Color(52, 73, 94));
        detailsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(detailsLabel);
        
        return panel;
    }
    
    private void updatePaymentMethod() {
        // Remover el panel actual
        Container parent = cardPanel.getParent();
        parent.remove(cardPanel);
        
        // Crear el nuevo panel según la selección
        int selectedIndex = paymentMethodCombo.getSelectedIndex();
        cardPanel = switch (selectedIndex) {
            case 0 -> createCardPanel();      // Credit/Debit Card
            case 1 -> createPayPalPanel();    // PayPal
            case 2 -> createBankTransferPanel(); // Bank Transfer
            default -> createCardPanel();
        };
        
        parent.add(cardPanel);
        parent.revalidate();
        parent.repaint();
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setBackground(new Color(245, 247, 250));
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cancelButton.setPreferredSize(new Dimension(150, 50));
        cancelButton.setBackground(new Color(149, 165, 166));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> dispose());
        
        JButton payButton = new JButton("Process Payment");
        payButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        payButton.setPreferredSize(new Dimension(200, 50));
        payButton.setBackground(new Color(46, 204, 113));
        payButton.setForeground(Color.WHITE);
        payButton.setFocusPainted(false);
        payButton.setBorderPainted(false);
        payButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        payButton.addActionListener(e -> processPayment());
        
        panel.add(cancelButton);
        panel.add(payButton);
        
        return panel;
    }
    
    private void processPayment() {
        int selectedMethod = paymentMethodCombo.getSelectedIndex();
        
        // Validar según método de pago
        if (selectedMethod == 0) { // Credit/Debit Card
            if (cardNumberField.getText().trim().isEmpty() ||
                cardHolderField.getText().trim().isEmpty() ||
                expiryField.getText().trim().isEmpty() ||
                cvvField.getText().trim().isEmpty()) {
                
                JOptionPane.showMessageDialog(this,
                    "Please complete all card information fields",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Validar formato de número de tarjeta (16 dígitos)
            String cardNumber = cardNumberField.getText().replaceAll("\\s+", "");
            if (!cardNumber.matches("\\d{16}")) {
                JOptionPane.showMessageDialog(this,
                    "Card number must be 16 digits",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Validar CVV (3 o 4 dígitos)
            if (!cvvField.getText().matches("\\d{3,4}")) {
                JOptionPane.showMessageDialog(this,
                    "CVV must be 3 or 4 digits",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        
        // Simular procesamiento de pago
        showProcessingDialog();
    }
    
    private void showProcessingDialog() {
        JDialog processingDialog = new JDialog(this, "Processing Payment", true);
        processingDialog.setSize(400, 200);
        processingDialog.setLocationRelativeTo(this);
        processingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        JLabel loadingLabel = new JLabel("Processing your payment...");
        loadingLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loadingLabel.setForeground(new Color(52, 73, 94));
        loadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setMaximumSize(new Dimension(300, 30));
        
        panel.add(loadingLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(progressBar);
        
        processingDialog.add(panel);
        
        // Simular procesamiento en un hilo separado
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(2000); // Simular procesamiento de 2 segundos
                return null;
            }
            
            @Override
            protected void done() {
                processingDialog.dispose();
                completePayment();
            }
        };
        
        worker.execute();
        processingDialog.setVisible(true);
    }
    
    private void completePayment() {
        try {
            // Actualizar estado de la reservación
            ReservationRepository repo = new ReservationRepository();
            ReservationService service = new ReservationService(repo);
            service.updatePaymentStatus(reservation, "PAID");
            
            // Actualizar la tabla
            tableModel.setValueAt("PAID", tableRow, 5);
            tableModel.setValueAt("✓ Paid", tableRow, 6);
            
            // Mostrar mensaje de éxito
            showSuccessDialog();
            
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error processing payment: " + e.getMessage(),
                "Payment Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showSuccessDialog() {
        JDialog successDialog = new JDialog(this, "Payment Successful!", true);
        successDialog.setSize(450, 300);
        successDialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel iconLabel = new JLabel("✓");
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 72));
        iconLabel.setForeground(new Color(46, 204, 113));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("Payment Successful!");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel messageLabel = new JLabel("<html><center>" +
            "Your payment of <b>$" + String.format("%.2f", reservation.getTotalPrice()) + "</b><br>" +
            "has been processed successfully.<br><br>" +
            "Your reservation is now confirmed!" +
            "</center></html>");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setForeground(new Color(127, 140, 141));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        okButton.setPreferredSize(new Dimension(150, 45));
        okButton.setMaximumSize(new Dimension(150, 45));
        okButton.setBackground(new Color(46, 204, 113));
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.addActionListener(e -> successDialog.dispose());
        
        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(messageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        panel.add(okButton);
        
        successDialog.add(panel);
        successDialog.setVisible(true);
    }
}