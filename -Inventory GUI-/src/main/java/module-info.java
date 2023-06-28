module waite.c482 {
    requires javafx.controls;
    requires javafx.fxml;


    opens waite.c482 to javafx.fxml;
    opens waite.c482.model to javafx.fxml;

    exports waite.c482;
    exports waite.c482.model;
    
    opens waite.c482.controller to javafx.fxml;
}