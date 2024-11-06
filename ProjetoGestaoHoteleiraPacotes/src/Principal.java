import controller.PacoteController;
import dao.PacoteDAO;
import enumeration.Funcionalidade;
import exception.PacoteException;
import service.PacoteService;
import test.PacoteTest;

public class Principal {

    public static void main(String[] args) {
        try {
            PacoteDAO pacoteDAO = new PacoteDAO();
            PacoteService pacoteService = new PacoteService(pacoteDAO);
            PacoteTest pacoteTest = new PacoteTest(pacoteService);
            PacoteController pacoteController = new PacoteController(pacoteTest);

            Funcionalidade funcionalidade = Funcionalidade.LISTAR;
            System.out.println("PACOTE: " + funcionalidade);
            System.out.println("RESULTADO DO TESTE:");
            System.out.println(pacoteController.testar(funcionalidade));

        } catch (PacoteException e) {
            System.err.println(e.getMessage());
        }
    }
}
