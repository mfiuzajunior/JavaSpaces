package br.edu.ifce.mflj.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import br.edu.ifce.mflj.model.Espaco;
import br.edu.ifce.mflj.model.Tupla;

@SuppressWarnings("serial")
public class ListaDeSalas extends JList<String> {

	private DefaultListModel<String>	salas;
	private Espaco						espaco;

	public ListaDeSalas(){
		this.espaco = Espaco.getInstancia();
		this.salas = new DefaultListModel<String>();
		this.setModel( salas );
		this.atualizarSalas();
	}

	public void adicionarSala( String sala ){
		this.salas.addElement( sala );
	}

	public void removerSala( String sala ){
		this.salas.removeElement( sala );
	}

	public void atualizarSalas() {
		List<Tupla> salasDoEspaco	= new ArrayList<Tupla>();
		Tupla		template		= new Tupla( Tupla.TIPO_TUPLA_SALA ),
					sala;

		List<Object> salasCadastradas	= Arrays.asList(this.salas.toArray());
		List<String> salasExpiradas		= new ArrayList<String>();

		// Retira as salas do espaço
		do {
			sala = (Tupla) espaco.retirarTupla( template );
			if( sala != null ){
				salasDoEspaco.add( sala );
			}
		} while( sala != null );

		// Adiciona as salas novas
		for( Tupla salaAtual : salasDoEspaco ){
			if( !salasCadastradas.contains( salaAtual.identificacao ) ){
				adicionarSala( salaAtual.identificacao );
			}
		}

		// Devolve as salas no espaço
		for( Tupla salaAtual : salasDoEspaco ){
			espaco.escreverTupla( salaAtual, Tupla.TEMPO_DE_VIDA_SALA );
		}

		// Marca as salas expiradas
		for( int indx = 0; indx < this.getModel().getSize(); indx++ ){
			template.identificacao = this.getModel().getElementAt( indx );

			if( espaco.lerTupla( template ) == null ){
				salasExpiradas.add( template.identificacao );
			}
		}

		// Remove as salas da lista
		for( String salaAtual : salasExpiradas ){
			removerSala( salaAtual );
		}
	}
}
