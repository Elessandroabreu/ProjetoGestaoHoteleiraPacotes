package controller;

import enumeration.Funcionalidade;
import exception.PessoaException;
import test.PessoaTest;

public class PessoaController {

    // Atributos
    private PessoaTest pessoaTest;

    // Construtor
    public PessoaController(PessoaTest pessoaTest) {
        this.pessoaTest = pessoaTest;
    }

    // Gerenciador de testes
    public String testar(Funcionalidade funcionalidade) throws PessoaException {
        switch (funcionalidade) {
            case LISTAR:
                return pessoaTest.listar();
            case CADASTRAR:
                return pessoaTest.cadastrar();
            case ALTERAR:
                return pessoaTest.alterar();
            case EXCLUIR:
                return pessoaTest.excluir();
            default:
                return null;
        }
    }

}
