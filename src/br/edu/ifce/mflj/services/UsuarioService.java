package br.edu.ifce.mflj.services;

import br.edu.ifce.mflj.model.Espaco;
import br.edu.ifce.mflj.model.Tupla;

public class UsuarioService {
	private Espaco espaco;

	public void iniciarServico(){
		espaco = Espaco.getInstancia();
	}

	public boolean logar(String apelido) {
		Tupla tupla	= new Tupla( Tupla.TIPO_TUPLA_USUARIO );
		tupla.identificacao	= apelido;

		if( (Tupla) espaco.lerTupla( tupla ) != null ){
			return false;

		} else {
			espaco.escreverTupla( tupla, 1000 * 60 );
			return true;
		}
	}
}
