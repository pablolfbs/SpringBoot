package br.com.senac.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.dominio.Aluno;
import br.com.senac.repositorio.AlunoRepositorio;

@Service
public class LoginService {
	
	@Autowired
	private AlunoRepositorio repoAluno;

	public boolean login(Aluno aluno) {
		Aluno alunoLogado = repoAluno.findByEmailAndNome(aluno.getEmail(), aluno.getNome());
		if (alunoLogado == null) {
			return false;
		}
		return true;
	}
	
}
