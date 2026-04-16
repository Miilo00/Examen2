package co.edu.poli.Parcial_Vera.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import co.edu.poli.Parcial_Vera.modelo.Asegurado;

public class DAOAsegurado implements CRUD<Asegurado> {

	@Override
	public String create(Asegurado t) throws Exception {

		Connection con = ConexionBD.getInstancia().getConexion();

		String sql = "INSERT INTO Asegurado (nombre) VALUES (?)";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, t.getNombre());

		ps.executeUpdate();

		return "✔ Asegurado creado correctamente";
	}

	@Override
	public <K> Asegurado readone(K id) throws Exception {

		Connection con = ConexionBD.getInstancia().getConexion();

		String sql = "SELECT * FROM Asegurado WHERE id = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, Integer.parseInt(id.toString()));

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return new Asegurado(
					rs.getInt("id"),
					rs.getString("nombre"));
		}

		return null;
	}

	@Override
	public List<Asegurado> readall() throws Exception {

		Connection con = ConexionBD.getInstancia().getConexion();
		List<Asegurado> lista = new ArrayList<>();

		String sql = "SELECT * FROM Asegurado";

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Asegurado a = new Asegurado(
					rs.getInt("id"),
					rs.getString("nombre"));
			lista.add(a);
		}

		return lista;
	}
}