package br.edu.ifce.mflj.view;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.edu.ifce.mflj.exceptions.EspacoOfflineException;
import br.edu.ifce.mflj.exceptions.SalaExistenteException;
import br.edu.ifce.mflj.services.SalaService;
import br.edu.ifce.mflj.services.UsuarioService;

@SuppressWarnings("serial")
public class LobbyView extends JFrame implements MouseListener {

	private static final int	LARGURA			= 800,
								ALTURA			= 600;

	private	ListaDeSalas	salas;
	private SalaService		servicoDeSalas;
	private UsuarioService	servicoDeUsuarios;

	private	JScrollPane		listaDeSalas;
	private	JLabel			adicionarSala,
							atualizarSalas;

	private String			apelido;

	public LobbyView(){
		super();

		iniciarServicos();
		iniciarGUI();

		this.repaint();
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	private void iniciarGUI(){
		setDefaulLookAndFeel();

		this.setResizable( false );
		this.setBounds( 100, 100, LobbyView.LARGURA, LobbyView.ALTURA );
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		this.getContentPane().setLayout( null );

		this.setVisible( true );

		iniciarComponentes();
//		receberApelido();
	}

	private void receberApelido() {
		do {
			apelido = (String)JOptionPane.showInputDialog( this, "Como deseja ser chamado?", "Atenção", JOptionPane.QUESTION_MESSAGE);
			try{
				if( apelido.trim().equals( "" ) ){
					JOptionPane.showMessageDialog( this, "É necessário entrar com um apelido", "Erro", JOptionPane.ERROR_MESSAGE );
				} else {
					if( !servicoDeUsuarios.logar( apelido ) ){
						JOptionPane.showMessageDialog( this, "Usuário já existe", "Atenção", JOptionPane.ERROR_MESSAGE );
						apelido = "";
					}
				}
			} catch( NullPointerException nullPointerException ){
				System.exit( -1 );
			}
		} while( apelido.trim().equals( "" ) );		
	}

	private void iniciarServicos() {
		servicoDeSalas		= new SalaService();
		servicoDeUsuarios	= new UsuarioService();

		servicoDeSalas.iniciarServico();
		servicoDeUsuarios.iniciarServico();
	}

	private void iniciarComponentes() {
		this.getContentPane().add( getListaDeSalas() );
		this.getContentPane().add( getAdicionarSala() );
		this.getContentPane().add( getAtualizarSalas() );
		
	}

	private Component getAtualizarSalas() {
		if( atualizarSalas == null ){
			atualizarSalas = new JLabel(new ImageIcon( getClass().getResource("/atualizar.png") ) );
			atualizarSalas.setBounds(	getAdicionarSala().getX() + getAdicionarSala().getWidth() + 5,
										getAdicionarSala().getY(), 32, 32 );
			atualizarSalas.addMouseListener( this );
		}

		return atualizarSalas;
	}

	private JScrollPane getListaDeSalas(){
		if( listaDeSalas == null ){
			listaDeSalas = new JScrollPane();
			listaDeSalas.setBounds( 5, 5, 100, 150 );
			listaDeSalas.setViewportView( getSalas() );
		}

		return listaDeSalas;
	}

	private ListaDeSalas getSalas() {
		if( salas == null ){
			salas = new ListaDeSalas();
			salas.addMouseListener( this );
			salas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}

		return salas;
	}

	private JLabel getAdicionarSala() {
		if( adicionarSala == null ){
			adicionarSala = new JLabel(new ImageIcon( getClass().getResource("/adicionar.png") ) );
			adicionarSala.setBounds( 5, getListaDeSalas().getY() + getListaDeSalas().getHeight() + 5, 32, 32 );
			adicionarSala.addMouseListener( this );
		}

		return adicionarSala;
	}

	private void setDefaulLookAndFeel(){
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		}
		catch( UnsupportedLookAndFeelException unsupportedLookAndFeelException ){}
		catch( ClassNotFoundException classNotFoundException ){}
		catch( InstantiationException instantiationException ){}
		catch( IllegalAccessException illegalAccessException ){}		
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		Object componente = mouseEvent.getSource();

		if( componente.equals( getSalas() ) ){
			if( getSalas().getSelectedValue() != null ){
				new SalaView( getSalas().getSelectedValue(), this, true );			
			}
		} else if( componente.equals( getAdicionarSala() ) ){
			String nomeDaSala = "";
			
			do {
				nomeDaSala = (String)JOptionPane.showInputDialog( this, "Insira o nome da sala", "Atenção", JOptionPane.QUESTION_MESSAGE);
				try{
					if( nomeDaSala.trim().equals( "" ) ){
						JOptionPane.showMessageDialog( this, "É necessário entrar com um nome para a sala", "Erro", JOptionPane.ERROR_MESSAGE );
					} else {
						servicoDeSalas.adicionarSala( nomeDaSala );
						this.salas.atualizarSalas();
					}

				} catch( NullPointerException nullPointerException ){
					nullPointerException.printStackTrace();
					break;

				} catch( EspacoOfflineException espacaoOfflineException ){
					JOptionPane.showMessageDialog( this, "Servidor offline", "Atenção", JOptionPane.ERROR_MESSAGE );

				} catch( SalaExistenteException salaExistenteException ){
					JOptionPane.showMessageDialog( this, "Sala já existe", "Atenção", JOptionPane.ERROR_MESSAGE );
				}
			} while( nomeDaSala.trim().equals( "" ) );
		} else if( componente.equals( getAtualizarSalas() ) ){
			String[] opcoes = new String[] { "Sim", "Não" };
			int	opcao = JOptionPane.showOptionDialog( this, "Atualizar salas?", "Atenção", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0] );
			switch (opcao) {
				case 0:
					this.salas.atualizarSalas();
					break;

				default:
					break;
			}
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	
	@Override
	public void mouseReleased(MouseEvent e) {}

	public static void main( String[] args ){
		new LobbyView();
	}
}
