module be.inf1.finalPacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    opens be.inf1.finalPacman to javafx.fxml;
    opens be.inf1.finalPacman.model to javafx.fxml;
    opens be.inf1.finalPacman.view to javafx.fxml;
    exports be.inf1.finalPacman;
}