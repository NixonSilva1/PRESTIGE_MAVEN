package gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class CateringMenuFrame extends JDialog {
    private String selectedMenu = "";
    private final List<String> selectedBeverages = new ArrayList<>();
    private double totalPrice = 0;
    private JLabel priceLabel;
    private boolean confirmed = false;
    
    // Precios de los menús
    private static final double MENU_CLASSIC_PRICE = 500.0;
    private static final double MENU_MEDITERRANEAN_PRICE = 650.0;
    private static final double MENU_GOURMET_PRICE = 800.0;
    private static final double MENU_VEGETARIAN_PRICE = 550.0;
    private static final double MENU_SEAFOOD_PRICE = 900.0;
    
    // Precios de bebidas
    private static final double BEVERAGE_PRICE = 50.0;
    
    public CateringMenuFrame(JFrame parent) {
        super(parent, "Select Catering Menu", true);
        setSize(900, 700);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Título
        JLabel titleLabel = new JLabel("Premium Catering Menu Selection");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(41, 128, 185));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Panel de contenido con scroll
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(245, 247, 250));
        
        // Sección de menús
        JPanel menusSection = createMenusSection();
        contentPanel.add(menusSection);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Sección de bebidas
        JPanel beveragesSection = createBeveragesSection();
        contentPanel.add(beveragesSection);
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        // Panel inferior con precio y botones
        JPanel bottomPanel = createBottomPanel();
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createMenusSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(new Color(245, 247, 250));
        
        JLabel sectionTitle = new JLabel("Select Your Menu");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        sectionTitle.setForeground(new Color(52, 73, 94));
        sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        section.add(sectionTitle);
        section.add(Box.createRigidArea(new Dimension(0, 15)));
        
        ButtonGroup menuGroup = new ButtonGroup();
        
        // Menú 1: Classic
        JPanel menu1 = createMenuOption(
            "Classic Menu - $500",
            "• Appetizer: Caesar Salad\n• Main Course: Grilled Chicken with Roasted Vegetables\n• Dessert: Chocolate Mousse",
            menuGroup,
            "Classic Menu"
        );
        section.add(menu1);
        section.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Menú 2: Mediterranean
        JPanel menu2 = createMenuOption(
            "Mediterranean Menu - $650",
            "• Appetizer: Greek Salad with Feta Cheese\n• Main Course: Grilled Salmon with Lemon Herbs\n• Dessert: Tiramisu",
            menuGroup,
            "Mediterranean Menu"
        );
        section.add(menu2);
        section.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Menú 3: Gourmet
        JPanel menu3 = createMenuOption(
            "Gourmet Menu - $800",
            "• Appetizer: Foie Gras with Toast Points\n• Main Course: Beef Tenderloin with Truffle Sauce\n• Dessert: Crème Brûlée",
            menuGroup,
            "Gourmet Menu"
        );
        section.add(menu3);
        section.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Menú 4: Vegetarian
        JPanel menu4 = createMenuOption(
            "Vegetarian Menu - $550",
            "• Appetizer: Caprese Salad\n• Main Course: Vegetable Lasagna with Pesto\n• Dessert: Fresh Fruit Tart",
            menuGroup,
            "Vegetarian Menu"
        );
        section.add(menu4);
        section.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Menú 5: Seafood
        JPanel menu5 = createMenuOption(
            "Seafood Deluxe Menu - $900",
            "• Appetizer: Oysters on the Half Shell\n• Main Course: Lobster Thermidor with Garlic Butter\n• Dessert: Lemon Cheesecake",
            menuGroup,
            "Seafood Deluxe Menu"
        );
        section.add(menu5);
        
        return section;
    }
    
    private JPanel createMenuOption(String title, String description, 
                                    ButtonGroup group, String menuName) {
        JPanel panel = new JPanel(new BorderLayout(15, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        // Radio button
        JRadioButton radioButton = new JRadioButton();
        radioButton.setBackground(Color.WHITE);
        radioButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        radioButton.addActionListener(e -> {
            selectedMenu = menuName;
            updateTotalPrice();
        });
        group.add(radioButton);
        
        // Información del menú
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(52, 73, 94));
        
        JTextArea descArea = new JTextArea(description);
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        descArea.setForeground(new Color(127, 140, 141));
        descArea.setBackground(Color.WHITE);
        descArea.setEditable(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        infoPanel.add(descArea);
        
        panel.add(radioButton, BorderLayout.WEST);
        panel.add(infoPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createBeveragesSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(new Color(245, 247, 250));
        
        JLabel sectionTitle = new JLabel("Select Beverages (Optional) - $50 each");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        sectionTitle.setForeground(new Color(52, 73, 94));
        sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel sectionSubtitle = new JLabel("You can select multiple options");
        sectionSubtitle.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        sectionSubtitle.setForeground(new Color(127, 140, 141));
        sectionSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        section.add(sectionTitle);
        section.add(Box.createRigidArea(new Dimension(0, 5)));
        section.add(sectionSubtitle);
        section.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Panel de bebidas
        JPanel beveragesPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        beveragesPanel.setBackground(new Color(245, 247, 250));
        
        String[] beverages = {
            "Premium Wine Selection",
            "Champagne",
            "Craft Beer",
            "Soft Drinks & Juices",
            "Coffee & Tea Service",
            "Cocktail Bar Service"
        };
        
        for (String beverage : beverages) {
            JPanel beveragePanel = createBeverageOption(beverage);
            beveragesPanel.add(beveragePanel);
        }
        
        section.add(beveragesPanel);
        
        return section;
    }
    
    private JPanel createBeverageOption(String beverageName) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        
        JCheckBox checkBox = new JCheckBox(beverageName);
        checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        checkBox.setBackground(Color.WHITE);
        checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkBox.addActionListener(e -> {
            if (checkBox.isSelected()) {
                selectedBeverages.add(beverageName);
            } else {
                selectedBeverages.remove(beverageName);
            }
            updateTotalPrice();
        });
        
        panel.add(checkBox, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Precio total
        priceLabel = new JLabel("Total: $0.00");
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        priceLabel.setForeground(new Color(46, 204, 113));
        
        // Botones
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonsPanel.setBackground(Color.WHITE);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cancelButton.setPreferredSize(new Dimension(130, 45));
        cancelButton.setBackground(new Color(149, 165, 166));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });
        
        JButton confirmButton = new JButton("Confirm Selection");
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        confirmButton.setPreferredSize(new Dimension(180, 45));
        confirmButton.setBackground(new Color(46, 204, 113));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);
        confirmButton.setBorderPainted(false);
        confirmButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmButton.addActionListener(e -> {
            if (selectedMenu.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Please select a menu before confirming.",
                    "No Menu Selected",
                    JOptionPane.WARNING_MESSAGE);
            } else {
                confirmed = true;
                dispose();
            }
        });
        
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(confirmButton);
        
        panel.add(priceLabel, BorderLayout.WEST);
        panel.add(buttonsPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private void updateTotalPrice() {
        totalPrice = 0;
        
        // Agregar precio del menú seleccionado
        switch (selectedMenu) {
            case "Classic Menu" -> totalPrice += MENU_CLASSIC_PRICE;
            case "Mediterranean Menu" -> totalPrice += MENU_MEDITERRANEAN_PRICE;
            case "Gourmet Menu" -> totalPrice += MENU_GOURMET_PRICE;
            case "Vegetarian Menu" -> totalPrice += MENU_VEGETARIAN_PRICE;
            case "Seafood Deluxe Menu" -> totalPrice += MENU_SEAFOOD_PRICE;
        }
        
        // Agregar precio de bebidas
        totalPrice += selectedBeverages.size() * BEVERAGE_PRICE;
        
        priceLabel.setText("Total: $" + String.format("%.2f", totalPrice));
    }
    
    // Getters
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public String getSelectedMenu() {
        return selectedMenu;
    }
    
    public List<String> getSelectedBeverages() {
        return new ArrayList<>(selectedBeverages);
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public String getSelectionSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Catering - ").append(selectedMenu);
        if (!selectedBeverages.isEmpty()) {
            summary.append(" + Beverages: ");
            summary.append(String.join(", ", selectedBeverages));
        }
        return summary.toString();
    }
}