package br.com.repository;

import java.util.List;

import br.com.entidades.Lancamento;

public interface IDaoTodosLancamentos {

	List<Lancamento> consultarTodos();
}
