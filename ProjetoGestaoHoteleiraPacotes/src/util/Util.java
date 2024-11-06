package util;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Util {

    // Métodos utilitários

    public static boolean validarDataFormatoBR(String dataBR) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate.parse(dataBR, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static String formatarDataBR(LocalDate data) {
        if (data == null) return null;
        DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatoBR);
    }

    public static LocalDate formatarStringParaLocalDate(String dataBR) {
        if (dataBR == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dataBR, formatter);
    }

    public static String formatarValorMonetario(Double valor) {
        if(valor == null) return null;
        DecimalFormat formatoMonetario = new DecimalFormat("0.00");
        return "R$ " + formatoMonetario.format(valor);
    }

}
