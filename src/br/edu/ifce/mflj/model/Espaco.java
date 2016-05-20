package br.edu.ifce.mflj.model;

import java.rmi.RemoteException;

import net.jini.core.entry.Entry;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.lease.Lease;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;
import br.edu.ifce.mflj.services.Lookup;

public class Espaco {
	private static Espaco instancia;

	private Lookup		lookup;
	private JavaSpace	javaSpace;
	private boolean		online;

	private Espaco(){
		lookup = new Lookup(JavaSpace.class);
		javaSpace = (JavaSpace)lookup.getService();
		online = true;
	}

	public static Espaco getInstancia(){
		if( instancia == null ){
			instancia = new Espaco();
		}
		return instancia;
	}

	public boolean isOnline(){
		return online;
	}

	public Entry lerTupla( Entry tupla ){
		try {
			return javaSpace.read( tupla, null, 0 );

		} catch (RemoteException e) {
			System.err.println("RemoteException");

		} catch (UnusableEntryException e) {
			System.err.println("UnusableEntryException");

		} catch (TransactionException e) {
			System.err.println("TransactionException");

		} catch (InterruptedException e) {
			System.err.println("InterruptedException");
		}

		return null;
	}

	public Entry retirarTupla( Entry tupla ){
		try {
			return javaSpace.take( tupla, null, 0 );

		} catch (RemoteException e) {
			System.err.println("RemoteException");

		} catch (UnusableEntryException e) {
			System.err.println("UnusableEntryException");

		} catch (TransactionException e) {
			System.err.println("TransactionException");

		} catch (InterruptedException e) {
			System.err.println("InterruptedException");
		}

		return null;
	}

	public Lease escreverTupla( Entry tupla, long lease ){
		try {
			return javaSpace.write( tupla, null, lease );

		} catch (RemoteException e) {
			System.err.println("RemoteException");

		} catch (TransactionException e) {
			System.err.println("TransactionException");
		}

		return null;
	}
}