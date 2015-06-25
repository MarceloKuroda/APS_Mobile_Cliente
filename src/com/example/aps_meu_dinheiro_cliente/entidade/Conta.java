package com.example.aps_meu_dinheiro_cliente.entidade;

public class Conta {
	private long id;
	private long idusuario;
	private String tipo;
	private String descricao;
	private Double valor;
	private String data;
	private boolean pago;

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean getPago() {
		return pago;
	}

	public void setPago(boolean pago) {
		this.pago = pago;
	}
	
	public long getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(long idusuario) {
		this.idusuario = idusuario;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Conta(long id, long idusuario, String tipo, String descricao, Double valor, String data, boolean pago) {
		this.id = id;
		this.idusuario = idusuario;
		this.tipo = tipo;
		this.descricao = descricao;
		this.valor = valor;
		this.data = data;
		this.pago = pago;
	}

	@Override
	public String toString(){
		return "Descricao: " + descricao + "\nData Pagam.: " + data + "\nValor: " + valor;
	}
}
