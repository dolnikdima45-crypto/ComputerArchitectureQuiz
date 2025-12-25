package ua.student.archquiz.computerarchitecturequiz.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    @Test
    @DisplayName("Перевірка правильної ініціалізації питання")
    void testQuestionInitialization() {
        // Підготовка даних
        String text = "Що таке CPU?";
        String[] options = {"Процесор", "Пам'ять", "Шина"};
        int correctIndex = 0;

        // Дія
        Question question = new Question(text, options, correctIndex);

        // Перевірка
        assertEquals(text, question.getText(), "Текст питання має співпадати");
        assertArrayEquals(options, question.getOptions(), "Варіанти відповідей мають співпадати");
        assertEquals(correctIndex, question.getCorrectOptionIndex(), "Індекс правильної відповіді має бути вірним");

        // Перевірка початкового стану (відповіді ще немає)
        assertFalse(question.isAnswered(), "Нове питання не повинно мати відповіді");
        assertEquals(-1, question.getUserAnswer(), "Індекс відповіді користувача має бути -1 за замовчуванням");
    }

    @Test
    @DisplayName("Перевірка встановлення відповіді користувача")
    void testSetUserAnswer() {
        Question question = new Question("Test", new String[]{"A", "B"}, 0);

        // Користувач вибирає варіант 1
        question.setUserAnswer(1);

        assertTrue(question.isAnswered(), "Питання має вважатися таким, на яке дали відповідь");
        assertEquals(1, question.getUserAnswer(), "Збережений індекс має бути 1");
    }

    @Test
    @DisplayName("Перевірка зміни відповіді")
    void testChangeUserAnswer() {
        Question question = new Question("Test", new String[]{"A", "B"}, 0);

        // Спочатку вибрали 0
        question.setUserAnswer(0);
        assertEquals(0, question.getUserAnswer());

        // Потім передумали і вибрали 1
        question.setUserAnswer(1);
        assertEquals(1, question.getUserAnswer(), "Відповідь повинна оновитися на нову");
    }
}