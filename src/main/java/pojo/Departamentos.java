package pojo;

public class Departamentos {
	private int codigoD;
	private String nombreD;
	private String localidad;
	
	public Departamentos(int codigoD, String nombreD, String localidad) {
		this.codigoD = codigoD;
		this.nombreD = nombreD;
		this.localidad = localidad;
	}
	public int getCodigoD() {
		return codigoD;
	}
	public void setCodigoD(int codigoD) {
		this.codigoD = codigoD;
	}
	public String getNombreD() {
		return nombreD;
	}
	public void setNombreD(String nombreD) {
		this.nombreD = nombreD;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	
}
