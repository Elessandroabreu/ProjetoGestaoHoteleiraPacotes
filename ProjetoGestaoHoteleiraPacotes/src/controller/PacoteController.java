package controller;

import enumeration.Funcionalidade;
import exception.PacoteException;
import test.PacoteTest;
import model.Acomodacao;
import model.Funcionario;
import model.Pacote;
import dao.PacoteDAO;

public class PacoteController {

    // Instância de PacoteTest usada para realizar testes no controller
    private PacoteTest pacoteTest;

    // Construtor que recebe um objeto PacoteTest e inicializa o atributo
    public PacoteController(PacoteTest pacoteTest) {
        this.pacoteTest = pacoteTest;
    }

    // Método para cadastrar um novo pacote
    public void cadastrarPacote() {
        try {
            // Cria um funcionário responsável pela acomodação
            Funcionario funcionario = new Funcionario(
                    1L, "Funcionario 1", null, null, null, null, null, "Cargo", 3000.0
            );

            // Cria uma acomodação com dados fictícios
            Acomodacao acomodacao = new Acomodacao(
                    1L, "Acomodação Luxo", 200.0, 4, "Descrição da Acomodação", funcionario
            );

            // Verifica se a acomodação é nula e exibe mensagem de erro, caso seja
            if (acomodacao == null) {
                System.out.println("Erro: Acomodação não pode ser nula.");
                return;
            }

            // Cria um novo pacote associando a acomodação criada
            Pacote pacote = new Pacote(
                    "Pacote Especial", acomodacao, 5, 1000.0
            );

            // Verifica se o pacote possui uma acomodação; caso contrário, exibe mensagem de erro
            if (pacote.getAcomodacao() == null) {
                System.out.println("Erro: Pacote não pode ser criado sem uma acomodação.");
                return;
            }

            // Inicializa o DAO para operações com banco de dados e tenta inserir o pacote
            PacoteDAO pacoteDAO = new PacoteDAO();
            boolean sucesso = pacoteDAO.inserir(pacote);

            // Exibe mensagem de sucesso ou erro com base no resultado da operação de inserção
            if (sucesso) {
                System.out.println("Pacote cadastrado com sucesso!");
            } else {
                System.out.println("Erro ao cadastrar pacote.");
            }

        } catch (PacoteException e) {
            // Captura exceção de pacote e exibe a mensagem de erro
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // Método para testar operações com pacotes, usando enum para definir qual funcionalidade executar
    public String testar(Funcionalidade funcionalidade) throws PacoteException {
        switch (funcionalidade) {
            case LISTAR:
                return pacoteTest.listar();
            case CADASTRAR:
                return pacoteTest.cadastrar();
            case ALTERAR:
                return pacoteTest.alterar();
            case EXCLUIR:
                return pacoteTest.excluir();
            default:
                return null;
        }
    }
}
