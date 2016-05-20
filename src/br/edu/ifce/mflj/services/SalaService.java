package br.edu.ifce.mflj.services;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifce.mflj.exceptions.EspacoOfflineException;
import br.edu.ifce.mflj.exceptions.SalaExistenteException;
import br.edu.ifce.mflj.model.Espaco;
import br.edu.ifce.mflj.model.Tupla;

public class SalaService {

	private Espaco	espaco;

	public void iniciarServico(){
		this.espaco = Espaco.getInstancia();
	}

	public boolean existeSala( String nomeDaSala ){
		Tupla sala = new Tupla( Tupla.TIPO_TUPLA_SALA );

		sala.identificacao = nomeDaSala;

		return espaco.lerTupla( sala ) != null;
	}

	public void adicionarSala( String sala ) throws EspacoOfflineException, SalaExistenteException {
		Tupla novaSala = new Tupla( Tupla.TIPO_TUPLA_SALA, sala );
		if( espaco.lerTupla( novaSala ) != null ){
			throw new SalaExistenteException();
		} else {
			novaSala( sala );
		}
	}

	private void novaSala(String sala) {
		Tupla tupla;
		tupla = new Tupla( Tupla.TIPO_TUPLA_SALA, sala );

		espaco.escreverTupla( tupla, Tupla.TEMPO_DE_VIDA_SALA );
	}

	public List<String> getListaDeSalas() {
		List<String>	salas			= new ArrayList<String>();
		List<Tupla>		tuplasRetiradas	= new ArrayList<Tupla>();
		Tupla			tupla;

		do {
			tupla = (Tupla) espaco.retirarTupla( new Tupla( Tupla.TIPO_TUPLA_SALA ) );
			if( tupla != null ){
				tuplasRetiradas.add( tupla );
				salas.add( tupla.identificacao );
			}
		} while( tupla != null );

		for( Tupla tuplaAtual : tuplasRetiradas ){
			espaco.escreverTupla( tuplaAtual, Tupla.TEMPO_DE_VIDA_SALA );
		}

		return salas;
	}
}
