package co.edu.poli.Parcial_Vera.modelo;

public class SeguroDeVida extends Seguro {

	private String beneficiario;

	public SeguroDeVida(String numero, String fechaExp, String estado, Asegurado asegurado, String beneficiario) {
		super(numero, fechaExp, estado, asegurado);
		this.beneficiario = beneficiario;
	}

	public String getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}

	@Override
	public String toString() {
		return "SeguroDeVida [" + super.toString() + ", beneficiario=" + beneficiario + "]";
	}
}