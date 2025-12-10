package gui;

import domain.Reservation;
import services.ReservationService;
import data.ReservationRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PaymentFrame extends JDialog {
    private final Reservation reservation;
    private final DefaultTableModel tableModel;
    private final int tableRow;
    
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
        
        setSize(600, 680);  // ✅ Reducido de 850 a 680
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));  // ✅ Reducido padding
        
        // Panel superior - Resumen de la reservación (más compacto)
        JPanel summaryPanel = createCompactSummaryPanel();
        
        // Panel central - Métodos de pago
        JPanel paymentPanel = createPaymentPanel();
        
        // Panel inferior - Botones
        JPanel buttonPanel = createButtonPanel();
        
        mainPanel.add(summaryPanel, BorderLayout.NORTH);
        mainPanel.add(paymentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createCompactSummaryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)  // ✅ Reducido padding
        ));
        
        // Título
        JLabel titleLabel = new JLabel("Payment Summary");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));  // ✅ Reducido de 22 a 18
        titleLabel.setForeground(new Color(41, 128, 185));
        
        // Detalles en una línea compacta
        JPanel detailsPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        detailsPanel.setBackground(Color.WHITE);
        
        JLabel venueLabel = new JLabel("Venue: " + reservation.getVenue().getName());
        venueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));  // ✅ Reducido
        venueLabel.setForeground(new Color(52, 73, 94));
        
        JLabel dateLabel = new JLabel("Date: " + reservation.getDate().toString());
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dateLabel.setForeground(new Color(52, 73, 94));
        
        JLabel clientLabel = new JLabel("Client: " + reservation.getUser().getName());
        clientLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        clientLabel.setForeground(new Color(52, 73, 94));
        
        detailsPanel.add(venueLabel);
        detailsPanel.add(dateLabel);
        detailsPanel.add(clientLabel);
        
        // Total
        JLabel totalLabel = new JLabel("Total: $" + String.format("%.2f", reservation.getTotalPrice()));
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));  // ✅ Reducido de 24 a 20
        totalLabel.setForeground(new Color(46, 204, 113));
        
        // Panel para organizar
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.add(detailsPanel, BorderLayout.CENTER);
        contentPanel.add(totalLabel, BorderLayout.SOUTH);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Payment Method");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));  // ✅ Reducido
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Selector de método de pago
        String[] paymentMethods = {"Credit/Debit Card", "PayPal", "Bank Transfer"};
        paymentMethodCombo = new JComboBox<>(paymentMethods);
        paymentMethodCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));  // ✅ Reducido
        paymentMethodCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));  // ✅ Reducido altura
        paymentMethodCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        paymentMethodCombo.addActionListener(e -> updatePaymentMethod());
        
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));  // ✅ Reducido espacio
        panel.add(paymentMethodCombo);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Panel de tarjeta (por defecto visible)
        cardPanel = createCompactCardPanel();
        panel.add(cardPanel);
        
        return panel;
    }
    
    private JPanel createCompactCardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)  // ✅ Reducido padding
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Card Number
        JLabel cardNumberLabel = new JLabel("Card Number");
        cardNumberLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));  // ✅ Reducido
        cardNumberLabel.setForeground(new Color(52, 73, 94));
        cardNumberLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        cardNumberField = new JTextField();
        cardNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cardNumberField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));  // ✅ Reducido
        cardNumberField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)  // ✅ Reducido padding
        ));
        
        // Card Holder Name
        JLabel cardHolderLabel = new JLabel("Card Holder Name");
        cardHolderLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cardHolderLabel.setForeground(new Color(52, 73, 94));
        cardHolderLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        cardHolderField = new JTextField();
        cardHolderField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cardHolderField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        cardHolderField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        
        // Expiry y CVV en la misma línea
        JPanel expiryPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        expiryPanel.setBackground(Color.WHITE);
        expiryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));  // ✅ Reducido
        
        // Expiry Date
        JPanel expiryColumn = new JPanel();
        expiryColumn.setLayout(new BoxLayout(expiryColumn, BoxLayout.Y_AXIS));
        expiryColumn.setBackground(Color.WHITE);
        
        JLabel expiryLabel = new JLabel("Expiry (MM/YY)");
        expiryLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        expiryLabel.setForeground(new Color(52, 73, 94));
        
        expiryField = new JTextField();
        expiryField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        expiryField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        expiryField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        
        expiryColumn.add(expiryLabel);
        expiryColumn.add(Box.createRigidArea(new Dimension(0, 3)));
        expiryColumn.add(expiryField);
        
        // CVV
        JPanel cvvColumn = new JPanel();
        cvvColumn.setLayout(new BoxLayout(cvvColumn, BoxLayout.Y_AXIS));
        cvvColumn.setBackground(Color.WHITE);
        
        JLabel cvvLabel = new JLabel("CVV");
        cvvLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cvvLabel.setForeground(new Color(52, 73, 94));
        
        cvvField = new JTextField();
        cvvField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cvvField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        cvvField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        
        cvvColumn.add(cvvLabel);
        cvvColumn.add(Box.createRigidArea(new Dimension(0, 3)));
        cvvColumn.add(cvvField);
        
        expiryPanel.add(expiryColumn);
        expiryPanel.add(cvvColumn);
        
        panel.add(cardNumberLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 3)));
        panel.add(cardNumberField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(cardHolderLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 3)));
        panel.add(cardHolderField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(expiryPanel);
        
        // Información de seguridad
        JLabel securityLabel = new JLabel("🔒 Your payment is encrypted and secure");
        securityLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        securityLabel.setForeground(new Color(127, 140, 141));
        securityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(securityLabel);
        
        return panel;
    }
    
    private JPanel createPayPalPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel iconLabel = new JLabel("PayPal");
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        iconLabel.setForeground(new Color(0, 48, 135));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel instructionLabel = new JLabel("You will be redirected to PayPal");
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        instructionLabel.setForeground(new Color(127, 140, 141));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel securityLabel = new JLabel("🔒 Secure payment via PayPal");
        securityLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        securityLabel.setForeground(new Color(127, 140, 141));
        securityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(instructionLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(securityLabel);
        
        return panel;
    }
    
    private JPanel createBankTransferPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("Bank Transfer Details");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String bankDetails = """
            <html>
            <b>Bank:</b> Prestige Bank International<br>
            <b>Account:</b> Prestige Events LLC<br>
            <b>Account #:</b> 1234-5678-9012-3456<br>
            <b>Routing:</b> 987654321<br>
            <b>SWIFT:</b> PRSTUS33
            </html>
            """;
        
        JLabel detailsLabel = new JLabel(bankDetails);
        detailsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        detailsLabel.setForeground(new Color(52, 73, 94));
        detailsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
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
            case 0 -> createCompactCardPanel();      // Credit/Debit Card
            case 1 -> createPayPalPanel();           // PayPal
            case 2 -> createBankTransferPanel();     // Bank Transfer
            default -> createCompactCardPanel();
        };
        
        parent.add(cardPanel);
        parent.revalidate();
        parent.repaint();
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setBackground(new Color(245, 247, 250));
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.setPreferredSize(new Dimension(130, 45));
        cancelButton.setBackground(new Color(149, 165, 166));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> dispose());
        
        JButton payButton = new JButton("Process Payment");
        payButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        payButton.setPreferredSize(new Dimension(170, 45));
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
        processingDialog.setSize(350, 150);
        processingDialog.setLocationRelativeTo(this);
        processingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel loadingLabel = new JLabel("Processing your payment...");
        loadingLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loadingLabel.setForeground(new Color(52, 73, 94));
        loadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setMaximumSize(new Dimension(280, 25));
        
        panel.add(loadingLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
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
        // Primero mostrar la factura
        showInvoiceDialog();
        
        // Luego el mensaje de éxito simple
        JDialog successDialog = new JDialog(this, "Payment Successful!", true);
        successDialog.setSize(400, 250);
        successDialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        JLabel iconLabel = new JLabel("✓");
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 60));
        iconLabel.setForeground(new Color(46, 204, 113));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("Payment Successful!");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel messageLabel = new JLabel("<html><center>" +
            "Your payment of <b>$" + String.format("%.2f", reservation.getTotalPrice()) + "</b><br>" +
            "has been processed successfully." +
            "</center></html>");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        messageLabel.setForeground(new Color(127, 140, 141));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        okButton.setPreferredSize(new Dimension(120, 40));
        okButton.setMaximumSize(new Dimension(120, 40));
        okButton.setBackground(new Color(46, 204, 113));
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.addActionListener(e -> successDialog.dispose());
        
        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(messageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(okButton);
        
        successDialog.add(panel);
        successDialog.setVisible(true);
    }

    private void showInvoiceDialog() {
        JDialog invoiceDialog = new JDialog(this, "Invoice - Prestige Events", true);
        invoiceDialog.setSize(650, 750);
        invoiceDialog.setLocationRelativeTo(this);
        
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Encabezado de la factura
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel companyLabel = new JLabel("PRESTIGE EVENTS LLC");
        companyLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        companyLabel.setForeground(Color.WHITE);
        companyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel invoiceLabel = new JLabel("INVOICE");
        invoiceLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        invoiceLabel.setForeground(Color.WHITE);
        invoiceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel invoiceNumber = new JLabel("Invoice #: INV-" + System.currentTimeMillis());
        invoiceNumber.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        invoiceNumber.setForeground(Color.WHITE);
        invoiceNumber.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(companyLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(invoiceLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        headerPanel.add(invoiceNumber);
        
        // Panel de contenido
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Información de la empresa
        JPanel companyInfoPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        companyInfoPanel.setBackground(Color.WHITE);
        companyInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        JLabel companyAddress = new JLabel("123 Event Plaza, New York, NY 10001");
        companyAddress.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel companyPhone = new JLabel("Phone: +1 (555) 123-4567");
        companyPhone.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel companyEmail = new JLabel("Email: info@prestigeevents.com");
        companyEmail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel companyWeb = new JLabel("Web: www.prestigeevents.com");
        companyWeb.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        companyInfoPanel.add(companyAddress);
        companyInfoPanel.add(companyPhone);
        companyInfoPanel.add(companyEmail);
        companyInfoPanel.add(companyWeb);
        
        // Información del cliente
        JPanel clientInfoPanel = new JPanel();
        clientInfoPanel.setLayout(new BoxLayout(clientInfoPanel, BoxLayout.Y_AXIS));
        clientInfoPanel.setBackground(new Color(236, 240, 241));
        clientInfoPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel billToLabel = new JLabel("BILL TO:");
        billToLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        billToLabel.setForeground(new Color(52, 73, 94));
        billToLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel clientName = new JLabel(reservation.getUser().getName());
        clientName.setFont(new Font("Segoe UI", Font.BOLD, 13));
        clientName.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel clientEmail = new JLabel(reservation.getUser().getEmail());
        clientEmail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        clientEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel eventDate = new JLabel("Event Date: " + reservation.getDate().toString());
        eventDate.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        eventDate.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        clientInfoPanel.add(billToLabel);
        clientInfoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        clientInfoPanel.add(clientName);
        clientInfoPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        clientInfoPanel.add(clientEmail);
        clientInfoPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        clientInfoPanel.add(eventDate);
        
        // Detalles de la reservación
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        
        JLabel detailsTitle = new JLabel("RESERVATION DETAILS:");
        detailsTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        detailsTitle.setForeground(new Color(52, 73, 94));
        detailsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Tabla de items
        String[] columnNames = {"Description", "Amount"};
        DefaultTableModel invoiceTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Agregar venue
        invoiceTableModel.addRow(new Object[]{
            "Venue: " + reservation.getVenue().getName(),
            "$" + String.format("%.2f", getVenuePrice())
        });
        
        // Agregar servicios
        for (var service : reservation.getServices()) {
            String serviceName = service.getClass().getSimpleName().replace("Service", "");
            if (service instanceof domain.CateringService cateringService) {
                serviceName = cateringService.getMenuDetails();
            }
            invoiceTableModel.addRow(new Object[]{
                "Service: " + serviceName,
                "$" + String.format("%.2f", service.calculatePrice())
            });
        }
        
        JTable itemsTable = new JTable(invoiceTableModel);
        itemsTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        itemsTable.setRowHeight(30);
        itemsTable.getColumnModel().getColumn(0).setPreferredWidth(400);
        itemsTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        
        JScrollPane tableScrollPane = new JScrollPane(itemsTable);
        tableScrollPane.setPreferredSize(new Dimension(550, 150));
        
        detailsPanel.add(detailsTitle);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        detailsPanel.add(tableScrollPane);
        
        // Panel de totales
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.Y_AXIS));
        totalPanel.setBackground(new Color(236, 240, 241));
        totalPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel subtotalLabel = new JLabel("Subtotal: $" + String.format("%.2f", reservation.getTotalPrice()));
        subtotalLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtotalLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        JLabel taxLabel = new JLabel("Tax (0%): $0.00");
        taxLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        taxLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        JLabel totalAmountLabel = new JLabel("TOTAL PAID: $" + String.format("%.2f", reservation.getTotalPrice()));
        totalAmountLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalAmountLabel.setForeground(new Color(46, 204, 113));
        totalAmountLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        totalPanel.add(subtotalLabel);
        totalPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        totalPanel.add(taxLabel);
        totalPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        totalPanel.add(totalAmountLabel);
        
        // Nota de agradecimiento
        JLabel thankYouLabel = new JLabel("<html><center>Thank you for choosing Prestige Events!<br>We look forward to making your event unforgettable.</center></html>");
        thankYouLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        thankYouLabel.setForeground(new Color(127, 140, 141));
        thankYouLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        contentPanel.add(companyInfoPanel);
        contentPanel.add(clientInfoPanel);
        contentPanel.add(detailsPanel);
        contentPanel.add(totalPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(thankYouLabel);
        
        // Botón de cerrar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton closeButton = new JButton("Close Invoice");
        closeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        closeButton.setPreferredSize(new Dimension(150, 45));
        closeButton.setBackground(new Color(41, 128, 185));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> invoiceDialog.dispose());
        
        buttonPanel.add(closeButton);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        invoiceDialog.add(mainPanel);
        invoiceDialog.setVisible(true);
    }

    private double getVenuePrice() {
        String venueName = reservation.getVenue().getName();
        return switch (venueName) {
            case "Hospice Club" -> 5000.0;
            case "house of mystery" -> 3500.0;
            case "Executive Hall" -> 2500.0;
            case "Ocean View Terrace" -> 4000.0;
            case "Intimate Lounge" -> 1800.0;
            default -> 0.0;
        };
    }
}