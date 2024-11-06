package service;

import dao.PacoteDAO;
import enumeration.Funcionalidade;
import exception.PacoteException;
import model.Acomodacao;
import model.Funcionario;
import model.Pacote;

import java.util.ArrayList;

public class PacoteService {

    private PacoteDAO pacoteDAO;

    // Construtor da classe
    public PacoteService(PacoteDAO pacoteDAO) {
        this.pacoteDAO = pacoteDAO;
    }

    // Método para listar pacotes
    public String listar() throws PacoteException {
        ArrayList<Pacote> pacotes = pacoteDAO.selecionar();
        String lista = pacotes.isEmpty() ? "Nenhum pacote encontrado." : "";
        for (Pacote pacote : pacotes) {
            lista += pacote.toString() + "\n";
        }
        return lista;
    }

    // Método para cadastrar um novo pacote com validação de campos obrigatórios
    public String cadastrar(Long idAcomodacao, String nome, int qtdDiarias, Double valorTotal) throws PacoteException {
        String mensagemErro = validarCampos(Funcionalidade.CADASTRAR, null, idAcomodacao, nome, qtdDiarias);
        if (!mensagemErro.isEmpty()) throw new PacoteException(mensagemErro);

        // Simular a criação de um funcionário responsável
        Funcionario funcionario = new Funcionario(1L, "Funcionario 1", null, null, null, null, null, "Cargo", 3000.0);

        // Criar a acomodação
        Acomodacao acomodacao = new Acomodacao(
                idAcomodacao,
                "Acomodação Luxo",
                200.0,
                4,
                "Descrição da Acomodação",
                funcionario
        );

        // Criar o pacote, passando valorTotal como nulo, se necessário
        Pacote pacote = new Pacote(nome, acomodacao, qtdDiarias, valorTotal != null ? valorTotal : 0.0);

        // Inserir o pacote no banco de dados
        if (pacoteDAO.inserir(pacote)) {
            return "Pacote cadastrado com sucesso!";
        } else {
            throw new PacoteException("Erro ao cadastrar pacote.");
        }
    }

    // Método para alterar um pacote existente
    public String alterar(Long id, Long idAcomodacao, String nome, int qtdDiarias, Double valorTotal) throws PacoteException {
        String mensagemErro = validarCampos(Funcionalidade.ALTERAR, id, idAcomodacao, nome, qtdDiarias);
        if (!mensagemErro.isEmpty()) throw new PacoteException(mensagemErro);

        // Selecionar o pacote existente
        Pacote pacote = pacoteDAO.selecionarPorId(id);
        if (pacote == null) {
            throw new PacoteException("Pacote não encontrado! Por favor, tente novamente.");
        }

        // Simular a criação de um funcionário responsável
        Funcionario funcionario = new Funcionario(1L, "Funcionario 1", null, null, null, null, null, "Cargo", 3000.0);

        // Atualizar a acomodação do pacote
        Acomodacao acomodacao = new Acomodacao(
                idAcomodacao,
                "Acomodação Luxo",
                200.0,
                4,
                "Descrição da Acomodação",
                funcionario
        );

        // Atualizar os dados do pacote
        pacote.setNome(nome);
        pacote.setQtdDiarias(qtdDiarias);
        pacote.setValorTotal(valorTotal != null ? valorTotal : pacote.getValorTotal()); // Se valorTotal for nulo, mantemos o valor atual
        pacote.setAcomodacao(acomodacao);

        // Atualizar o pacote no banco de dados
        if (pacoteDAO.atualizar(pacote)) {
            return "Pacote atualizado com sucesso!";
        } else {
            throw new PacoteException("Erro ao atualizar pacote! Por favor, tente novamente.");
        }
    }

    // Método para excluir um pacote
    public String excluir(Long id) throws PacoteException {
        if (pacoteDAO.deletar(id)) {
            return "Pacote excluído com sucesso!";
        } else {
            throw new PacoteException("Erro ao excluir pacote, Por favor, tente novamente.");
        }
    }

    // Método para validar campos obrigatórios
    private String validarCampos(Funcionalidade funcionalidade, Long id, Long idAcomodacao, String nome, Integer qtdDiarias) throws PacoteException {
        String erros = "";

        // Validação do ID no caso de alteração ou exclusão
        if (funcionalidade == Funcionalidade.ALTERAR || funcionalidade == Funcionalidade.EXCLUIR) {
            if (id == null) {
                erros += "\n- Id é obrigatório.";
            } else if (pacoteDAO.selecionarPorId(id) == null) {
                erros += "\n- Id não encontrado.";
            }
        }

        // Validação dos campos obrigatórios para cadastro ou alteração
        if (funcionalidade == Funcionalidade.CADASTRAR || funcionalidade == Funcionalidade.ALTERAR) {
            if (idAcomodacao == null) {
                erros += "\n- ID da acomodação é obrigatório.";
            }
            if (nome == null || nome.trim().isEmpty()) {
                erros += "\n- Nome é obrigatório.";
            }
            if (qtdDiarias == null || qtdDiarias <= 0) {
                erros += "\n- Quantidade de diárias deve ser maior que zero.";
            }
        }

        // Montagem da mensagem de erro
        if (!erros.isEmpty()) {
            return "Não foi possível " + funcionalidade.name().toLowerCase() + " o pacote! Erro(s) encontrado(s):" + erros;
        }
        return "";
    }
}
