package ua.student.archquiz.computerarchitecturequiz.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ua.student.archquiz.computerarchitecturequiz.model.Question;

public class QuizController {

    @FXML private VBox questionsContainer;
    @FXML private Button nextButton;
    @FXML private Button backButton;

    private Question[] allQuestions;
    private int currentPage = 0;
    private final int QUESTIONS_PER_PAGE = 3;

    // Змінна для збереження часу початку тесту
    private long startTime;

    @FXML
    public void initialize() {
        // Засікаємо час початку
        startTime = System.currentTimeMillis();

        allQuestions = new Question[] {
                // --- СТОРІНКА 1 ---
                new Question("Який блок процесора виконує арифметичні обчислення?",
                        new String[]{"ALU (Арифметико-логічний)", "CU (Пристрій керування)", "Кеш-пам'ять"}, 0),

                new Question("У чому вимірюється тактова частота процесора?",
                        new String[]{"У бітах", "У Герцах (Hz)", "У Вольтах"}, 1),

                new Question("Яка пам'ять є найшвидшою?",
                        new String[]{"Оперативна (RAM)", "Жорсткий диск (HDD)", "Регістри процесора"}, 2),

                // --- СТОРІНКА 2 ---
                new Question("Яка головна особливість RAM (оперативної пам'яті)?",
                        new String[]{"Вона зберігає дані постійно", "Вона енергозалежна (дані зникають без струму)", "Це лише для ігор"}, 1),

                new Question("Що таке системна шина?",
                        new String[]{"Набір проводів для передачі даних", "Програма в BIOS", "Роз'єм для живлення"}, 0),

                new Question("Архітектура фон Неймана передбачає...",
                        new String[]{"Спільну пам'ять для програм і даних", "Роздільну пам'ять", "Відсутність пам'яті"}, 0)
        };

        showPage(0);
    }

    private void showPage(int pageIndex) {
        questionsContainer.getChildren().clear();
        currentPage = pageIndex;

        int start = pageIndex * QUESTIONS_PER_PAGE;
        int end = Math.min(start + QUESTIONS_PER_PAGE, allQuestions.length);

        for (int i = start; i < end; i++) {
            createQuestionCard(allQuestions[i], i + 1);
        }

        backButton.setVisible(currentPage > 0);

        if (end == allQuestions.length) {
            nextButton.setText("Завершити тест");
        } else {
            nextButton.setText("Далі >");
        }
    }

    private void createQuestionCard(Question q, int number) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.getStyleClass().add("question-card");

        Label qLabel = new Label(number + ". " + q.getText());
        qLabel.getStyleClass().add("question-text");
        qLabel.setWrapText(true);
        card.getChildren().add(qLabel);

        ToggleGroup group = new ToggleGroup();

        String[] options = q.getOptions();
        for (int j = 0; j < options.length; j++) {
            RadioButton rb = new RadioButton(options[j]);
            rb.setToggleGroup(group);
            rb.getStyleClass().add("radio-button");
            rb.setWrapText(true);

            if (q.getUserAnswer() == j) {
                rb.setSelected(true);
            }

            int finalOptionIndex = j;
            rb.setOnAction(e -> q.setUserAnswer(finalOptionIndex));

            card.getChildren().add(rb);
        }
        questionsContainer.getChildren().add(card);
    }

    @FXML
    private void onNext() {
        int start = currentPage * QUESTIONS_PER_PAGE;
        int end = Math.min(start + QUESTIONS_PER_PAGE, allQuestions.length);

        for (int i = start; i < end; i++) {
            if (!allQuestions[i].isAnswered()) {
                showAlert("Увага", "Будь ласка, дайте відповідь на всі питання перед продовженням!");
                return;
            }
        }

        if (end == allQuestions.length) {
            finishTest();
        } else {
            showPage(currentPage + 1);
        }
    }

    @FXML
    private void onBack() {
        if (currentPage > 0) {
            showPage(currentPage - 1);
        }
    }

    private void finishTest() {
        // 1. Рахуємо час
        long endTime = System.currentTimeMillis();
        long durationMillis = endTime - startTime;
        long seconds = (durationMillis / 1000) % 60;
        long minutes = (durationMillis / (1000 * 60)) % 60;
        String timeString = String.format("%02d хв %02d сек", minutes, seconds);

        // 2. Рахуємо бали та шукаємо помилки
        int score = 0;
        StringBuilder errorReport = new StringBuilder();

        for (int i = 0; i < allQuestions.length; i++) {
            Question q = allQuestions[i];
            if (q.getUserAnswer() == q.getCorrectOptionIndex()) {
                score++;
            } else {
                // Чистий текст без ліній і значків
                errorReport.append("\n"); // Просто відступ
                errorReport.append("Питання ").append(i + 1).append(": ").append(q.getText());
                errorReport.append("\nВаша відповідь: ").append(q.getOptions()[q.getUserAnswer()]);
                errorReport.append("\nПравильно: ").append(q.getOptions()[q.getCorrectOptionIndex()]);
                errorReport.append("\n"); // Відступ між помилками
            }
        }

        // 3. Формуємо фінальний текст (чистий)
        StringBuilder resultText = new StringBuilder();
        resultText.append("ВАШ РЕЗУЛЬТАТ: ").append(score).append(" з ").append(allQuestions.length).append("\n");
        resultText.append("ЧАС ВИКОНАННЯ: ").append(timeString).append("\n\n");

        if (score == allQuestions.length) {
            resultText.append("Вітаємо! Ви відповіли правильно на всі питання!\nВідмінний результат!");
        } else {
            resultText.append("РОБОТА НАД ПОМИЛКАМИ:");
            resultText.append(errorReport);
        }

        // 4. Показуємо результат
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Результат тестування");
        alert.setHeaderText("Тест завершено!");

        TextArea textArea = new TextArea(resultText.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        alert.getDialogPane().setContent(textArea);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(500, 400);

        alert.showAndWait();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}