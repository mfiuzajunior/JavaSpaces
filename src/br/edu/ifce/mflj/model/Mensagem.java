package br.edu.ifce.mflj.model;

public class Mensagem {
	public String	de,
					para,
					mensagem;

	public Mensagem(){}

	public Mensagem( String de, String para, String mensagem ){
		this.de			= de;
		this.para		= para;
		this.mensagem	= mensagem;
	}
}
