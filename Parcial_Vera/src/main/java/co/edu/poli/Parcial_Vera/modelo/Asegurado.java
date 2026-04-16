package co.edu.poli.Parcial_Vera.modelo;

public class Asegurado {

	private int id;
	private String nombre;

	public Asegurado(int string, String nombre) {
		this.id = string;
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return id + ", " + nombre;
	}
}