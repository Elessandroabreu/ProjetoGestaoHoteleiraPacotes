package service;

import dao.PessoaDAO;
import enumeration.Funcionalidade;
import exception.PessoaException;
import model.Pessoa;
import util.Util;

import java.time.LocalDate;
import java.util.ArrayList;

public class PessoaService {

    // Atributos
    private PessoaDAO pessoaDAO;

    // Construtor
    public PessoaService(PessoaDAO pessoaDAO) {
        this.pessoaDAO = pessoaDAO;
    }

    // Métodos públicos

    public String listar() throws PessoaException {
        ArrayList<Pessoa> pessoas =  pessoaDAO.selecionar();
        String lista = "";
        if(pessoas.size() > 0) {
            for (Pessoa pessoa : pessoas) {
                lista += pessoa + "\n";
            }
        } else {
            lista = "Nenhuma pessoa encontrada.";
        }
        return lista;
    }

    public String cadastrar(
        String nomeCompleto,
        String dataNascimentoFormatoBR,
        String documento,
        String pais,
        String estado,
        String cidade
    ) throws PessoaException {
        String mensagemErro = validarCampos(Funcionalidade.CADASTRAR, null, nomeCompleto, dataNascimentoFormatoBR, documento);
        if(!mensagemErro.equals("")) throw new PessoaException(mensagemErro);

        LocalDate dataNascimento = Util.formatarStringParaLocalDate(dataNascimentoFormatoBR);
        Pessoa pessoa = new Pessoa(
            nomeCompleto,
            dataNascimento,
            documento,
            pais,
            estado,
            cidade
        );

        if(pessoaDAO.inserir(pessoa)) {
            return "Pessoa cadastrada com sucesso!";
        } else {
            throw new PessoaException("Não foi possível cadastrar a pessoa! Por favor, tente novamente.");
        }
    }

    public String alterar(
            Long id,
            String nomeCompleto,
            String dataNascimentoFormatoBR,
            String documento,
            String pais,
            String estado,
            String cidade
    ) throws PessoaException {
        String mensagemErro = validarCampos(Funcionalidade.ALTERAR, id, nomeCompleto, dataNascimentoFormatoBR, documento);
        if(!mensagemErro.equals("")) throw new PessoaException(mensagemErro);

        LocalDate dataNascimento = Util.formatarStringParaLocalDate(dataNascimentoFormatoBR);
        Pessoa pessoa = new Pessoa(
                id,
                nomeCompleto,
                dataNascimento,
                documento,
                pais,
                estado,
                cidade
        );

        if(pessoaDAO.atualizar(pessoa)) {
            return "Pessoa alterada com sucesso!";
        } else {
            throw new PessoaException("Não foi possível alterar a pessoa!! Por favor, tente novamente.");
        }
    }

    public String excluir(Long id) throws PessoaException {
        String mensagemErro = validarCampos(Funcionalidade.EXCLUIR, id, null, null, null);
        if(!mensagemErro.equals("")) throw new PessoaException(mensagemErro);

        if(pessoaDAO.deletar(id)) {
            return "Pessoa excluída com sucesso!";
        } else {
            throw new PessoaException("Não foi possível excluir a pessoa! Por favor, tente novamente.");
        }
    }

    // Métodos privados

    private String validarCampos(
        Funcionalidade funcionalidade,
        Long id,
        String nomeCompleto,
        String dataNascimentoFormatoBR,
        String documento
    ) throws PessoaException {
        String erros = "";
        // Verificação de id
        if(funcionalidade == Funcionalidade.ALTERAR || funcionalidade == Funcionalidade.EXCLUIR) {
            if(id == null) {
                erros += "\n- Id é obrigatório.";
            } else if(pessoaDAO.selecionarPorId(id) == null) {
                erros += "\n- Id não encontrado.";
            }
        }
        // Verificação de outros campos
        if(funcionalidade == Funcionalidade.CADASTRAR || funcionalidade == Funcionalidade.ALTERAR) {
            // Nome completo
            if(nomeCompleto == null || nomeCompleto.trim().equals("")) {
                erros += "\n- Nome completo é obrigatório.";
            }
            // Data de nascimento
            if(dataNascimentoFormatoBR == null || dataNascimentoFormatoBR.trim().equals("")) {
                erros += "\n- Data de nascimento é obrigatória.";
            } else if(!Util.validarDataFormatoBR(dataNascimentoFormatoBR)) {
                erros += "\n- Data de nascimento inválida.";
            }
            // Documento
            if(documento == null || documento.trim().equals("")) {
                erros += "\n- Documento é obrigatório.";
            }
        }

        // Montagem da mensagem de erro
        String mensagemErro = "";
        if(!erros.equals("")) {
            mensagemErro = "Não foi possível " + funcionalidade.name().toLowerCase() + " a pessoa! " +
                "Erro(s) encontrado(s):" + erros;
        }
        return mensagemErro;
    }

}
