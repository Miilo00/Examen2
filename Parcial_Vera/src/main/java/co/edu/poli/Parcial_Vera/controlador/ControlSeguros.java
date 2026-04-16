package co.edu.poli.Parcial_Vera.controlador;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import co.edu.poli.Parcial_Vera.modelo.Asegurado;
import co.edu.poli.Parcial_Vera.modelo.Seguro;
import co.edu.poli.Parcial_Vera.modelo.SeguroDeVehiculo;
import co.edu.poli.Parcial_Vera.modelo.SeguroDeVida;
import co.edu.poli.Parcial_Vera.servicios.DAOSeguro;
import co.edu.poli.Parcial_Vera.servicios.DAOAsegurado;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ControlSeguros {

    @FXML
    private Button btnConsultar;

    @FXML
    private TextField txtNumeroConsulta;

    @FXML
    private TextArea txtResultado;

    @FXML
    private TextField txtNumero;

    @FXML
    private DatePicker dateFecha;

    @FXML
    private ComboBox<Asegurado> cmbAsegurado;

    @FXML
    private RadioButton rbVehiculo;

    @FXML
    private RadioButton rbVida;

    @FXML
    private ToggleGroup tipoSeguro;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtBeneficiario;

    @FXML
    private Button btnCrear;

    private DAOSeguro daoSeguro;
    private DAOAsegurado daoAsegurado;

    @FXML
    private void initialize() {

        daoSeguro = new DAOSeguro();
        daoAsegurado = new DAOAsegurado();

        dateFecha.setValue(LocalDate.now());

        try {
            List<Asegurado> lista = daoAsegurado.readall();
            cmbAsegurado.getItems().setAll(lista);
        } catch (Exception e) {
            mostrarAlerta(e.getMessage());
        }

        txtNumeroConsulta.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarSoloNumeros(txtNumeroConsulta);
        });

        txtNumero.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validarSoloNumeros(txtNumero);
        });
    }

    @FXML
    private void pressConsulta(ActionEvent event) {

        txtResultado.setText("");

        String numero = txtNumeroConsulta.getText().trim();

        if (numero.isEmpty()) {
            mostrarAlerta("Ingrese número de seguro");
            return;
        }

        try {
            Seguro s = daoSeguro.readone(numero);

            if (s != null) {

                StringBuilder resultado = new StringBuilder();

                resultado.append("Número: ").append(s.getNumero()).append("\n");
                resultado.append("Fecha expedición: ").append(s.getFechaExpedicion()).append("\n");
                resultado.append("Estado: ").append(s.getEstado()).append("\n");
                resultado.append("Asegurado: ").append(s.getAsegurado()).append("\n");

                if (s instanceof SeguroDeVehiculo) {
                    SeguroDeVehiculo sv = (SeguroDeVehiculo) s;
                    resultado.append("Marca: ").append(sv.getMarca()).append("\n");
                }

                if (s instanceof SeguroDeVida) {
                    SeguroDeVida sv = (SeguroDeVida) s;
                    resultado.append("Beneficiario: ").append(sv.getBeneficiario()).append("\n");
                }

                txtResultado.setText(resultado.toString());

            } else {
                mostrarAlerta("No existe el seguro");
            }

        } catch (Exception e) {
            mostrarAlerta(e.getMessage());
        }
    }

    @FXML
    private void pressCreacion(ActionEvent event) {

        String numero = txtNumero.getText().trim();

        if (numero.isEmpty()) {
            mostrarAlerta("Ingrese el número de seguro");
            return;
        }

        if (dateFecha.getValue() == null) {
            mostrarAlerta("Seleccione fecha");
            return;
        }

        Asegurado asegurado = cmbAsegurado.getValue();

        if (asegurado == null) {
            mostrarAlerta("Seleccione asegurado");
            return;
        }

        String fechaExp = dateFecha.getValue()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String marca = txtMarca.getText().trim();
        String beneficiario = txtBeneficiario.getText().trim();

        Seguro nuevo;

        if (rbVida.isSelected()) {

            if (beneficiario.isEmpty()) {
                mostrarAlerta("Ingrese beneficiario");
                return;
            }

            nuevo = new SeguroDeVida(numero, fechaExp, "Activo", asegurado, beneficiario);

        } else {

            if (marca.isEmpty()) {
                mostrarAlerta("Ingrese marca");
                return;
            }

            nuevo = new SeguroDeVehiculo(numero, fechaExp, "Activo", asegurado, marca);
        }

        try {
            String resultado = daoSeguro.create(nuevo);
            mostrarAlerta(resultado);

            if (resultado.startsWith("✔")) {
                limpiarFormCrear();
            }

        } catch (Exception e) {
            mostrarAlerta(e.getMessage());
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resultado");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarFormCrear() {
        txtNumero.clear();
        dateFecha.setValue(LocalDate.now());
        cmbAsegurado.setValue(null);
        rbVehiculo.setSelected(true);
        txtMarca.clear();
        txtBeneficiario.clear();
    }

    private void validarSoloNumeros(TextField txt) {
        String texto = txt.getText().trim();

        if (!texto.isEmpty() && !texto.matches("\\d+")) {
            txt.setStyle("-fx-border-color: red;");
            txt.setText("");
            mostrarAlerta("Solo números permitidos");
            Platform.runLater(txt::requestFocus);
        } else {
            txt.setStyle("");
        }
    }
}