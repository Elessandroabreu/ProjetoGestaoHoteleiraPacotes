package test;

import exception.PacoteException;
import service.PacoteService;

// Classe de teste para realizar operações de Pacote usando o PacoteService
public class PacoteTest implements Test {

    private PacoteService pacoteService;

    // Construtor que inicializa o PacoteTest com uma instância de PacoteService
    public PacoteTest(PacoteService pacoteService) {
        this.pacoteService = pacoteService;
    }

    // Método para testar a listagem de pacotes, delegando a chamada para o PacoteService
    public String listar() throws PacoteException {
        return pacoteService.listar();
    }

    // Método para testar o cadastro de um pacote
    public String cadastrar() throws PacoteException {
        return pacoteService.cadastrar(1L, "Promoção Luxo", 5, 1500.0);
    }

    // Método para testar a atualização de um pacote existente
    // Aqui o valorTotal é opcional (pode ser null)
    public String alterar() throws PacoteException {
        return pacoteService.alterar(1L, 2L, "Promoção Super Luxo", 7, null);
    }

    // Método para testar a exclusão de um pacote
    public String excluir() throws PacoteException {
        return pacoteService.excluir(1L);
    }
}
