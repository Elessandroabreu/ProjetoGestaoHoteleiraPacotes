package exception;

// Classe de exceção personalizada para tratar erros relacionados a Pacote
public class PacoteException extends Exception {

    // Construtor que recebe uma mensagem de erro e a passa para a superclasse Exception
    public PacoteException(String mensagem) {
        super(mensagem); // Chama o construtor da classe Exception com a mensagem fornecida
    }
}
