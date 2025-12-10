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
    
    private JTextField nameField;
    private JTextField emailField;
    private JSpinner dateSpinner;
    private final JTabbedPane tabbedPane;

    public MainFrame() {
        setTitle("Prestige Events - Reservation System");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // BOTÓN DE LOGOUT - AGREGAR AQUÍ
        setJMenuBar(createMenuBar());

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(245, 247, 250));

        // Pestañas
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(Color.WHITE);

        JPanel clientPanel = createClientPanel();
        JPanel venuePanel = createVenuesPanel();
        JPanel servicesPanel = createServicesPanel();
        JPanel confirmPanel = createConfirmPanel();

        tabbedPane.addTab("1. Client Information", clientPanel);
        tabbedPane.addTab("2. Select Venue", venuePanel);
        tabbedPane.addTab("3. Additional Services", servicesPanel);
        tabbedPane.addTab("4. Confirm Booking", confirmPanel);

        // Deshabilitar pestañas (solo navegación con botones)
        tabbedPane.setEnabledAt(1, false);
        tabbedPane.setEnabledAt(2, false);
        tabbedPane.setEnabledAt(3, false);

        // Panel de navegación
        JPanel navigationPanel = createNavigationPanel();

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(navigationPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // MÉTODO DEL MENÚ BAR CON LOGOUT
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(52, 73, 94));
        
        // Espaciador para empujar el botón a la derecha
        menuBar.add(Box.createHorizontalGlue());
        
        // Información del usuario
        User currentUser = SessionManager.getInstance().getCurrentUser();
        JLabel userLabel = new JLabel("👤 " + currentUser.getName() + " ");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        menuBar.add(userLabel);
        
        // Botón de logout
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setMargin(new Insets(5, 15, 5, 15));
        
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(192, 57, 43));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(231, 76, 60));
            }
        });
        
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                SessionManager.getInstance().logout();
                dispose();
                new WelcomeFrame().setVisible(true);
            }
        });
        
        menuBar.add(logoutButton);
        menuBar.add(Box.createRigidArea(new Dimension(10, 0)));
        
        return menuBar;
    }

    private JPanel createNavigationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Panel izquierdo con Previous
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(new Color(245, 247, 250));
        
        JButton previousButton = new JButton("← Previous");
        previousButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        previousButton.setPreferredSize(new Dimension(140, 45));
        previousButton.setBackground(new Color(149, 165, 166));
        previousButton.setForeground(Color.WHITE);
        previousButton.setFocusPainted(false);
        previousButton.setBorderPainted(false);
        previousButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        previousButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                previousButton.setBackground(new Color(127, 140, 141));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                previousButton.setBackground(new Color(149, 165, 166));
            }
        });
        previousButton.addActionListener(e -> {
            int currentIndex = tabbedPane.getSelectedIndex();
            if (currentIndex > 0) {
                tabbedPane.setSelectedIndex(currentIndex - 1);
            }
        });
        
        JButton viewReservationsButton = new JButton("View Reservations");
        viewReservationsButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        viewReservationsButton.setPreferredSize(new Dimension(180, 45));
        viewReservationsButton.setBackground(new Color(155, 89, 182));
        viewReservationsButton.setForeground(Color.WHITE);
        viewReservationsButton.setFocusPainted(false);
        viewReservationsButton.setBorderPainted(false);
        viewReservationsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewReservationsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                viewReservationsButton.setBackground(new Color(142, 68, 173));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                viewReservationsButton.setBackground(new Color(155, 89, 182));
            }
        });
        viewReservationsButton.addActionListener(e -> {
            new ViewReservationsFrame().setVisible(true);
        });
        
        leftPanel.add(previousButton);
        leftPanel.add(viewReservationsButton);
        
        // Panel derecho con Next
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(new Color(245, 247, 250));
        
        JButton nextButton = new JButton("Next →");
        nextButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        nextButton.setPreferredSize(new Dimension(140, 45));
        nextButton.setBackground(new Color(52, 152, 219));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setBorderPainted(false);
        nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nextButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nextButton.setBackground(new Color(41, 128, 185));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                nextButton.setBackground(new Color(52, 152, 219));
            }
        });
        nextButton.addActionListener(e -> {
            int current = tabbedPane.getSelectedIndex();
            
            // Validar antes de avanzar
            String validationError = validateCurrentTab(current);
            if (validationError != null) {
                JOptionPane.showMessageDialog(this,
                    validationError,
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Avanzar a la siguiente pestaña
            if (current < tabbedPane.getTabCount() - 1) {
                tabbedPane.setSelectedIndex(current + 1);
            }
        });
        
        rightPanel.add(nextButton);
        
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);
        
        return panel;
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

    private JPanel createVenuesPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titleLabel = new JLabel("Select Your Venue");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(41, 128, 185));

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

    private void addVenueOption(JPanel parent, String name, int capacity, double price, String description) {
        JPanel panel = new JPanel(new BorderLayout(15, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        // Panel de imagen (izquierda)
        JPanel imagePanel = createVenueImage(name);
        
        // Panel de información (centro)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nameLabel.setForeground(new Color(52, 73, 94));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel capacityLabel = new JLabel("Capacity: " + capacity + " people");
        capacityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        capacityLabel.setForeground(new Color(127, 140, 141));
        capacityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel priceLabel = new JLabel("Base Price: $" + String.format("%.2f", price));
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        priceLabel.setForeground(new Color(46, 204, 113));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // USAR JTextArea PARA DESCRIPCIONES LARGAS
        JTextArea descriptionArea = new JTextArea(description);
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        descriptionArea.setForeground(new Color(127, 140, 141));
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);           // ✅ Permite saltos de línea
        descriptionArea.setWrapStyleWord(true);      // ✅ Corta por palabras completas
        descriptionArea.setRows(3);                  // ✅ Máximo 3 líneas
        descriptionArea.setBorder(null);             // Sin borde
        descriptionArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionArea.setFocusable(false);         // No se puede enfocar
        
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(capacityLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        infoPanel.add(priceLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        infoPanel.add(descriptionArea);
        
        // Botón de selección (derecha)
        JButton selectButton = new JButton("Select");
        selectButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        selectButton.setPreferredSize(new Dimension(140, 40));
        selectButton.setBackground(new Color(52, 152, 219));
        selectButton.setForeground(Color.WHITE);
        selectButton.setFocusPainted(false);
        selectButton.setBorderPainted(false);
        selectButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        selectButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (selectButton.getBackground().equals(new Color(52, 152, 219))) {
                    selectButton.setBackground(new Color(41, 128, 185));
                }
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!selectButton.getBackground().equals(new Color(46, 204, 113))) {
                    selectButton.setBackground(new Color(52, 152, 219));
                }
            }
        });
        
        selectButton.addActionListener(e -> {
            selectedVenue = new Venue(name, capacity);
            selectedVenuePrice = price;
            
            // Resetear todos los botones
            Component[] components = parent.getComponents();
            for (Component comp : components) {
                if (comp instanceof JPanel venuePanel) {
                    Component[] venueComponents = venuePanel.getComponents();
                    for (Component venueComp : venueComponents) {
                        if (venueComp instanceof JPanel buttonContainer) {
                            Component[] buttonComponents = buttonContainer.getComponents();
                            for (Component btnComp : buttonComponents) {
                                if (btnComp instanceof JButton btn && !btn.equals(selectButton)) {
                                    btn.setText("Select");
                                    btn.setBackground(new Color(52, 152, 219));
                                }
                            }
                        }
                    }
                }
            }
            
            // Marcar como seleccionado
            selectButton.setText("✓ Selected");
            selectButton.setBackground(new Color(46, 204, 113));
            
            recalculateTotalPrice();
        });
        
        // Panel para el botón (mantener alineación vertical)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(selectButton);
        
        panel.add(imagePanel, BorderLayout.WEST);
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);
        
        parent.add(panel);
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
        
        // Primera letra del venue
        String displayText = (venueName != null) ? venueName.substring(0, 1).toUpperCase() : "?";
        
        JLabel textLabel = new JLabel(displayText);
        textLabel.setFont(new Font("Segoe UI", Font.BOLD, 64));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setForeground(Color.WHITE);
        
        imagePanel.add(textLabel, BorderLayout.CENTER);
    }

    private JPanel createServicesPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titleLabel = new JLabel("Additional Services");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(41, 128, 185));

        JPanel servicesPanel = new JPanel(new GridLayout(6, 1, 15, 15));
        servicesPanel.setBackground(new Color(245, 247, 250));

        addServiceOption(servicesPanel, "Premium Catering", 500, 
            "Gourmet menu with multiple course options");
        addServiceOption(servicesPanel, "Professional Photography", 800,
            "Full day coverage with edited photos");
        addServiceOption(servicesPanel, "Live Music Band", 1200,
            "Professional 4-piece band for 4 hours");
        addServiceOption(servicesPanel, "Floral Decoration", 600,
            "Complete venue decoration with premium flowers");
        addServiceOption(servicesPanel, "Video Recording", 1000,
            "Professional videography with drone footage");
        addServiceOption(servicesPanel, "Custom Lighting", 400,
            "Professional lighting setup and effects");

        JScrollPane scrollPane = new JScrollPane(servicesPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void addServiceOption(JPanel parent, String name, double price, String description) {
        JPanel panel = new JPanel(new BorderLayout(15, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameLabel.setForeground(new Color(52, 73, 94));

        JLabel priceLabel = new JLabel("$" + String.format("%.2f", price));
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        priceLabel.setForeground(new Color(46, 204, 113));

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        descLabel.setForeground(new Color(127, 140, 141));

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(priceLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(descLabel);

        // Detectar si es "Premium Catering" para abrir el diálogo especial
        if (name.equals("Premium Catering")) {
            JButton selectMenuButton = new JButton("Select Menu");
            selectMenuButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            selectMenuButton.setPreferredSize(new Dimension(140, 40));
            selectMenuButton.setBackground(new Color(52, 152, 219));
            selectMenuButton.setForeground(Color.WHITE);
            selectMenuButton.setFocusPainted(false);
            selectMenuButton.setBorderPainted(false);
            selectMenuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            selectMenuButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (selectMenuButton.getBackground().equals(new Color(52, 152, 219))) {
                        selectMenuButton.setBackground(new Color(41, 128, 185));
                    }
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (!selectMenuButton.getBackground().equals(new Color(46, 204, 113))) {
                        selectMenuButton.setBackground(new Color(52, 152, 219));
                    }
                }
            });

            selectMenuButton.addActionListener(e -> {
                CateringMenuFrame cateringFrame = new CateringMenuFrame(this);
                cateringFrame.setVisible(true);

                if (cateringFrame.isConfirmed()) {
                    // Remover servicio de catering previo si existe
                    selectedServices.removeIf(s -> s instanceof CateringService);

                    // Agregar nuevo servicio de catering con el precio y detalles
                    CateringService cateringService = new CateringService(
                        cateringFrame.getTotalPrice(),
                        cateringFrame.getSelectionSummary()
                    );
                    selectedServices.add(cateringService);

                    // Cambiar botón a "Selected"
                    selectMenuButton.setText("✓ Selected");
                    selectMenuButton.setBackground(new Color(46, 204, 113));

                    recalculateTotalPrice();
                }
            });

            panel.add(infoPanel, BorderLayout.CENTER);
            panel.add(selectMenuButton, BorderLayout.EAST);
        } else {
            // Para otros servicios, usar checkbox
            JCheckBox checkBox = new JCheckBox();
            checkBox.setBackground(Color.WHITE);
            checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
            checkBox.addActionListener(e -> {
                if (checkBox.isSelected()) {
                    AbstractService service = switch (name) {
                        case "Professional Photography" -> new PhotographyService();
                        case "Live Music Band" -> new MusicService();
                        case "Floral Decoration" -> new DecorationService();
                        case "Video Recording" -> new VideoService();
                        case "Custom Lighting" -> new LightingService();
                        default -> null;
                    };
                    if (service != null) {
                        selectedServices.add(service);
                    }
                } else {
                    selectedServices.removeIf(s -> {
                        String serviceName = s.getClass().getSimpleName();
                        return serviceName.contains(name.split(" ")[0]);
                    });
                }
                recalculateTotalPrice();
            });

            panel.add(checkBox, BorderLayout.WEST);
            panel.add(infoPanel, BorderLayout.CENTER);
        }

        parent.add(panel);
    }

    private JPanel createConfirmPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titleLabel = new JLabel("Confirm Your Booking");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(41, 128, 185));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Panel central con todo el contenido
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);

        // Panel de resumen
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
        summaryPanel.setBackground(Color.WHITE);
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        summaryPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        summaryPanel.setMaximumSize(new Dimension(700, Integer.MAX_VALUE));

        JLabel summaryTitle = new JLabel("Reservation Summary");
        summaryTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        summaryTitle.setForeground(new Color(52, 73, 94));
        summaryTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea summaryText = new JTextArea();
        summaryText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        summaryText.setForeground(new Color(52, 73, 94));
        summaryText.setBackground(Color.WHITE);
        summaryText.setEditable(false);
        summaryText.setText(generateSummary());
        summaryText.setAlignmentX(Component.CENTER_ALIGNMENT);

        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        totalLabel.setForeground(new Color(46, 204, 113));
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton confirmButton = new JButton("Confirm Reservation");
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        confirmButton.setPreferredSize(new Dimension(220, 50));
        confirmButton.setMaximumSize(new Dimension(220, 50));
        confirmButton.setBackground(new Color(46, 204, 113));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);
        confirmButton.setBorderPainted(false);
        confirmButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.addActionListener(e -> confirmReservation());

        summaryPanel.add(summaryTitle);
        summaryPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        summaryPanel.add(summaryText);
        summaryPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        summaryPanel.add(totalLabel);
        summaryPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        summaryPanel.add(confirmButton);

        // Panel de información de contacto
        JPanel contactPanel = new JPanel();
        contactPanel.setLayout(new BoxLayout(contactPanel, BoxLayout.Y_AXIS));
        contactPanel.setBackground(new Color(236, 240, 241));
        contactPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        contactPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contactPanel.setMaximumSize(new Dimension(700, Integer.MAX_VALUE));

        JLabel contactTitle = new JLabel("Contact Information");
        contactTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        contactTitle.setForeground(new Color(52, 73, 94));
        contactTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel companyName = new JLabel("Prestige Events LLC");
        companyName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        companyName.setForeground(new Color(41, 128, 185));
        companyName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel phoneLabel = new JLabel("📞 Phone: +1 (555) 123-4567");
        phoneLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        phoneLabel.setForeground(new Color(52, 73, 94));
        phoneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel emailLabel = new JLabel("✉️ Email: info@prestigeevents.com");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailLabel.setForeground(new Color(52, 73, 94));
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel addressLabel = new JLabel("📍 Address: 123 Event Plaza, New York, NY 10001");
        addressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addressLabel.setForeground(new Color(52, 73, 94));
        addressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel hoursLabel = new JLabel("🕐 Business Hours: Mon-Fri 9:00 AM - 6:00 PM");
        hoursLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        hoursLabel.setForeground(new Color(52, 73, 94));
        hoursLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contactPanel.add(contactTitle);
        contactPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contactPanel.add(companyName);
        contactPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contactPanel.add(phoneLabel);
        contactPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contactPanel.add(emailLabel);
        contactPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contactPanel.add(addressLabel);
        contactPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contactPanel.add(hoursLabel);

        centerPanel.add(summaryPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(contactPanel);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    private String generateSummary() {
        StringBuilder summary = new StringBuilder();
        User currentUser = SessionManager.getInstance().getCurrentUser();
        
        summary.append("Client: ").append(currentUser.getName()).append("\n");
        summary.append("Email: ").append(currentUser.getEmail()).append("\n");
        
        if (dateSpinner != null && dateSpinner.getValue() != null) {
            summary.append("Event Date: ").append(dateSpinner.getValue()).append("\n");
        }
        
        summary.append("\n");

        if (selectedVenue != null) {
            summary.append("Venue: ").append(selectedVenue.getName()).append("\n");
            summary.append("Capacity: ").append(selectedVenue.getCapacity()).append(" people\n");
            summary.append("Venue Price: $").append(String.format("%.2f", selectedVenuePrice)).append("\n\n");
        }

        if (!selectedServices.isEmpty()) {
            summary.append("Selected Services:\n");
            for (AbstractService service : selectedServices) {
                if (service != null) {  // ✅ Verificar que no sea null
                    if (service instanceof CateringService cateringService) {
                        summary.append("• ").append(cateringService.getMenuDetails())
                            .append(" - $").append(String.format("%.2f", service.calculatePrice())).append("\n");
                    } else {
                        String serviceName = service.getClass().getSimpleName().replace("Service", "");
                        summary.append("• ").append(serviceName)
                            .append(" - $").append(String.format("%.2f", service.calculatePrice())).append("\n");
                    }
                }
            }
        }

        return summary.toString();
    }

    private void recalculateTotalPrice() {
        totalPrice = selectedVenuePrice;
        
        for (AbstractService service : selectedServices) {
            totalPrice += service.calculatePrice();
        }
        
        updateTotal();
    }

    private void updateTotal() {
        if (totalLabel != null) {
            totalLabel.setText("Total: $" + String.format("%.2f", totalPrice));
        }
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
        JDialog successDialog = new JDialog(this, "Reservation Confirmed!", true);
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
        
        JLabel titleLabel = new JLabel("Reservation Successful!");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel messageLabel = new JLabel("<html><center>" +
            "Your reservation has been confirmed!<br><br>" +
            "Total Amount: <b>$" + String.format("%.2f", totalPrice) + "</b><br><br>" +
            "You can view your reservation details in 'View Reservations'" +
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