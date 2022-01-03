package com.Application.springbootapp;

import com.Application.springbootapp.WindowApp.LoginWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class SpringBootSwingCommandLineRunner implements CommandLineRunner {
    private final LoginWindow logWin;

    @Autowired
    public SpringBootSwingCommandLineRunner(LoginWindow logWin) {
        this.logWin = logWin;
    }

    @Override
    public void run(String... args) throws Exception {
        EventQueue.invokeLater(() -> logWin.setVisible(true));
    }
}
