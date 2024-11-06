package model;

import util.Util;

import java.time.LocalDate;
import java.util.Objects;

public class Funcionario extends Pessoa {

    // Atributos
    private Long id; // Identificador
    private String cargo; // Obrigatório
    private Double salario; // Opcional

    // Construtor vazio
    public Funcionario() {}

    // Construtor sem o id
    public Funcionario(
        String nomeCompleto,
        LocalDate dataNascimento,
        String documento,
        String pais,
        String estado,
        String cidade,
        String cargo,
        Double salario
    ) {
        super(nomeCompleto, dataNascimento, documento, pais, estado, cidade);
        this.cargo = cargo;
        this.salario = salario;
    }

    // Construtor com todos os atributos
    public Funcionario(
        Long id,
        String nomeCompleto,
        LocalDate dataNascimento,
        String documento,
        String pais,
        String estado,
        String cidade,
        Long idPessoa,
        String cargo,
        Double salario
    ) {
        super(idPessoa, nomeCompleto, dataNascimento, documento, pais, estado, cidade);
        this.id = id;
        this.cargo = cargo;
        this.salario = salario;
    }

    public Funcionario(long aLong, String string, Object o, Object o1, Object o2, Object o3, String string1, String string2, double aDouble) {
    }

    // Getters e Setters

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    // Outros métodos

    @Override
    public String toString() {
        return "Id: " + id +
            " | Nome completo: " + super.getNomeCompleto() +
            " | Data de nascimento: " + Util.formatarDataBR(super.getDataNascimento()) +
            " | Documento: " + super.getDocumento() +
            " | País: " + super.getPais() +
            " | Estado: " + super.getEstado() +
            " | Cidade: " + super.getCidade() +
            " | Cargo: " + cargo +
            " | Salário: " + salario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Funcionario that = (Funcionario) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }


}
