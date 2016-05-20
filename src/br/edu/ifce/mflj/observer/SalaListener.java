package br.edu.ifce.mflj.observer;

public interface SalaListener {
	void novaSala(String sala);
	void salaExpirada(String nomeDaSala);
}
