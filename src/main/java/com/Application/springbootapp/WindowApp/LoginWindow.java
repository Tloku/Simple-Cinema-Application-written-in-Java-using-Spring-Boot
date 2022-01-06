package com.Application.springbootapp.WindowApp;

import com.Application.springbootapp.Entities.User;
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
    private iRoleService roleService;
    private iUserService userService;
    private iOrderService orderService;
    private iTicketService ticketService;
    private iMovieService movieService;
    private iScheduleService scheduleService;
    private iRepertoireService repertoireService;
    private iCategoryService categoryService;

    private JLabel correctLabel = new JLabel("Zły email lub hasło");
    private RegisterWindow regWin;

    @Autowired
    public LoginWindow(iRoleService roleService, iUserService userService, iOrderService orderService,
                       iTicketService ticketService, iMovieService movieService, iScheduleService scheduleService,
                       iRepertoireService repertoireService, iCategoryService categoryService) {
        super("Logowanie");
        this.roleService = roleService;
        this.userService = userService;
        this.orderService = orderService;
        this.ticketService = ticketService;
        this.movieService = movieService;
        this.scheduleService = scheduleService;
        this.repertoireService = repertoireService;
        this.categoryService = categoryService;
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
        regWin = new RegisterWindow(userService);

        registerButton.addActionListener(e -> {regWin.setVisible(true);});

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
        User uzytkownik = userService.findUserByEmail(email);
        if(uzytkownik.getPassword().equals(password)){
            correctLabel.setForeground(Color.GREEN);
            correctLabel.setText("Zalogowano!");
            correctLabel.setVisible(true);
            super.update(this.getGraphics());
            TimeUnit.SECONDS.sleep(1);
            this.setVisible(false);

            if(uzytkownik.getRola().getRoleID() == 1) {
                EmployeeWindow employeeWindow = new EmployeeWindow(orderService, ticketService, movieService,
                        scheduleService, repertoireService, categoryService);
                employeeWindow.setVisible(true);
                System.out.println("Otworzylem okno employee");
            } else if (uzytkownik.getRola().getRoleID() == 2) {
                ClientWindow clientWindow = new ClientWindow(orderService, ticketService, movieService,
                        scheduleService, repertoireService, userService, uzytkownik.getUserID());
                clientWindow.setVisible(true);
                System.out.println("Otworzylem okno klient");
            }
        } else {
            correctLabel.setVisible(true);
            correctLabel.setForeground(Color.RED);
            correctLabel.setText("Zle haslo!");
            super.update(this.getGraphics());
        }
    }
}
