import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
public class LibraryAnalyticsDashboardGUI extends JFrame {

    private LibraryManagerLogic dataManagerForTheGUI;

    private JTextArea displayArea;
    //BEFORE OPENING THE GUI READ THE README.TXT
    //buttons
    private JButton loadFilesButton, generateReportButton, saveReportButton;
    private JButton showGenresButton, oclcSearchButton, genreSearchButton;
    private JButton bottomRightBooksButton, bottomRightDVDsButton, bottomRightCDsButton;
    private JRadioButton booksRadio, dvdsRadio, cdsRadio;
    private JTextField oclcField, genreField;

    //NEW EXPORT BUTTONS ASSIGNMENT 2

    private JButton exportBooksJSONButton = new JButton("Books -> JSON");
    private JButton exportDVDsJSONButton  = new JButton("DVDs  → JSON");
    private JButton exportCDsJSONButton   = new JButton("CDs   → JSON");
    //Assignment C XML generation button
    private JButton exportXMLButton = new JButton("Book,CD,DVD -> XML");

    //this sets up the gui and loads LibraryManager so these 2 classes can interact
    public LibraryAnalyticsDashboardGUI() {
        dataManagerForTheGUI = new LibraryManagerLogic();
        setupGUI();
    }

    private void setupGUI() {
        setTitle("Library Analytics Dashboard");
        setSize(900, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // center
        displayArea = new JTextArea("Please load the following:\n" +
                "Book, DVD, CD, and Loan Record by Order");
        displayArea.setBackground(Color.BLACK);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Courier New", Font.BOLD, 15));
        displayArea.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // control pannels
        JPanel controlsPanel = new JPanel();

        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        controlsPanel.setPreferredSize(new Dimension(400, 0));
        controlsPanel.setBorder(BorderFactory.createTitledBorder(" Buttons"));

        // FILE panel
        loadFilesButton = new JButton("Load Files");
        generateReportButton = new JButton("Generate Report");
        saveReportButton = new JButton("Save Report");
        JPanel filePanel = new JPanel(new GridLayout(3, 1, 4, 4));
        filePanel.setBorder(BorderFactory.createTitledBorder("Files & Reports"));
        filePanel.add(loadFilesButton);
        filePanel.add(generateReportButton);
        filePanel.add(saveReportButton);


        // genre panel
        booksRadio = new JRadioButton("Books", true);
        dvdsRadio  = new JRadioButton("DVDs");
        cdsRadio   = new JRadioButton("CDs");
        ButtonGroup group = new ButtonGroup(); //This is the buttonGroup
        group.add(booksRadio);
        group.add(dvdsRadio);
        group.add(cdsRadio);
        showGenresButton = new JButton("Show Genres");

        JPanel genrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genrePanel.setLayout(new BoxLayout(genrePanel, BoxLayout.X_AXIS));
        genrePanel.setBorder(BorderFactory.createTitledBorder("Genre Display"));
        genrePanel.add(booksRadio);
        genrePanel.add(dvdsRadio);
        genrePanel.add(cdsRadio);
        genrePanel.add(Box.createVerticalStrut(4));
        genrePanel.add(showGenresButton);

        // SEARCH panel (OCLC + Genre)
        oclcField = new JTextField(10);
        oclcSearchButton = new JButton("Search for OCLC");
        JPanel oclcPanel = new JPanel();
        oclcPanel.add(new JLabel("OCLC:"));
        oclcPanel.add(oclcField);
        oclcPanel.add(oclcSearchButton);

        genreField = new JTextField(10);
        genreSearchButton = new JButton("Search for a Genre");
        JPanel genreSearchPanel = new JPanel();
        genreSearchPanel.add(new JLabel("Genre:"));
        genreSearchPanel.add(genreField);
        genreSearchPanel.add(genreSearchButton);
        //This provides the layout of the search panel
        JPanel searchPanel = new JPanel(new GridLayout(1, 1, 4, 4));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search"));
        searchPanel.add(oclcPanel);
        searchPanel.add(genreSearchPanel);

        // bottomRight panel
        bottomRightBooksButton = new JButton("Top 10 Book");
        bottomRightDVDsButton  = new JButton("Top 10 DVDs");
        bottomRightCDsButton   = new JButton("Top 10 CDs");
        JPanel topPanel = new JPanel(new GridLayout(2, 2, 4, 4));
        topPanel.setBorder(BorderFactory.createTitledBorder("Loan"));
        topPanel.add(new JLabel("Top 10 Items:"));
        topPanel.add(bottomRightBooksButton);
        topPanel.add(bottomRightDVDsButton);
        topPanel.add(bottomRightCDsButton);
        controlsPanel.add(filePanel);
        controlsPanel.add(Box.createVerticalStrut(6));
        controlsPanel.add(genrePanel);
        controlsPanel.add(Box.createVerticalStrut(6));
        controlsPanel.add(searchPanel);
        controlsPanel.add(Box.createVerticalStrut(6));
        controlsPanel.add(topPanel);
        //-----------------------------
        //Assignment 2 - Export JSON
        //----------------------------

        JPanel exportPanel = new JPanel(new GridLayout(4,1,4,4));
        exportPanel.add(new JLabel("Export"));
        exportPanel.setBorder(BorderFactory.createTitledBorder("Export"));
        exportPanel.add(exportBooksJSONButton);
        exportPanel.add(exportDVDsJSONButton);
        exportPanel.add(exportCDsJSONButton);
        exportPanel.add(exportXMLButton); //Assignment C
        controlsPanel.add(Box.createVerticalStrut(6));
        controlsPanel.add(exportPanel);

        //


        // STATUS (BOTTOM)
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(BorderFactory.createEtchedBorder());
        statusPanel.add(new JLabel("Please load library files to begin."));

        // this adds the action listnersq
        // also making the buttons disabled so it is required to press the load button
        //Using the setEnabled false method this makes the buttons turned off
        //So it is required to press the load button
        loadFilesButton.addActionListener(e -> loadLibraryFiles());
        generateReportButton.addActionListener(e -> generateCatalogueReport());
        saveReportButton.addActionListener(e -> saveReportToFile());
        showGenresButton.addActionListener(e -> showGenres());
        oclcSearchButton.addActionListener(e -> searchByOCLC());
        genreSearchButton.addActionListener(e -> searchByGenre());
        bottomRightBooksButton.addActionListener(e -> showTopBooksOnLoan());
        bottomRightDVDsButton.addActionListener(e -> showTopDVDsOnLoan());
        bottomRightCDsButton.addActionListener(e -> showTopCDsOnLoan());
        showGenresButton.setEnabled(false);
        saveReportButton.setEnabled(false);
        bottomRightCDsButton.setEnabled(false);
        oclcSearchButton.setEnabled(false);
        genreSearchButton.setEnabled(false);
        bottomRightBooksButton.setEnabled(false);
        bottomRightDVDsButton.setEnabled(false);
        generateReportButton.setEnabled(false);
        exportBooksJSONButton.setEnabled(false);
        exportDVDsJSONButton.setEnabled(false);
        exportCDsJSONButton.setEnabled(false);
        exportXMLButton.setEnabled(false);
        // Layout of the frame
        setLayout(new BorderLayout(8, 7));
        add(scrollPane, BorderLayout.CENTER);
        add(controlsPanel, BorderLayout.EAST);
        add(statusPanel, BorderLayout.SOUTH);
        setVisible(true);

        exportBooksJSONButton.addActionListener(e -> exportBooksToJSON());
        exportDVDsJSONButton.addActionListener(e -> exportDVDsToJSON());
        exportCDsJSONButton.addActionListener(e ->exportCDsToJSON());
        exportXMLButton.addActionListener(e -> exportLibraryToXML());
    }

    //Export Methods for ASSIGNEMNT 2
    //Exports the entire book cataloge into a JSON file chosen by the user
    //calls the toJSONBooks() from LibraryManagerLogic to generate the JSON string,
    //then saves it into saveJSONToFile()
    private void exportBooksToJSON()
    {
        String savePath = chooseSaveFile("Save Books JSON As", "books.json");
        if(savePath == null) return;
        try {
            String json = dataManagerForTheGUI.toJSONBooks();
            dataManagerForTheGUI.saveJSONToFile(json, savePath);
            displayArea.setText("Books exported to JSON:\n" + savePath
                    + "\n\n---Final Output---\n"
                    + json);
            JOptionPane.showMessageDialog(this,
                    "Books JSON saved to\n" + savePath, "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Exports the entire DVD catalogue to a JSON file chosen by the user.
     * Calls toJSONDVDs() from LibraryManagerLogic to generate the JSON string,
     * then saves it using saveJSONToFile() and previews the output in the display area.
     */
    private void exportDVDsToJSON()
    {
        String savePath = chooseSaveFile("Save DVDs JSON As", "dvds.json");
        if (savePath == null) return;
        try {
            String json = dataManagerForTheGUI.toJSONDVDs();
            dataManagerForTheGUI.saveJSONToFile(json, savePath);
            displayArea.setText("DVDs exported to JSON:\n" + savePath
                    + "\n\n--- Final Output ---\n"
                    + json);
            JOptionPane.showMessageDialog(this,
                    "DVDs JSON saved to:\n" + savePath, "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Exports the entire CD catalogue to a JSON file chosen by the user.
     * Calls toJSONCDs() from LibraryManagerLogic to generate the JSON string,
     * then saves it using saveJSONToFile() and previews the output in the display area.
     */
    private void exportCDsToJSON()
    {
        String savePath = chooseSaveFile("Save CDs JSON As", "cds.json");
        if (savePath == null) return;
        try {
            String json = dataManagerForTheGUI.toJSONCDs();
            dataManagerForTheGUI.saveJSONToFile(json, savePath);
            displayArea.setText("CDs exported to JSON:\n" + savePath
                    + "\n\n--- Final Output ---\n"
                    + json);
            JOptionPane.showMessageDialog(this,
                    "CDs JSON saved to:\n" + savePath, "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    //Assignment Part C
    private void exportLibraryToXML()
    {
        String savePath = chooseSaveFile("Save Library XML As", "library_By_Genre.xml");
        if (savePath == null) return;
        try{
            String xml = dataManagerForTheGUI.generateXMLByGenre();
            dataManagerForTheGUI.saveXMLToFile(xml, savePath);
            displayArea.setText("Library XML Exported: \n" + savePath
                    + xml);
            JOptionPane.showMessageDialog(this,
                    "Library XML saved to:\n" + savePath, "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch(IOException ex){
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }



    // Logic methods

    /**
     * Opens a JFileChooser for the user to pick a save location.

     *
     * @param title           the dialog window title
     * @param defaultFileName the suggested file name shown in the dialog
     * @return the absolute path chosen by the user, or null if cancelled
     */
    private String chooseSaveFile(String title, String defaultFileName) {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle(title);
        fc.setSelectedFile(new File(defaultFileName));
        int result = fc.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    private String chooseFile(String title) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(title);

        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        } else {
            JOptionPane.showMessageDialog(this,
                    "File selection cancelled. Loading aborted.", //gives a message using another window
                    "Cancelled",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }




    private void loadLibraryFiles() {
        dataManagerForTheGUI.reset();

        try {
            // Load Books
            String booksFile = chooseFile("Select Books Catalogue File");
            if (booksFile == null) return;
            dataManagerForTheGUI.loadBooks(booksFile);

            // Load DVDs
            String dvdsFile = chooseFile("Select DVD Catalogue File");
            if (dvdsFile == null) return;
            dataManagerForTheGUI.loadDVDs(dvdsFile);

            // Load CDs
            String cdsFile = chooseFile("Select CD Catalogue File");
            if (cdsFile == null) return;
            dataManagerForTheGUI.loadCDs(cdsFile);

            // Load Loan Records
            String loansFile = chooseFile("Select Loan Records File");
            if (loansFile == null) return;
            dataManagerForTheGUI.loadLoanRecords(loansFile);

            //  show all of the content
            enableButtons();
            displayArea.setText("___ FILES LOADED ___\n\n");
            displayArea.append("Books loaded: " + dataManagerForTheGUI.getBookCount() + "\n");
            displayArea.append("DVDs loaded: " + dataManagerForTheGUI.getDVDCount() + "\n");
            displayArea.append("CDs loaded: " + dataManagerForTheGUI.getCDCount() + "\n");
            displayArea.append("Loan records loaded: " + dataManagerForTheGUI.getLoanCount() + "\n\n");
            displayArea.append("Total unique items: " + dataManagerForTheGUI.getTotalCount());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error loading files: " + ex.getMessage(),
                    "Something Went Wrong",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateCatalogueReport() {
        displayArea.setText(dataManagerForTheGUI.generateCatalogueReport());
    }

    //This just saves the report by creating a new txt file called Library Catalouge Report
    private void saveReportToFile() {
        String filename = "Library_Catalogue_Report.txt";
        try {
            dataManagerForTheGUI.saveReportToFile(filename);
            JOptionPane.showMessageDialog(this, "Report saved to: " + filename, "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //This method shows the genre by using the arraylist and using the
    //data manager to get the data form the library manager logic
    private void showGenres() {
        displayArea.setText("");
        ArrayList<String> genres = null;
        String label = "";
        if (booksRadio.isSelected()) {
            genres = dataManagerForTheGUI.getBookGenres();
            label = "BOOK GENRE";
        } else if (dvdsRadio.isSelected()) {
            genres = dataManagerForTheGUI.getDVDGenres();
            label = "DVD GENRE";
        } else if (cdsRadio.isSelected()) {
            displayArea.append("");
            if (dataManagerForTheGUI.isCDCatalogueEmpty()) {
                displayArea.append("No CD data loaded yet");
                return;
            } else {
                genres = dataManagerForTheGUI.getCDGenres();
                label = "CD GENRE";
            }
        }
        displayArea.append("--- " + label + " ---\n\n");
        if (genres != null) {
            for (String g : genres) displayArea.append(g + "\n");
            displayArea.append("\nTotal: " + genres.size() + " unique genres");
        }
    }
    //This method shows the OCLC
    private void searchByOCLC() {
        String oclc = oclcField.getText().trim();
        if (oclc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an OCLC number", "Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        displayArea.setText("--- OCLC SEARCH RESULTS ---\n\n");
        displayArea.append("Search for OCLC: " + oclc + "\n\n");

        String result = dataManagerForTheGUI.searchByOCLC(oclc);
        displayArea.append(result != null ? result : "No OCLC Record Was Found!");
    }


    private void searchByGenre() {
        String genre = genreField.getText().trim();
        String oclc = oclcField.getText().trim();
        if (genre.isEmpty()) {
            //Using the showMessageDialog this gives a pop up
            JOptionPane.showMessageDialog(this, "Please enter genre", "Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        displayArea.setText("___ GENRE SEARCH RESULTS ___\n");
        displayArea.append("Searching for genre: " + genre + "\n\n");
        // Book, Using an arrayList this helps in getting the required data
        ArrayList<String> books = dataManagerForTheGUI.searchBooksByGenre(genre);

        displayArea.append("___ BOOKS ___\n");
        if (books.isEmpty())
            displayArea.append("No books found in this genre\n");
        else {
            for (String title : books) displayArea.append(title + "\n");
            displayArea.append("(" + books.size() + " books found)\n");
        }
        // DVDs
        ArrayList<String> dvds = dataManagerForTheGUI.searchDVDsByGenre(genre);
        displayArea.append("\n--- DVDs ---\n");
        if (dvds.isEmpty())
            displayArea.append("No DVDs found in this genre\n");
        else {
            for (String title : dvds) displayArea.append(title + "\n");
            displayArea.append("(" + dvds.size() + " DVDs found)\n");
        }
        // CDs
        displayArea.append("\n--- CDs ---\n");
        if (dataManagerForTheGUI.isCDCatalogueEmpty()) {
            displayArea.append("No CD data loaded yet\n");
        } else {
            ArrayList<String> cds = dataManagerForTheGUI.searchCDsByGenre(genre);
            if (cds.isEmpty())
                displayArea.append("No CDs found in this genre\n");
            else {
                for (String title : cds) displayArea.append("Title: " + title + "\n");

                displayArea.append("(" + cds.size() + " CDs found)\n");
            }
        }
    }
    //Using a for loop this displays each of the top ten loaned items
    //Applies for to the following Methods
    ///This part was just copy and paste of the showTopBooksOnLoan method
    private void showTopBooksOnLoan() {
        displayArea.setText("___ TOP 10 BOOKS ON LOAN ___\n\n");
        ArrayList<String> top = dataManagerForTheGUI.getTopTenBooks();
        if (top.isEmpty())
            displayArea.append("Nothing is there....\n");
        else
            for (int i = 0; i < top.size(); i++)
                displayArea.append((i+1) + ". " + top.get(i) + "\n");
    }

    private void showTopDVDsOnLoan() {
        displayArea.setText("___ TOP 10 DVDs ON LOAN ___\n\n");
        ArrayList<String> top = dataManagerForTheGUI.getTopTenDVDs();
        if (top.isEmpty())
            displayArea.append("Nothing is there....\n");
        else
            for (int i = 0; i < top.size(); i++)
                displayArea.append((i+1) + ". " + top.get(i) + "\n");
    }

    private void showTopCDsOnLoan() {
        displayArea.setText("___ TOP 10 CDs ON LOAN ___\n\n");
        ArrayList<String> top = dataManagerForTheGUI.getTopTenCDs();
        if (top.isEmpty())
            displayArea.append("Nothing is there.....\n");
        else
            for (int i = 0; i < top.size(); i++)
                displayArea.append((i+1) + ". " + top.get(i) + "\n");
    }

    //makes the buttons usable and gives freedom to the user
    private void enableButtons() {
        generateReportButton.setEnabled(true);
        saveReportButton.setEnabled(true);
        showGenresButton.setEnabled(true);
        oclcSearchButton.setEnabled(true);
        genreSearchButton.setEnabled(true);
        bottomRightBooksButton.setEnabled(true);
        bottomRightDVDsButton.setEnabled(true);
        bottomRightCDsButton.setEnabled(true);
        exportBooksJSONButton.setEnabled(true);
        exportDVDsJSONButton.setEnabled(true);
        exportCDsJSONButton.setEnabled(true);
        exportXMLButton.setEnabled(true);
    }

    public static void main(String[] args) {
        LibraryAnalyticsDashboardGUI lADG = new LibraryAnalyticsDashboardGUI();
    }
}