package com.Application.springbootapp.WindowApp;

import com.Application.springbootapp.Entities.User;
import com.Application.springbootapp.Services.iUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.*;

@Component
public class ChangePasswordWindow extends JFrame {
    private JPanel panel = new JPanel(new GridBagLayout());
    private iUserService userService;
    private JLabel newPasswordLabel = new JLabel("Podaj nowe hasło");
    private JPasswordField newPasswordPasswordField = new JPasswordField(16);
    private JLabel repeatPasswordLabel = new JLabel("Powtórz nowe hasło");
    private JPasswordField repeatPasswordPasswordField = new JPasswordField(16);
    private JButton changePasswordButton = new JButton("Zmień");
    private int userID;

    @Autowired
    public ChangePasswordWindow(iUserService userService, @Value("${property.userID:0}") int userID) {
        super("Zmień hasło");
        this.userService = userService;
        this.userID = userID;
        initLayout();
        initComponents();
    }

    private void initComponents() {
        int windowSizeWidth = 400;
        int windowsSizeHeight = 300;
        this.setSize(windowSizeWidth, windowsSizeHeight);
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.setLocation((width - windowSizeWidth) / 2, (height - windowsSizeHeight) / 2);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(false);
        changePasswordButton.addActionListener(e -> {changePasswordButtonActionListener();});
    }

    private void changePasswordButtonActionListener() {
        User user = userService.findUserByID(userID);
        String oldPassword = user.getPassword();
        String password = new String(newPasswordPasswordField.getPassword());
        String repeatedPassword = new String(repeatPasswordPasswordField.getPassword());

        if(!oldPassword.equals(password)) {
            int changeConfirm = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz zmienić hasło?",
                    "Zmiana hasła",
                    JOptionPane.YES_NO_OPTION);
            if(password.equals(repeatedPassword) && changeConfirm == JOptionPane.YES_OPTION) {
                userService.changePasswordByUserID(userID, password);
                this.setVisible(false);
            } else if(!password.equals(repeatedPassword)) {
                JOptionPane.showMessageDialog(null, "Hasła nie są identyczne!");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Stare hasło nie może być nowym hasłem!");
            return;
        }
    }

    private void initLayout() {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(newPasswordLabel, constraints);

        constraints.gridx = 1;
        panel.add(newPasswordPasswordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(repeatPasswordLabel, constraints);

        constraints.gridx = 1;
        panel.add(repeatPasswordPasswordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(changePasswordButton, constraints);

        this.add(panel);
    }

}
