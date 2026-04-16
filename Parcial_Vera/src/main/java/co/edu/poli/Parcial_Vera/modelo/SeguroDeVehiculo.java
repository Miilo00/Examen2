package co.edu.poli.Parcial_Vera.modelo;

public class SeguroDeVehiculo extends Seguro {

	private String marca;

	public SeguroDeVehiculo(String numero, String fechaExp, String estado, Asegurado asegurado, String marca) {
		super(numero, fechaExp, estado, asegurado);
		this.marca = marca;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	@Override
	public String toString() {
		return "SeguroDeVehiculo [" + super.toString() + ", marca=" + marca + "]";
	}
}
