package test;

import exception.PessoaException;
import service.PessoaService;

public class PessoaTest implements Test {

    // Atributos
    private PessoaService pessoaService;

    // Construtor
    public PessoaTest(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    // Métodos de testes

    public String listar() throws PessoaException {
        return pessoaService.listar();
    }

    public String cadastrar() throws PessoaException {
        return pessoaService.cadastrar(
                "a",
                "10/01/2000",
                "c",
                "a2",
                "a3",
                "a4"
        );
    }

    public String alterar() throws PessoaException {
        return pessoaService.alterar(
                10L,
                "fsdfds",
                "01/10/2000",
                "fsdfds",
                "z2",
                "z3",
                "z4"
        );
    }

    public String excluir() throws PessoaException {
        return pessoaService.excluir(10L);
    }

}
