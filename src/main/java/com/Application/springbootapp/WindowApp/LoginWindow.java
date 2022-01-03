package com.Application.springbootapp.WindowApp;

import com.Application.springbootapp.Entities.Użytkownik;
import com.Application.springbootapp.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

//TODO możliwość zmiany hasła
@Controller
public class LoginWindow extends JFrame {
    private JPanel panel = new JPanel(new GridBagLayout());
    private JLabel emailLabel = new JLabel("Email: ");
    private JLabel passwordLabel = new JLabel("Haslo: ");
    private JTextField emailTextField = new JTextField(16);
    private JPasswordField passwordTextField = new JPasswordField(16);
    private JButton loginButton = new JButton("Zaloguj");
    private JButton registerButton = new JButton("Zarejestruj");
    private iRolaService rolaService;
    private iUżytkownikService uzytkownikService;
    private iZamówienieService zamowienieService;
    private iBiletService biletService;
    private iFilmService filmService;
    private iHarmonogramService harmonogramService;
    private iRepertuarKinaService repertuarKinaService;
    private iGatunekService gatunekService;

    private JLabel correctLabel = new JLabel("Zły email lub hasło");
    private RegisterWindow regWin;

    @Autowired
    public LoginWindow(iRolaService rolaService, iUżytkownikService uzytkownikService, iZamówienieService zamowienieService,
                       iBiletService biletService, iFilmService filmService, iHarmonogramService harmonogramService,
                       iRepertuarKinaService repertuarKinaService, iGatunekService gatunekService) {
        super("Logowanie");

        this.rolaService = rolaService;
        this.uzytkownikService = uzytkownikService;
        this.zamowienieService = zamowienieService;
        this.biletService = biletService;
        this.filmService = filmService;
        this.harmonogramService = harmonogramService;
        this.repertuarKinaService = repertuarKinaService;
        this.gatunekService = gatunekService;

        initComponents();
        initLayout();
        this.setVisible(true);
    }

    private void initLayout() {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(emailLabel, constraints);

        constraints.gridx = 1;
        panel.add(emailTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        panel.add(passwordTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(loginButton, constraints);

        constraints.gridx = 1;
        panel.add(registerButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(correctLabel, constraints);

        this.add(panel);
    }

    private void initComponents() {
        int windowSizeWidth = 400;
        int windowsSizeHeight = 300;
        this.setSize(windowSizeWidth, windowsSizeHeight);
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.setLocation((width - windowSizeWidth) / 2, (height - windowsSizeHeight) / 2);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        correctLabel.setForeground(Color.RED);
        correctLabel.setFont(new Font("Serif", Font.BOLD, 13));
        correctLabel.setVisible(false);
        regWin = new RegisterWindow(rolaService, uzytkownikService);

        registerButton.addActionListener(e -> {
            regWin.setVisible(true);
        });

        loginButton.addActionListener(e -> {
            String email = emailTextField.getText();
            String password = new String(passwordTextField.getPassword());
            boolean valid = regWin.anyBlank(email, password);

            if(!valid && !email.equals(password)) {
                try{
                    loginButtonActionListener(email, password);
                }catch(InterruptedException ex){
                    System.out.println(ex.getMessage());
                }
            }
            else{
                correctLabel.setVisible(true);
            }
        });
    }

    private void loginButtonActionListener(String email, String password) throws InterruptedException {
        //TODO logika logowania się, pobieranie danych z bazy i sprawdzenie
        // czy haslo dla emailu jest prawidłowe
        Użytkownik uzytkownik = uzytkownikService.findUzytkownikByEmail(email);

        if(uzytkownik.getHaslo().equals(password)){
            correctLabel.setVisible(true);
            correctLabel.setForeground(Color.GREEN);
            correctLabel.setText("Zalogowano!");
            correctLabel.paintImmediately(correctLabel.getVisibleRect());
            TimeUnit.SECONDS.sleep(1);
            TimeUnit.MILLISECONDS.sleep(500);
            this.setVisible(false);

            if(uzytkownik.getRola().getRolaID() == 1) {
                EmployeeWindow employeeWindow = new EmployeeWindow(zamowienieService, biletService, filmService,
                         harmonogramService, uzytkownikService, repertuarKinaService, gatunekService);
                employeeWindow.setVisible(true);
                System.out.println("Otworzylem okno employee");
            } else if (uzytkownik.getRola().getRolaID() == 2) {
                ClientWindow clientWindow = new ClientWindow(zamowienieService, biletService, filmService,
                        harmonogramService, repertuarKinaService, uzytkownik.getUzytkownikID());
                clientWindow.setVisible(true);
                System.out.println("Otworzylem okno klient");
            }
        } else {
            correctLabel.setVisible(true);
            correctLabel.setForeground(Color.RED);
            correctLabel.setText("Zle haslo!");
            correctLabel.paintImmediately(correctLabel.getVisibleRect());
        }
    }
}
