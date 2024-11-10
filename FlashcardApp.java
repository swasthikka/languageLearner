import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class FlashcardApp extends JFrame {
    private List<String[]> flashcards;
    private List<String[]> vocabulary;
    private int currentIndex = 0;
    private JTextArea wordTextArea;
    private JLabel translationLabel;
    private String registeredUsername;
    private String registeredPassword;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private int score;
    private int puzzleIndex;
    private int selectedWordCount;
    private List<String[]> puzzleWords;
    private JTextField answerField;
    private JLabel wordLabel;
    private JLabel feedbackLabel;
    public FlashcardApp() {
        setTitle("Flashcard Learning Game");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the frame
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(new Color(245, 245, 245));
        add(cardPanel);
        showRegistrationPage();
    }
    private void showRegistrationPage() {
        JPanel registrationPanel = new JPanel(new GridBagLayout());
        registrationPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        registrationPanel.setBackground(new Color(240, 240, 255));  // Light blue background for the panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);  // Add padding
        gbc.weightx = 1.0;  // Allow horizontal expansion of components
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setForeground(new Color(0, 102, 204));  // Blue color for the label text
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        registrationPanel.add(usernameLabel, gbc);
        JTextField usernameField = new JTextField(15);  // Set initial width
        usernameField.setBackground(new Color(255, 255, 255));  // White background for text fields
        usernameField.setForeground(new Color(0, 0, 0));  // Black text
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));  // Blue border
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        registrationPanel.add(usernameField, gbc);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setForeground(new Color(0, 102, 204));  // Blue color for the label text
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        registrationPanel.add(passwordLabel, gbc);
        JPasswordField passwordField = new JPasswordField(15);  // Set initial width
        passwordField.setBackground(new Color(255, 255, 255));  // White background for text fields
        passwordField.setForeground(new Color(0, 0, 0));  // Black text
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));  // Blue border
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        registrationPanel.add(passwordField, gbc);
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordLabel.setForeground(new Color(0, 102, 204));  // Blue color for the label text
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        registrationPanel.add(confirmPasswordLabel, gbc);
        JPasswordField confirmPasswordField = new JPasswordField(15);  // Set initial width
        confirmPasswordField.setBackground(new Color(255, 255, 255));  // White background for text fields
        confirmPasswordField.setForeground(new Color(0, 0, 0));  // Black text
        confirmPasswordField.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));  // Blue border
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        registrationPanel.add(confirmPasswordField, gbc);
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 14));
        registerButton.setBackground(new Color(0, 102, 204));  // Blue background
        registerButton.setForeground(Color.WHITE);  // White text
        registerButton.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));  // Blue border
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        registrationPanel.add(registerButton, gbc);
        int option = JOptionPane.showConfirmDialog(this, registrationPanel, "Register", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!");
                showRegistrationPage();
                return;
            }
            if (!isValidPassword(password)) {
                JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long and contain a number.");
                showRegistrationPage();
                return;
            }
            registeredUsername = username;
            registeredPassword = password;
            showLoginPage();
        }
    }
    private void showLoginPage() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        loginPanel.setBackground(new Color(240, 240, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);
        gbc.gridy++;
        gbc.gridwidth = 1;
        loginPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx++;
        loginPanel.add(usernameField, gbc);
        gbc.gridx = 0; gbc.gridy++;
        loginPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx++;
        loginPanel.add(passwordField, gbc);
        int option = JOptionPane.showConfirmDialog(this, loginPanel, "Login", JOptionPane.OK_CANCEL_OPTION);
        handleLoginForm(option, usernameField, passwordField);
    }
    private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches(".*\\d.*");
    }
    private void handleLoginForm(int option, JTextField usernameField, JPasswordField passwordField) {
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            if (username.equals(registeredUsername) && password.equals(registeredPassword)) {
                loadCSVFile();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        }
    }
    private void loadCSVFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select CSV File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            flashcards = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values.length == 3) {
                        flashcards.add(values);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (flashcards.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No flashcards available. Closing application.");
                System.exit(0);
            } else {
                Collections.shuffle(flashcards);  // Shuffle flashcards for random order
                vocabulary = new ArrayList<>();
                showPostLoginPage();
            }
        }
    }
    private void showPostLoginPage() {
        JPanel postLoginPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        postLoginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JButton playGameButton = createStyledButton("Play Game");
        playGameButton.addActionListener(e -> showFlashcardGame());
        JButton viewListButton = createStyledButton("View List");
        viewListButton.addActionListener(e -> showVocabulary());
        JButton playPuzzleButton = createStyledButton("Play Puzzle");
        playPuzzleButton.addActionListener(e -> promptWordCountForPuzzle());
        postLoginPanel.add(playGameButton);
        postLoginPanel.add(viewListButton);
        postLoginPanel.add(playPuzzleButton);
        cardPanel.add(postLoginPanel, "PostLogin");
        cardLayout.show(cardPanel, "PostLogin");
    }
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(100, 149, 237));  // Cornflower blue for button background
        button.setForeground(Color.WHITE);  // White text color
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(200, 40));  // Set consistent button size
        button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));  // Button border
        return button;
    }
    private void promptWordCountForPuzzle() {
        String[] options = {"10", "15", "20"};
        String wordCountStr = (String) JOptionPane.showInputDialog(this,
                "Select the number of words:", "Word Count",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (wordCountStr != null) {
            selectedWordCount = Integer.parseInt(wordCountStr);
            startPuzzleGame();
        }
    }
    private void startPuzzleGame() {
        if (vocabulary.isEmpty() || vocabulary.size() < selectedWordCount) {
            JOptionPane.showMessageDialog(this, "Not enough words in the vocabulary list!");
            return;
        }
        puzzleWords = new ArrayList<>(vocabulary);
        Collections.shuffle(puzzleWords);
        puzzleWords = puzzleWords.subList(0, selectedWordCount);
        score = 0;
        puzzleIndex = 0;
        showPuzzlePage();
    }
    private void showPuzzlePage() {
        JPanel puzzlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Add padding around components
        wordLabel = new JLabel("", SwingConstants.CENTER);
        wordLabel.setFont(getFontWithUnicodeSupport(24));  // Adjust font size for better visibility
        wordLabel.setForeground(new Color(0x2A2A2A));  // Dark gray color for the word text
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // Span across 2 columns
        puzzlePanel.add(wordLabel, gbc);
        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(getFontWithUnicodeSupport(16));
        feedbackLabel.setForeground(new Color(0xFF4B5C));  // Red for feedback (error)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;  // Span across 2 columns
        puzzlePanel.add(feedbackLabel, gbc);
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(0xF4F4F4));  // Light background for the input panel
        JLabel translationLabel = new JLabel("Your translation:");
        translationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        translationLabel.setForeground(new Color(0x2A2A2A));  // Dark gray color for label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        inputPanel.add(translationLabel, gbc);
        answerField = new JTextField(15);  // Limit the width of the text field
        answerField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(answerField, gbc);
        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(0x4CAF50));  // Green background for the button
        submitButton.setForeground(Color.WHITE);  // White text
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setFocusPainted(false);  // Remove the border when focused
        submitButton.setPreferredSize(new Dimension(120, 40));  // Set a standard button size
        submitButton.addActionListener(e -> checkAnswer());
        gbc.gridx = 2;
        gbc.gridy = 0;
        inputPanel.add(submitButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        puzzlePanel.add(inputPanel, gbc);
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(0xFF4B5C));  // Red background for the back button
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "PostLogin"));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        puzzlePanel.add(backButton, gbc);
        cardPanel.add(puzzlePanel, "PuzzleGame");
        cardLayout.show(cardPanel, "PuzzleGame");
        displayNextPuzzleWord();
    }
    private void displayNextPuzzleWord() {
        if (puzzleIndex < puzzleWords.size()) {
            String[] word = puzzleWords.get(puzzleIndex);
            wordLabel.setText(word[0]); // Show word in vocabulary
            answerField.setText("");
            feedbackLabel.setText("");
            answerField.requestFocus();
        } else {
            endPuzzleGame();
        }
    }
    private void checkAnswer() {
        String[] word = puzzleWords.get(puzzleIndex);
        String correctAnswer = word[1];
        String userAnswer = answerField.getText().trim();
        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            feedbackLabel.setText("Correct!");
            score++;
        } else {
            feedbackLabel.setText("Wrong! Correct answer: " + correctAnswer);
        }
        puzzleIndex++;
        Timer timer = new Timer(1000, e -> displayNextPuzzleWord()); // Brief delay to show feedback
        timer.setRepeats(false);
        timer.start();
    }
    private void endPuzzleGame() {
        JOptionPane.showMessageDialog(this, "Puzzle completed! Your score: " + score + "/" + selectedWordCount);
        cardLayout.show(cardPanel, "PostLogin");
    }
    private void showFlashcardGame() {
        setTitle("Flashcard Game");
        wordTextArea = new JTextArea();
        wordTextArea.setEditable(false);
        wordTextArea.setFont(getFontWithUnicodeSupport(48));
        wordTextArea.setWrapStyleWord(true);
        wordTextArea.setLineWrap(true);
        wordTextArea.setBackground(new Color(255, 255, 255)); // Light background for text area
        wordTextArea.setForeground(new Color(0, 0, 0)); // Dark text color for readability
        wordTextArea.setPreferredSize(new Dimension(300, 100));
        translationLabel = new JLabel("", SwingConstants.CENTER);
        translationLabel.setFont(getFontWithUnicodeSupport(16));
        translationLabel.setForeground(new Color(100, 100, 100)); // Slightly lighter text
        translationLabel.setPreferredSize(new Dimension(300, 50));
        JButton knowButton = new JButton("I know this word");
        JButton dontKnowButton = new JButton("I don't know this word");
        JButton Next = new JButton("Next");
        JButton backButton = new JButton("Back");
        styleButton(knowButton);
        styleButton(dontKnowButton);
        styleButton(Next);
        styleButton(backButton);
        knowButton.addActionListener(e -> showNextFlashcard());
        dontKnowButton.addActionListener(e -> addWordToVocabulary());
        Next.addActionListener(e -> showNextFlashcard());
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "PostLogin"));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(knowButton);
        buttonPanel.add(dontKnowButton);
        buttonPanel.add(Next);
        buttonPanel.add(backButton);
        JPanel flashcardPanel = new JPanel(new BorderLayout());
        flashcardPanel.setBackground(new Color(240, 240, 240)); // Light gray background
        flashcardPanel.add(wordTextArea, BorderLayout.NORTH);
        flashcardPanel.add(translationLabel, BorderLayout.CENTER);
        flashcardPanel.add(buttonPanel, BorderLayout.SOUTH);
        JPanel centeredPanel = new JPanel(new GridBagLayout());
        centeredPanel.setBackground(new Color(240, 240, 240));
        centeredPanel.add(flashcardPanel);
        cardPanel.add(centeredPanel, "FlashcardGame");
        cardLayout.show(cardPanel, "FlashcardGame");
        currentIndex = 0;  // Reset index for shuffled order
        showNextFlashcard();
    }
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180)); // Steel blue background
        button.setForeground(Color.WHITE); // White text
        button.setPreferredSize(new Dimension(180, 40));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20)); // Adds padding inside the button
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    private void showNextFlashcard() {
        if (currentIndex < flashcards.size()) {
            String[] flashcard = flashcards.get(currentIndex);
            wordTextArea.setText(flashcard[0]);
            translationLabel.setText("<html><center>Translation: " + flashcard[1] + " (" + flashcard[2] + ")</center></html>");
            currentIndex++;
        } else {
            JOptionPane.showMessageDialog(this, "You have finished all flashcards!");
            currentIndex = 0;  // Restart if needed
        }
    }
    private void addWordToVocabulary() {
        if (currentIndex > 0) {
            String[] flashcard = flashcards.get(currentIndex - 1);
            vocabulary.add(new String[]{flashcard[1], flashcard[0]});
        }
    }
    private JFrame vocabFrame;
    private JPanel vocabPanel;
    private void showVocabulary() {
        JPanel vocabularyPanel = new JPanel();
        vocabularyPanel.setLayout(new GridBagLayout());  // Use GridBagLayout for flexible layout
        GridBagConstraints gbc = new GridBagConstraints();  // For controlling the layout positioning
        JLabel titleLabel = new JLabel("Vocabulary List");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0x2A2A2A));  // Dark gray color
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 20, 0);  // Space around the title
        vocabularyPanel.add(titleLabel, gbc);
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));  // Stack items vertically
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setPreferredSize(new Dimension(400, 300));  // Set the preferred size for scrolling area
        for (int i = 0; i < vocabulary.size(); i++) {
            final int index = i;  // Declare the loop variable as final
            String[] word = vocabulary.get(i);
            JPanel wordPanel = new JPanel();
            wordPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            wordPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));  // Add padding to each word entry
            String wordText = word[0] + " - " + word[1];  // Display the word and its translation
            JLabel wordLabel = new JLabel(wordText);
            wordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            wordLabel.setPreferredSize(new Dimension(250, 25));  // Set preferred size for label
            wordPanel.add(wordLabel);
            JButton removeButton = new JButton("Remove");
            removeButton.setBackground(new Color(0xFF4B5C));  // Red background
            removeButton.setForeground(Color.WHITE);  // White text
            removeButton.setFont(new Font("Arial", Font.PLAIN, 14));
            removeButton.setFocusPainted(false);  // Remove the border when focused
            removeButton.setPreferredSize(new Dimension(100, 30));  // Set button size
            removeButton.addActionListener(e -> {
                vocabulary.remove(index);
                showVocabulary(); // Refresh the vocabulary list
            });
            wordPanel.add(removeButton);
            listPanel.add(wordPanel);
        }
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        vocabularyPanel.add(scrollPane, gbc);
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(0x4CAF50));  // Green background
        backButton.setForeground(Color.WHITE);  // White text
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "PostLogin"));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        vocabularyPanel.add(backButton, gbc);
        cardPanel.add(vocabularyPanel, "VocabularyList");
        cardLayout.show(cardPanel, "VocabularyList");
    }
    private Font getFontWithUnicodeSupport(int size) {
        try {
            return new Font("Nirmala UI", Font.PLAIN, size);
        } catch (Exception e) {
            return new Font("Mangal", Font.PLAIN, size);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlashcardApp app = new FlashcardApp();
            app.setVisible(true);
        });
    }
}