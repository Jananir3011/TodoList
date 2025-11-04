import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;

public class TodoList extends JFrame {
    private DefaultListModel<String> listModel;
    private JList<String> taskList;
    private JTextField taskField;
    private JButton addButton, removeButton;

    public TodoList() {
        setTitle("✨ To-Do List ✨");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

       
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 230, 255), 0, getHeight(), new Color(204, 229, 255));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(10, 10));
        add(mainPanel);

        
        taskField = new JTextField();
        taskField.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        taskField.setForeground(new Color(51, 25, 102)); 
        taskField.setBackground(new Color(255, 250, 205)); 
        taskField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(102, 0, 204), 3, true), 
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(taskField, BorderLayout.NORTH);

        
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
        taskList.setCellRenderer(new TaskCellRenderer());
        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(102, 0, 204), 3, true));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(204, 229, 255));
        addButton = createFancyButton("➕ Add Task", new Color(102, 255, 178));
        removeButton = createFancyButton("❌ Remove Task", new Color(255, 102, 102));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    
        addButton.addActionListener(e -> {
            String task = taskField.getText().trim();
            if (!task.isEmpty()) {
                listModel.addElement(task);
                taskField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Enter a task!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        
        removeButton.addActionListener(e -> {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Select a task to remove!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        
        taskList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = taskList.locationToIndex(e.getPoint());
                    String task = listModel.get(index);
                    if (!task.startsWith("✔ ")) {
                        listModel.set(index, "✔ " + task);
                    } else {
                        listModel.set(index, task.replace("✔ ", ""));
                    }
                    taskList.repaint();
                }
            }
        });

        setVisible(true);
    }

    
    private static class TaskCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                                                      int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            String task = value.toString();
            if (task.startsWith("✔ ")) {
                label.setForeground(new Color(128, 128, 128)); 
                label.setFont(label.getFont().deriveFont(Font.ITALIC, 18));
            } else {
                label.setForeground(new Color(51, 25, 102));
                label.setFont(label.getFont().deriveFont(Font.BOLD, 18));
            }
            if (isSelected) {
                label.setBackground(new Color(255, 255, 153)); 
            }
            return label;
        }
    }

    private JButton createFancyButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Verdana", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TodoList::new);
    }
}