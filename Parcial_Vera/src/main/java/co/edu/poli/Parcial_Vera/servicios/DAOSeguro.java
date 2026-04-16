package co.edu.poli.Parcial_Vera.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import co.edu.poli.Parcial_Vera.modelo.Seguro;
import co.edu.poli.Parcial_Vera.modelo.SeguroDeVida;
import co.edu.poli.Parcial_Vera.modelo.SeguroDeVehiculo;
import co.edu.poli.Parcial_Vera.modelo.Asegurado;

public class DAOSeguro implements CRUD<Seguro> {

    @Override
    public String create(Seguro t) throws Exception {

        Connection con = ConexionBD.getInstancia().getConexion();
        con.setAutoCommit(false);

        String sql1 = "INSERT INTO Seguro (numero, fecha_expedicion, estado, id_asegurado, tipo) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql1);

        ps.setLong(1, Long.parseLong(t.getNumero()));
        ps.setString(2, t.getFechaExpedicion());
        ps.setString(3, t.getEstado());
        ps.setInt(4, t.getAsegurado().getId());
        ps.setString(5, (t instanceof SeguroDeVida) ? "vida" : "vehiculo");

        ps.executeUpdate();

        String sql2;

        if (t instanceof SeguroDeVida) {
            sql2 = "INSERT INTO SeguroDeVida (numero, beneficiario) VALUES (?, ?)";
        } else {
            sql2 = "INSERT INTO SeguroDeVehiculo (numero, marca) VALUES (?, ?)";
        }

        ps = con.prepareStatement(sql2);

        ps.setLong(1, Long.parseLong(t.getNumero()));

        if (t instanceof SeguroDeVida) {
            ps.setString(2, ((SeguroDeVida) t).getBeneficiario());
        } else {
            ps.setString(2, ((SeguroDeVehiculo) t).getMarca());
        }

        try {
            ps.executeUpdate();
            con.commit();
            return "✔ guardada correctamente";
        } catch (Exception e) {
            con.rollback();
            return e.getMessage();
        } finally {
            con.setAutoCommit(true);
        }
    }

    @Override
    public <K> Seguro readone(K num) throws Exception {

        Connection con = ConexionBD.getInstancia().getConexion();

        String sqlVehiculo = "SELECT s.numero, s.fecha_expedicion, s.estado, a.id, a.nombre, v.marca "
                + "FROM SeguroDeVehiculo v "
                + "INNER JOIN Seguro s ON v.numero = s.numero "
                + "INNER JOIN Asegurado a ON s.id_asegurado = a.id "
                + "WHERE v.numero = ?";

        PreparedStatement ps = con.prepareStatement(sqlVehiculo);

        ps.setLong(1, Long.parseLong(num.toString()));
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new SeguroDeVehiculo(
                    String.valueOf(rs.getLong("numero")),
                    rs.getString("fecha_expedicion"),
                    rs.getString("estado"),
                    new Asegurado(rs.getInt("id"), rs.getString("nombre")),
                    rs.getString("marca"));
        }

        String sqlVida = "SELECT s.numero, s.fecha_expedicion, s.estado, a.id, a.nombre, v.beneficiario "
                + "FROM SeguroDeVida v "
                + "INNER JOIN Seguro s ON v.numero = s.numero "
                + "INNER JOIN Asegurado a ON s.id_asegurado = a.id "
                + "WHERE v.numero = ?";

        ps = con.prepareStatement(sqlVida);

        ps.setLong(1, Long.parseLong(num.toString()));
        rs = ps.executeQuery();

        if (rs.next()) {
            return new SeguroDeVida(
                    String.valueOf(rs.getLong("numero")),
                    rs.getString("fecha_expedicion"),
                    rs.getString("estado"),
                    new Asegurado(rs.getInt("id"), rs.getString("nombre")),
                    rs.getString("beneficiario"));
        }

        return null;
    }

    @Override
    public List<Seguro> readall() {
        return null;
    }
}