package br.edu.ifce.mflj.model;

import net.jini.core.entry.Entry;

@SuppressWarnings("serial")
public class SalaUsuario implements Entry {
	public String	nomeDaSala,
					apelido;

	public SalaUsuario(){}

	public SalaUsuario(String nomeDaSala) {
		this.nomeDaSala = nomeDaSala;
	}

	public SalaUsuario(String nomeDaSala, String apelido) {
		this.nomeDaSala = nomeDaSala;
		this.apelido = apelido;
	}
}
