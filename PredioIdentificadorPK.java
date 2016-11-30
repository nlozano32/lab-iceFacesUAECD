package co.gov.uaecd.siic.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class PredioIdentificadorPK {
	@Column(name="CODIGO_BARRIO")
	private String codigoBarrio;

	@Column(name="CODIGO_MANZANA")
	private String codigoManzana;

	@Column(name="CODIGO_PREDIO")
	private String codigoPredio;

	@Column(name="CODIGO_CONSTRUCCION")
	private String codigoConstruccion;

	@Column(name="CODIGO_RESTO")
	private String codigoResto;

	private static final long serialVersionUID = 1L;

	public PredioIdentificadorPK() {
		super();
	}

	public String getCodigoBarrio() {
		return codigoBarrio;
	}

	public void setCodigoBarrio(String codigoBarrio) {
		this.codigoBarrio = codigoBarrio;
	}

	public String getCodigoManzana() {
		return codigoManzana;
	}

	public void setCodigoManzana(String codigoManzana) {
		this.codigoManzana = codigoManzana;
	}

	public String getCodigoPredio() {
		return codigoPredio;
	}

	public void setCodigoPredio(String codigoPredio) {
		this.codigoPredio = codigoPredio;
	}

	public String getCodigoConstruccion() {
		return codigoConstruccion;
	}

	public void setCodigoConstruccion(String codigoConstruccion) {
		this.codigoConstruccion = codigoConstruccion;
	}

	public String getCodigoResto() {
		return codigoResto;
	}

	public void setCodigoResto(String codigoResto) {
		this.codigoResto = codigoResto;
	}
}
