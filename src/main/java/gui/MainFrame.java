package gui;

import data.*;
import domain.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import services.*;

public class MainFrame extends JFrame {
    private Venue selectedVenue;
    private double selectedVenuePrice = 0; 
    private final List<AbstractService> selectedServices = new ArrayList<>();
    private JLabel totalLabel;
    private double totalPrice = 0;
    
    private JTextField nameField, emailField;
    private JSpinner dateSpinner;
    private final JTabbedPane tabbedPane;
    
    public MainFrame() {
        setTitle("Prestige Events - Reservation System");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));
        
        // Panel de pestañas
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setForeground(new Color(52, 73, 94));
        
        // Pestañas
        tabbedPane.addTab("  1. Client Information  ", createClientPanel());
        tabbedPane.addTab("  2. Select Venue  ", createVenuePanel());
        tabbedPane.addTab("  3. Additional Services  ", createServicesPanel());
        tabbedPane.addTab("  4. Confirm Booking  ", createConfirmationPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Botones de navegación
        mainPanel.add(createNavigationPanel(), BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createNavigationPanel() {
        JPanel navPanel = new JPanel(new BorderLayout());
        navPanel.setBackground(Color.WHITE);
        navPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));
        
        JButton previousButton = new JButton("← Previous");
        previousButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        previousButton.setPreferredSize(new Dimension(130, 40));
        previousButton.setBackground(new Color(149, 165, 166));
        previousButton.setForeground(Color.WHITE);
        previousButton.setFocusPainted(false);
        previousButton.setBorderPainted(false);
        previousButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton nextButton = new JButton("Next →");
        nextButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nextButton.setPreferredSize(new Dimension(130, 40));
        nextButton.setBackground(new Color(52, 152, 219));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setBorderPainted(false);
        nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton viewReservationsButton = new JButton("View Reservations");
        viewReservationsButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        viewReservationsButton.setPreferredSize(new Dimension(170, 40));
        viewReservationsButton.setBackground(new Color(155, 89, 182));
        viewReservationsButton.setForeground(Color.WHITE);
        viewReservationsButton.setFocusPainted(false);
        viewReservationsButton.setBorderPainted(false);
        viewReservationsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Acciones de los botones
        previousButton.addActionListener(e -> {
            int current = tabbedPane.getSelectedIndex();
            if (current > 0) {
                tabbedPane.setSelectedIndex(current - 1);
            }
        });
        
        nextButton.addActionListener(e -> {
            int current = tabbedPane.getSelectedIndex();
            if (current < tabbedPane.getTabCount() - 1) {
                String validationError = validateCurrentTab(current);
                    if (validationError != null) {
                        JOptionPane.showMessageDialog(this,
                            validationError,
                            "Validation Error",
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    tabbedPane.setSelectedIndex(current + 1);
            }
        });
        
        viewReservationsButton.addActionListener(e -> {
            new ViewReservationsFrame().setVisible(true);
        });
        
        // Hover effects
        addHoverEffect(previousButton, new Color(149, 165, 166), new Color(127, 140, 141));
        addHoverEffect(nextButton, new Color(52, 152, 219), new Color(41, 128, 185));
        addHoverEffect(viewReservationsButton, new Color(155, 89, 182), new Color(142, 68, 173));
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.add(previousButton);
        leftPanel.add(viewReservationsButton);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(nextButton);
        
        navPanel.add(leftPanel, BorderLayout.WEST);
        navPanel.add(rightPanel, BorderLayout.EAST);
        
        return navPanel;
    }
    
    private void addHoverEffect(JButton button, Color normal, Color hover) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hover);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normal);
            }
        });
    }
    
    private String validateCurrentTab(int tabIndex) {
        return switch (tabIndex) {
            case 0 -> { // Client Information
                // Ya no validamos nameField ni emailField porque vienen del usuario logueado
                // Solo verificamos que haya una fecha seleccionada
                if (dateSpinner.getValue() == null) {
                    yield "Please select an event date";
                }
                yield null; // Todo está bien
            }
            case 1 -> { // Venue Selection
                if (selectedVenue == null) {
                    yield "Please select a venue before proceeding";
                }
                yield null;
            }
            case 2 -> { // Services
                // Opcional: no requerimos servicios
                yield null;
            }
            default -> null;
        };
    }
    
    private JPanel createClientPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Título
        JLabel titleLabel = new JLabel("Client Information");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(41, 128, 185));
        
        // Panel de información del usuario
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        
        // Obtener usuario logueado
        User currentUser = SessionManager.getInstance().getCurrentUser();
        
        // Panel de bienvenida
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(new Color(236, 240, 241));
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        welcomePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getName() + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(52, 73, 94));
        
        JLabel accountLabel = new JLabel("Account: " + currentUser.getEmail());
        accountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        accountLabel.setForeground(new Color(127, 140, 141));
        
        JLabel typeLabel = new JLabel("User Type: " + (currentUser.isAdmin() ? "Administrator" : "Client"));
        typeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        typeLabel.setForeground(new Color(127, 140, 141));
        
        welcomePanel.add(welcomeLabel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 8)));
        welcomePanel.add(accountLabel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        welcomePanel.add(typeLabel);
        
        // Información del cliente (solo lectura)
        JPanel clientDataPanel = new JPanel(new GridBagLayout());
        clientDataPanel.setBackground(Color.WHITE);
        clientDataPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Full Name (solo lectura)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLabel.setForeground(new Color(52, 73, 94));
        clientDataPanel.add(nameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        nameField = new JTextField(currentUser.getName());
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameField.setPreferredSize(new Dimension(400, 40));
        nameField.setEditable(false);
        nameField.setBackground(new Color(236, 240, 241));
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        clientDataPanel.add(nameField, gbc);
        
        // Email (solo lectura)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        emailLabel.setForeground(new Color(52, 73, 94));
        clientDataPanel.add(emailLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        emailField = new JTextField(currentUser.getEmail());
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setPreferredSize(new Dimension(400, 40));
        emailField.setEditable(false);
        emailField.setBackground(new Color(236, 240, 241));
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        clientDataPanel.add(emailField, gbc);
        
        // Event Date (EDITABLE)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel dateLabel = new JLabel("Event Date:");
        dateLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        dateLabel.setForeground(new Color(52, 73, 94));
        clientDataPanel.add(dateLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        SpinnerDateModel dateModel = new SpinnerDateModel();
        dateSpinner = new JSpinner(dateModel);
        dateSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateSpinner.setPreferredSize(new Dimension(400, 40));
        
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "MMM dd, yyyy");
        dateSpinner.setEditor(dateEditor);
        dateEditor.getTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateEditor.getTextField().setBackground(Color.WHITE);
        dateEditor.getTextField().setEditable(false);
        
        clientDataPanel.add(dateSpinner, gbc);
        
        // Nota informativa
        JLabel noteLabel = new JLabel("<html><i>Your account information is pre-filled. Select your event date below.</i></html>");
        noteLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        noteLabel.setForeground(new Color(127, 140, 141));
        noteLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        infoPanel.add(welcomePanel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        infoPanel.add(clientDataPanel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        infoPanel.add(noteLabel);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(infoPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createVenuePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Título
        JLabel titleLabel = new JLabel("Select Your Event Venue");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(41, 128, 185));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Panel de lugares
        JPanel venuesPanel = new JPanel(new GridLayout(5, 1, 15, 15));
        venuesPanel.setBackground(new Color(245, 247, 250));
        
        addVenueOption(venuesPanel, "Hospice Club", 150, 5000, 
            "On the premises, you will find three event halls: Pesebreras, Kiosco, and Vivero, for 150 people, as well as a swimming pool, patio, park, Las Marías, and much more. All of them stand out for their colonial touches and cozy atmosphere. At Caminito de Piedra, you will also find the following spaces:");
        addVenueOption(venuesPanel, "house of mystery", 80, 3500,
            "Surrounded by nature, its countryside settings offer the perfect balance between city comfort and the serenity of the countryside. Enjoy a hall with a capacity for 140 people, ideal for your grand celebration, and an outdoor area for 100 people, perfect for ceremonies, cocktails, or receptions surrounded by natural beauty.");
        addVenueOption(venuesPanel, "Executive Hall", 50, 2500,
            "If what you want is a wedding worthy of the best and in a spectacular location, Casa Duque is and will be the place. The house is located 1 hour from Bogotá, just before reaching El Rosal. You will live the ceremony of your dreams! Say 'I do' in a magical outdoor setting and continue the rest of the evening in the same venue, no need to travel!");
        addVenueOption(venuesPanel, "Ocean View Terrace", 100, 4000,
            "An unbeatable space to live that dream wedding is Weddings Cancun Marina. Their Club House located on a dock in the Nichupte lagoon offers spectacular views to enjoy with their loved ones while they pronounce loud and clear that \"I do\" that they crave more and more every day.");
        addVenueOption(venuesPanel, "Intimate Lounge", 30, 1800,
            "El Sauce Restaurant Park is a spacious and comfortable countryside venue that offers recreational and event services with quality to the residents of Bogotá and its surroundings. Surrounded by extensive green areas for enjoyment and leisure, it is perfect for sharing pleasant moments with family. It offers halls for rent so that you can enjoy the celebration you deserve.");
        
        JScrollPane scrollPane = new JScrollPane(venuesPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void addVenueOption(JPanel panel, String name, int capacity, double price, String description) {
        JPanel venuePanel = new JPanel(new BorderLayout(15, 15));
        venuePanel.setBackground(Color.WHITE);
        venuePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // NUEVO: Panel de imagen a la izquierda
        JPanel imagePanel = createVenueImage(name);
        
        // Información
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        nameLabel.setForeground(new Color(52, 73, 94));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel capacityLabel = new JLabel("Capacity: " + capacity + " guests");
        capacityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        capacityLabel.setForeground(new Color(127, 140, 141));
        capacityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel priceLabel = new JLabel("Price: $" + String.format("%.2f", price));
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        priceLabel.setForeground(new Color(46, 204, 113));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel descLabel = new JLabel("<html>" + description + "</html>");
        descLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        descLabel.setForeground(new Color(127, 140, 141));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        infoPanel.add(capacityLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(priceLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(descLabel);
        
        // Botón
        JButton selectButton = new JButton("Select Venue");
        selectButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        selectButton.setPreferredSize(new Dimension(140, 40));
        selectButton.setBackground(new Color(52, 152, 219));
        selectButton.setForeground(Color.WHITE);
        selectButton.setFocusPainted(false);
        selectButton.setBorderPainted(false);
        selectButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        addHoverEffect(selectButton, new Color(52, 152, 219), new Color(41, 128, 185));
        
        selectButton.addActionListener(e -> {
            selectedVenue = new Venue(name, capacity);
            selectedVenuePrice = price;
            recalculateTotalPrice();
            
            JOptionPane.showMessageDialog(this, 
                "Venue Selected!\n" + name + " - $" + String.format("%.2f", price),
                "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        
        // NUEVO: Agregar imagen a la izquierda
        venuePanel.add(imagePanel, BorderLayout.WEST);
        venuePanel.add(infoPanel, BorderLayout.CENTER);
        venuePanel.add(selectButton, BorderLayout.EAST);
        
        panel.add(venuePanel);
    }

    private JPanel createVenueImage(String venueName) {
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(150, 120));
        imagePanel.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        
        // Mapeo de nombres a archivos de imagen
        String imageName = switch (venueName) {
            case "Hospice Club" -> "hospice_club.jpg";
            case "house of mystery" -> "house_of_mystery.jpg";
            case "Executive Hall" -> "executive_hall.jpg";
            case "Ocean View Terrace" -> "ocean_view.jpg";
            case "Intimate Lounge" -> "intimate_lounge.jpg";
            default -> null;
        };
        
        if (imageName != null) {
            try {
                // Intentar cargar desde el classpath primero
                java.net.URL imageUrl = getClass().getResource("/images/" + imageName);
                
                if (imageUrl != null) {
                    // Si se encuentra en el classpath
                    ImageIcon originalIcon = new ImageIcon(imageUrl);
                    Image scaledImage = originalIcon.getImage().getScaledInstance(
                        150, 120, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    
                    JLabel imageLabel = new JLabel(scaledIcon);
                    imagePanel.add(imageLabel, BorderLayout.CENTER);
                } else {
                    // Si no se encuentra, intentar desde el sistema de archivos
                    String filePath = "src/main/resources/images/" + imageName;
                    java.io.File imageFile = new java.io.File(filePath);
                    
                    if (imageFile.exists()) {
                        ImageIcon originalIcon = new ImageIcon(filePath);
                        Image scaledImage = originalIcon.getImage().getScaledInstance(
                            150, 120, Image.SCALE_SMOOTH);
                        ImageIcon scaledIcon = new ImageIcon(scaledImage);
                        
                        JLabel imageLabel = new JLabel(scaledIcon);
                        imagePanel.add(imageLabel, BorderLayout.CENTER);
                    } else {
                        // Si tampoco existe el archivo, mostrar placeholder
                        addPlaceholder(imagePanel, venueName);
                    }
                }
            } catch (Exception e) {
                addPlaceholder(imagePanel, venueName);
            }
        } else {
            addPlaceholder(imagePanel, venueName);
        }
        
        return imagePanel;
    }

    private void addPlaceholder(JPanel imagePanel, String venueName) {
        // Color de fondo según el venue
        Color backgroundColor;
        
        if (venueName == null) {
            backgroundColor = new Color(149, 165, 166);
        } else {
            backgroundColor = switch (venueName) {
                case "Hospice Club" -> new Color(231, 76, 60);      // Rojo
                case "house of mystery" -> new Color(155, 89, 182);  // Púrpura
                case "Executive Hall" -> new Color(52, 152, 219);    // Azul
                case "Ocean View Terrace" -> new Color(26, 188, 156); // Verde agua
                case "Intimate Lounge" -> new Color(241, 196, 15);   // Amarillo
                default -> new Color(149, 165, 166);                 // Gris
            };
        }
        
        imagePanel.setBackground(backgroundColor);
        
        // Texto simple del nombre del venue
        String displayText = (venueName != null) ? venueName.substring(0, 1).toUpperCase() : "?";
        
        JLabel textLabel = new JLabel(displayText);
        textLabel.setFont(new Font("Segoe UI", Font.BOLD, 64));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setForeground(Color.WHITE);
        
        imagePanel.add(textLabel, BorderLayout.CENTER);
    }
    
    private JPanel createServicesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel titleLabel = new JLabel("Additional Services");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(41, 128, 185));
        
        JPanel servicesPanel = new JPanel(new GridLayout(5, 1, 15, 15));
        servicesPanel.setBackground(new Color(245, 247, 250));
        
        addServiceOption(servicesPanel, "Premium Catering", 500, 
            "Customizable buffet with multiple menu options");
        addServiceOption(servicesPanel, "Floral Decoration", 300,
            "Elegant floral arrangements for the entire event");
        addServiceOption(servicesPanel, "Audio System", 200,
            "Professional sound equipment with DJ");
        addServiceOption(servicesPanel, "Photography", 400,
            "Professional 6-hour photo session");
        addServiceOption(servicesPanel, "Special Lighting", 250,
            "LED lights and special effects");
        
        JScrollPane scrollPane = new JScrollPane(servicesPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void addServiceOption(JPanel panel, String name, double price, String description) {
        JPanel servicePanel = new JPanel(new BorderLayout(15, 15));
        servicePanel.setBackground(Color.WHITE);
        servicePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nameLabel.setForeground(new Color(52, 73, 94));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel priceLabel = new JLabel("Starting at: $" + String.format("%.2f", price));
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        priceLabel.setForeground(new Color(46, 204, 113));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        descLabel.setForeground(new Color(127, 140, 141));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        infoPanel.add(priceLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        infoPanel.add(descLabel);
        
        // Si es catering, usar un botón especial
        if (name.equals("Premium Catering")) {
            JButton selectButton = new JButton("Select Menu");
            selectButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            selectButton.setPreferredSize(new Dimension(140, 40));
            selectButton.setBackground(new Color(52, 152, 219));
            selectButton.setForeground(Color.WHITE);
            selectButton.setFocusPainted(false);
            selectButton.setBorderPainted(false);
            selectButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            selectButton.addActionListener(e -> {
                CateringMenuFrame menuFrame = new CateringMenuFrame(this);
                menuFrame.setVisible(true);
                
                if (menuFrame.isConfirmed()) {
                    // Remover servicio de catering previo si existe
                    selectedServices.removeIf(s -> s.getName().equals("Catering"));
                    
                    // Agregar nuevo servicio con el precio y detalles actualizados
                    CateringService cateringService = new CateringService(
                        menuFrame.getTotalPrice(),
                        menuFrame.getSelectionSummary()
                    );
                    selectedServices.add(cateringService);
                    
                    // Recalcular precio total
                    recalculateTotalPrice();
                    
                    selectButton.setText("✓ Selected");
                    selectButton.setBackground(new Color(46, 204, 113));
                    
                    JOptionPane.showMessageDialog(this,
                        "Catering menu added!\n" + menuFrame.getSelectionSummary() +
                        "\nPrice: $" + String.format("%.2f", menuFrame.getTotalPrice()),
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            });
            
            servicePanel.add(infoPanel, BorderLayout.CENTER);
            servicePanel.add(selectButton, BorderLayout.EAST);
        } else {
            // Para otros servicios, usar checkbox normal
            JCheckBox checkBox = new JCheckBox("Add Service");
            checkBox.setFont(new Font("Segoe UI", Font.BOLD, 14));
            checkBox.setBackground(Color.WHITE);
            checkBox.setForeground(new Color(52, 73, 94));
            checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            checkBox.addActionListener(e -> {
                if (checkBox.isSelected()) {
                    AbstractService service = new CateringService(price);
                    selectedServices.add(service);
                } else {
                    selectedServices.removeIf(s -> s.calculatePrice() == price && !s.getName().equals("Catering"));
                }
                recalculateTotalPrice(); // CAMBIAR ESTA LÍNEA - usar recalculateTotalPrice() en lugar de updateTotal()
            });
                        
            servicePanel.add(infoPanel, BorderLayout.CENTER);
            servicePanel.add(checkBox, BorderLayout.EAST);
        }
        
        panel.add(servicePanel);
    }
    
    private JPanel createConfirmationPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel titleLabel = new JLabel("Booking Summary");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(41, 128, 185));
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));
        
        JLabel infoLabel = new JLabel("Review your information and confirm the booking");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoLabel.setForeground(new Color(127, 140, 141));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        totalLabel.setForeground(new Color(46, 204, 113));
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        centerPanel.add(infoLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(totalLabel);
        
        JButton confirmButton = new JButton("Confirm Booking");
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        confirmButton.setPreferredSize(new Dimension(250, 55));
        confirmButton.setBackground(new Color(231, 76, 60));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);
        confirmButton.setBorderPainted(false);
        confirmButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        addHoverEffect(confirmButton, new Color(231, 76, 60), new Color(192, 57, 43));
        
        confirmButton.addActionListener(e -> confirmReservation());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 247, 250));
        buttonPanel.add(confirmButton);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void updateTotal() {
        totalLabel.setText("Total: $" + String.format("%.2f", totalPrice));
    }
    
    private void recalculateTotalPrice() {
        // Inicializar con el precio del venue guardado
        totalPrice = selectedVenuePrice;
        
        // Agregar precio de todos los servicios
        for (AbstractService service : selectedServices) {
            totalPrice += service.calculatePrice();
        }
        
        updateTotal();
    }

    private void confirmReservation() {
        // Verificar que el usuario esté logueado
        if (!SessionManager.getInstance().isLoggedIn()) {
            JOptionPane.showMessageDialog(this,
                "You must be logged in to make a reservation",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (selectedVenue == null) {
            JOptionPane.showMessageDialog(this, "Please select a venue", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // YA NO ES NECESARIO VALIDAR nameField y emailField porque vienen del usuario logueado
        
        try {
            // Usar el usuario logueado directamente
            User user = SessionManager.getInstance().getCurrentUser();
            
            java.util.Date selectedDate = (java.util.Date) dateSpinner.getValue();
            LocalDate eventDate = LocalDate.ofInstant(selectedDate.toInstant(), 
                java.time.ZoneId.systemDefault());
            
            ReservationRepository repo = new ReservationRepository();
            ReservationService reservationService = new ReservationService(repo);
            
            // Validar si ya existe una reserva en ese lugar y fecha
            if (reservationService.isVenueAvailable(selectedVenue.getName(), eventDate)) {
                
                Reservation reservation = new Reservation(user, eventDate, selectedVenue);
                
                // Agregar todos los servicios
                for (AbstractService service : selectedServices) {
                    reservation.addService(service);
                }
                
                // Guardar la reservación
                repo.add(reservation);
                
                // Mensaje de éxito
                showSuccessDialog();
                
                dispose();
                new MainFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                    """
                    Sorry! This venue is already booked for the selected date.
                    Please choose another date or venue.""",
                    "Venue Not Available", JOptionPane.WARNING_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showSuccessDialog() {
        JDialog successDialog = new JDialog(this, "Booking Confirmed!", true);
        successDialog.setSize(500, 350);
        successDialog.setLocationRelativeTo(this);
        successDialog.setLayout(new BorderLayout(20, 20));
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Ícono de éxito
        JLabel iconLabel = new JLabel("✓");
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 72));
        iconLabel.setForeground(new Color(46, 204, 113));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("Booking Confirmed Successfully!");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel messageLabel = new JLabel("<html><center>" +
            "Your reservation has been scheduled!<br><br>" +
            "Total: <b>$" + String.format("%.2f", totalPrice) + "</b><br><br>" +
            "You will receive a confirmation email shortly.<br><br>" +
            "To view your reservations, click on<br>" +
            "<b>'View Reservations'</b> in the main menu." +
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
        
        contentPanel.add(iconLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(messageLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(okButton);
        
        successDialog.add(contentPanel);
        successDialog.setVisible(true);
    }
}