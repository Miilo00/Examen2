module co.edu.poli.examen2_Soto {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires io.github.cdimascio.dotenv.java;

    opens co.edu.poli.Parcial_Vera.vista to javafx.fxml, javafx.graphics;
    opens co.edu.poli.Parcial_Vera.controlador to javafx.fxml;
    exports co.edu.poli.Parcial_Vera.controlador;
    
}
