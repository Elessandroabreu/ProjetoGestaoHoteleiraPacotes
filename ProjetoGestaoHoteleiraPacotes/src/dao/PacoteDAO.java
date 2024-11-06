package dao;

import connection.ConexaoMySQL;
import exception.PacoteException;
import model.Acomodacao;
import model.Funcionario;
import model.Pacote;

import java.sql.*;
import java.util.ArrayList;

public class PacoteDAO implements DAO<Pacote> {

    // Método para selecionar todos os pacotes e retorná-los em uma lista
    @Override
    public ArrayList<Pacote> selecionar() throws PacoteException {
        try {
            // SQL para buscar dados do pacote, acomodação, funcionário e pessoa associados
            String sql = "SELECT p.id AS pacote_id, p.id_acomodacao, p.nome AS pacote_nome, p.qtd_diarias, p.valor_total, " +
                    "a.nome AS acomodacao_nome, a.valor_diaria, a.limite_hospedes, a.descricao, " +
                    "f.id AS funcionario_id, f.cargo, f.salario, pe.nome_completo, pe.cidade " +
                    "FROM pacote p " +
                    "JOIN acomodacao a ON a.id = p.id_acomodacao " +
                    "JOIN funcionario f ON f.id = a.id_funcionario_responsavel " +
                    "JOIN pessoa pe ON f.id_pessoa = pe.id";

            // Criação de um Statement para executar a consulta SQL
            Statement declaracao = ConexaoMySQL.get().createStatement();
            ResultSet resultado = declaracao.executeQuery(sql);
            ArrayList<Pacote> pacotes = new ArrayList<>();

            // Processa cada linha do resultado para instanciar objetos Pacote
            while (resultado.next()) {
                Funcionario funcionario = new Funcionario(
                        resultado.getLong("funcionario_id"),
                        resultado.getString("pe.nome_completo"),
                        null,
                        null,
                        null,
                        null,
                        resultado.getString("pe.cidade"),
                        resultado.getString("f.cargo"),
                        resultado.getDouble("f.salario")
                );

                Acomodacao acomodacao = new Acomodacao(
                        resultado.getLong("p.id_acomodacao"),
                        resultado.getString("acomodacao_nome"),
                        resultado.getDouble("a.valor_diaria"),
                        resultado.getInt("limite_hospedes"),
                        resultado.getString("descricao"),
                        funcionario
                );

                Pacote pacote = new Pacote(
                        resultado.getLong("pacote_id"),
                        resultado.getString("pacote_nome"),
                        acomodacao,
                        resultado.getInt("p.qtd_diarias"),
                        resultado.getDouble("p.valor_total")
                );

                pacotes.add(pacote);
            }
            return pacotes;

        } catch (SQLException e) {
            throw new PacoteException("Erro ao selecionar pacotes: " + e.getMessage());
        }
    }

    // Método para selecionar um pacote por ID
    @Override
    public Pacote selecionarPorId(Long id) throws PacoteException {
        try {
            // SQL para buscar o pacote pelo ID especificado
            String sql = "SELECT p.id AS pacote_id, p.id_acomodacao, p.nome AS pacote_nome, p.qtd_diarias, p.valor_total, " +
                    "a.nome AS acomodacao_nome, a.valor_diaria, a.limite_hospedes, a.descricao, " +
                    "f.id AS funcionario_id, f.cargo, f.salario, pe.nome_completo, pe.cidade " +
                    "FROM pacote p " +
                    "JOIN acomodacao a ON a.id = p.id_acomodacao " +
                    "JOIN funcionario f ON f.id = a.id_funcionario_responsavel " +
                    "JOIN pessoa pe ON f.id_pessoa = pe.id " +
                    "WHERE p.id = ?";

            // Preparação de um PreparedStatement para execução da consulta
            Connection conexao = ConexaoMySQL.get();
            PreparedStatement preparacao = conexao.prepareStatement(sql);
            preparacao.setLong(1, id);
            ResultSet resultado = preparacao.executeQuery();

            // Verifica se o resultado contém dados, e cria o objeto Pacote
            if (resultado.next()) {
                Funcionario funcionario = new Funcionario(
                        resultado.getLong("funcionario_id"),
                        resultado.getString("pe.nome_completo"),
                        null,
                        null,
                        null,
                        null,
                        resultado.getString("pe.cidade"),
                        resultado.getString("f.cargo"),
                        resultado.getDouble("f.salario")
                );

                Acomodacao acomodacao = new Acomodacao(
                        resultado.getLong("p.id_acomodacao"),
                        resultado.getString("acomodacao_nome"),
                        resultado.getDouble("a.valor_diaria"),
                        resultado.getInt("limite_hospedes"),
                        resultado.getString("descricao"),
                        funcionario
                );

                return new Pacote(
                        resultado.getLong("pacote_id"),
                        resultado.getString("pacote_nome"),
                        acomodacao,
                        resultado.getInt("p.qtd_diarias"),
                        resultado.getDouble("p.valor_total")
                );
            } else {
                throw new PacoteException("Nenhum pacote encontrado com o ID: " + id);
            }

        } catch (SQLException e) {
            throw new PacoteException("Erro ao selecionar pacote por ID: " + e.getMessage());
        }
    }

    // Método para inserir um novo pacote no banco de dados
    @Override
    public Boolean inserir(Pacote pacote) throws PacoteException {
        if (pacote.getAcomodacao() == null) {
            throw new PacoteException("Acomodação não pode ser nula.");
        }

        try {
            Connection conexao = ConexaoMySQL.get();
            String sql = "INSERT INTO pacote (id_acomodacao, nome, qtd_diarias, valor_total) VALUES (?, ?, ?, ?)";

            // Configuração do PreparedStatement para inserção do pacote
            PreparedStatement preparacao = conexao.prepareStatement(sql);
            preparacao.setLong(1, pacote.getAcomodacao().getId());
            preparacao.setString(2, pacote.getNome());
            preparacao.setInt(3, pacote.getQtdDiarias());
            preparacao.setObject(4, pacote.getValorTotal(), Types.DOUBLE);

            int linhasAfetadas = preparacao.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            // Tratamento de erro para ID de acomodação inválido ou outros erros de SQL
            if (e.getSQLState().equals("23000")) {
                throw new PacoteException("ID de acomodação inválido: " + pacote.getAcomodacao().getId());
            }
            throw new PacoteException("Erro ao inserir pacote: " + e.getMessage());
        }
    }

    // Método para atualizar um pacote existente no banco de dados
    @Override
    public Boolean atualizar(Pacote pacote) throws PacoteException {
        try {
            Connection conexao = ConexaoMySQL.get();
            String sql = "UPDATE pacote SET id_acomodacao = ?, nome = ?, qtd_diarias = ?, valor_total = ? WHERE id = ?";

            // Configuração do PreparedStatement para atualização do pacote
            PreparedStatement preparacao = conexao.prepareStatement(sql);
            preparacao.setLong(1, pacote.getAcomodacao().getId());
            preparacao.setString(2, pacote.getNome());
            preparacao.setInt(3, pacote.getQtdDiarias());
            preparacao.setObject(4, pacote.getValorTotal(), Types.DOUBLE);
            preparacao.setLong(5, pacote.getId());

            int linhasAfetadas = preparacao.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            // Tratamento de erro para ID de acomodação inválido ou outros erros de SQL
            if (e.getSQLState().equals("23000")) {
                throw new PacoteException("ID de acomodação inválido ao atualizar: " + pacote.getAcomodacao().getId());
            }
            throw new PacoteException("Erro ao atualizar pacote: " + e.getMessage());
        }
    }

    // Método para deletar um pacote do banco de dados por ID
    @Override
    public Boolean deletar(Long id) throws PacoteException {
        try {
            Connection conexao = ConexaoMySQL.get();
            String sql = "DELETE FROM pacote WHERE id = ?";

            // Configuração do PreparedStatement para exclusão do pacote
            PreparedStatement preparacao = conexao.prepareStatement(sql);
            preparacao.setLong(1, id);

            int linhasAfetadas = preparacao.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            throw new PacoteException("Erro ao deletar pacote: " + e.getMessage());
        }
    }
}
