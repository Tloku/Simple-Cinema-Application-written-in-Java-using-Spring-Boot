package com.Application.springbootapp.WindowApp;

import com.Application.springbootapp.Entities.Movie;
import com.Application.springbootapp.Entities.Category;
import com.Application.springbootapp.Services.iMovieService;
import com.Application.springbootapp.Services.iCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.stream.Stream;
import static org.apache.logging.log4j.util.Strings.isBlank;

@Controller
public class AddMovieWindow extends JFrame {
    private JPanel panel = new JPanel(new GridBagLayout());
    private JLabel titleLabel = new JLabel("Tytuł: ");
    private JTextField titleTextField = new JTextField(16);
    private JLabel movieLengthLabel = new JLabel("Długość: ");
    private JTextField movieLengthTextField = new JTextField(16);
    private JLabel descriptionLabel = new JLabel("Opis: ");
    private JTextField descriptionTextField = new JTextField(16);
    private JLabel studioLabel = new JLabel("Studio: ");
    private JTextField studioTextField = new JTextField(16);
    private JLabel dateLabel = new JLabel("Data wydania: ");
    private JTextField dataTextField = new JTextField("YYYY-MM-DD",16);
    private JLabel categoryLabel = new JLabel("Gatunek: ");
    private JComboBox categoryComboBox = new JComboBox();
    private JButton addButton = new JButton("Dodaj film");
    private iMovieService movieService;
    private iCategoryService categoryService;
    private int repertoireID;
    private List<Category> categoriesList;
    private Vector<String> categoriesListStrings = new Vector<>();
    private int categoryID;
    private Date data;


    @Autowired
    public AddMovieWindow(iMovieService movieService, iCategoryService categoryService,
                          @Value("${property.repertoireID:0}") int repertoireID) {
        super("Dodaj film");
        this.movieService = movieService;
        this.repertoireID = repertoireID;
        this.categoryService = categoryService;
        initComponents();
        initLayout();
    }

    private void initComponents() {
        int windowSizeWidth = 350;
        int windowsSizeHeight = 500;
        this.setSize(windowSizeWidth, windowsSizeHeight);
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.setLocation((width - windowSizeWidth) / 2, (height - windowsSizeHeight) / 2);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(false);
        initCategoryComboBox();
        addButton.setEnabled(false);
        addButton.addActionListener(e -> {addButtonActionListener();});
        categoryComboBox.addActionListener(e ->{categoryComboBoxActionListener();});
    }

    private void addButtonActionListener() {
        String title = titleTextField.getText();
        int length;
        String description = descriptionTextField.getText();
        String studio = studioTextField.getText();
        String date = dataTextField.getText();
        boolean valid = anyBlank(title, studio, date);
        if(!valid){
            try{
                data = new SimpleDateFormat("YYYY-MM-DD").parse(date);
            } catch(Exception ex) {
                System.out.println(ex.getMessage());
                return;
            }
            try {
                length = Integer.parseInt(movieLengthTextField.getText());
            } catch(NumberFormatException ex) {
                System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(null, "Nie podano prawidlowej dlugosci filmu");
                return;
            }
            Movie movie = movieService.addMovie(title, length, description, studio, data, repertoireID, categoryID);
            this.setVisible(false);
            JOptionPane.showMessageDialog(null,"ID nowo dodanego filmu to: " + movie.getMovieID() +"\n" +
                    "Musisz dodać harmonogram dla tego filmu");
        }
        else {
            JOptionPane.showMessageDialog(null, "Nie podano wszystkich danych");
        }
    }

    public boolean anyBlank(String ...strings){
        return Stream.of(strings).anyMatch(string->isBlank(string));
    }

    private void initLayout() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        panel.add(titleLabel, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 1;
        panel.add(titleTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(movieLengthLabel, constraints);

        constraints.gridx = 1;
        panel.add(movieLengthTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(descriptionLabel, constraints);

        constraints.gridx = 1;
        panel.add(descriptionTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(studioLabel, constraints);

        constraints.gridx = 1;
        panel.add(studioTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(dateLabel, constraints);

        constraints.gridx = 1;
        panel.add(dataTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(categoryLabel, constraints);

        constraints.gridx = 1;
        panel.add(categoryComboBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        panel.add(addButton, constraints);

        this.add(panel);
    }

    private void initCategoryComboBox() {
        categoriesList = categoryService.findAll();
        categoriesListStrings.add("ID/Nazwa");
        for(Category category : categoriesList) {
            categoriesListStrings.add(category.getCategoryID() + ". " + category.getName());
        }
        categoryComboBox = new JComboBox(categoriesListStrings);
    }

    private void categoryComboBoxActionListener() {
        String selectedItem = (String) categoryComboBox.getSelectedItem();
        boolean isNumeric = false;
        try {
            categoryID = Integer.parseInt(String.valueOf(selectedItem.charAt(0)));
            isNumeric = true;
        } catch(NumberFormatException ex) {
            System.out.println(ex.getMessage());
            isNumeric = false;
        }
        addButton.setEnabled(true & isNumeric);
    }
}
