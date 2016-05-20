package br.edu.ifce.mflj.model;

import net.jini.core.entry.Entry;

@SuppressWarnings("serial")
public class Tupla implements Entry {
	public static final Integer	TIPO_TUPLA_SALA		= 0,
								TIPO_TUPLA_USUARIO	= 1;
	public static final	Integer	TEMPO_DE_VIDA_SALA	= 1000 * 60 * 10;

	public String	identificacao;
	public Integer	tipoTupla;

	public Tupla(){}

	public Tupla( Integer tipoTupla ){
		this.tipoTupla = tipoTupla;
	}

	public Tupla( Integer tipoTupla, String identificacao ){
		this.tipoTupla		= tipoTupla;
		this.identificacao	= identificacao;
	}
}
