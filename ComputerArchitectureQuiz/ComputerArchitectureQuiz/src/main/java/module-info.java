module ua.student.archquiz.computerarchitecturequiz {
    requires javafx.controls;
    requires javafx.fxml;


    requires org.kordamp.bootstrapfx.core;


    opens ua.student.archquiz.computerarchitecturequiz to javafx.fxml;
    opens ua.student.archquiz.computerarchitecturequiz.controller to javafx.fxml;

    exports ua.student.archquiz.computerarchitecturequiz;
}