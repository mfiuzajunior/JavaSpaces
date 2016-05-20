package br.edu.ifce.mflj.view;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
public class SalaView extends JDialog {

	private static final int	LARGURA			= 400,
								ALTURA			= 300;

	public SalaView( String titulo, Frame parent, boolean modal ){
		super( parent, modal );

		iniciarGUI( titulo );

		this.repaint();
	}

	private void iniciarGUI( String titulo ){
		setDefaulLookAndFeel();
		iniciarComponentes();

		this.setTitle( titulo );
		this.setResizable( false );
		this.setBounds( this.getParent().getX() + 10, this.getParent().getY() + 10, SalaView.LARGURA, SalaView.ALTURA );

		this.getContentPane().setLayout( null );

		this.setVisible( true );

	}

	private void iniciarComponentes() {
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
}
