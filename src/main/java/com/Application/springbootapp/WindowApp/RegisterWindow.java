package com.Application.springbootapp.WindowApp;

import com.Application.springbootapp.Services.iUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import static org.apache.logging.log4j.util.Strings.isBlank;

@Controller
public class RegisterWindow extends JFrame {
    private JLabel welcomeRegister = new JLabel("Zarejestruj się");
    private JLabel nameLabel = new JLabel("Imię: ");
    private JLabel surnameLabel = new JLabel("Nazwisko: ");
    private JLabel emailLabel = new JLabel("Email: ");
    private JLabel passwordLabel = new JLabel("Hasło: ");
    private JLabel correctLabel = new JLabel("Nie podano każdej danej");
    private JTextField nameTextField = new JTextField(16);
    private JTextField surnameTextField = new JTextField(16);
    private JTextField emailTextField = new JTextField(16);
    private JPasswordField passwordField = new JPasswordField(16);
    private JButton registerButton = new JButton("Zarejestruj");
    private JPanel panel = new JPanel(new GridBagLayout());
    private iUserService userService;

    @Autowired
    RegisterWindow(iUserService userService){
        super("Rejestracja");
        this.userService = userService;
        initComponents();
    }

    private void initComponents(){
        int windowSizeWidth = 400;
        int windowsSizeHeight = 400;
        this.setSize(windowSizeWidth,windowsSizeHeight);
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.setLocation((width - windowSizeWidth)/2, (height - windowsSizeHeight)/2);
        initLayout();
        correctLabel.setForeground(Color.RED);
        correctLabel.setFont(new Font("Serif", Font.BOLD, 13));
        correctLabel.setVisible(false);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(false);

        registerButton.addActionListener(e -> {
            try{
                registerButtonActionListener();
            }catch(InterruptedException ex){
                System.out.println(ex.getMessage());
            }
        });
    }

    private void registerButtonActionListener() throws InterruptedException {
        String name, surname, email, password;
        name = nameTextField.getText();
        surname = surnameTextField.getText();
        email = emailTextField.getText();
        password = new String(passwordField.getPassword());

        boolean valid = anyBlank(name, surname, email, password);
        if(!valid){
            userService.add(name, surname, email, password, 2);
            if(correctLabel.isVisible()){
                correctLabel.setVisible(false);
            }
            correctLabel.setVisible(true);
            correctLabel.setForeground(Color.GREEN);
            correctLabel.setText("Zarejestrowano!");
            super.update(this.getGraphics());
            TimeUnit.SECONDS.sleep(1);
            correctLabel.setText("Zamykam okno");
            super.update(this.getGraphics());
            TimeUnit.MILLISECONDS.sleep(500);
            this.setVisible(false);
        }
        else {
            correctLabel.setVisible(true);
        }
    }

    public boolean anyBlank(String ...strings){
        return Stream.of(strings).anyMatch(string->isBlank(string));
    }

    private void initLayout(){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(welcomeRegister, constraints);

        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(nameLabel, constraints);

        constraints.gridx = 1;
        panel.add(nameTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(surnameLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(surnameTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(emailLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(emailTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(registerButton, constraints);

        constraints.gridy = 6;
        panel.add(correctLabel, constraints);

        this.add(panel);
    }
}
