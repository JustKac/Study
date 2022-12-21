package br.com.segsat.restwhitspringbootandjava;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.segsat.restwhitspringbootandjava.exceptions.UnsupportedMathoperationException;

@RestController

public class MathController {

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(@PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo) throws Exception{

        if(!isNumeric(numberOne) || !isNumeric(numberTwo)){
            throw new UnsupportedMathoperationException("Please set a numeric value!");
        }
        
        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }

    private Double convertToDouble(String strNumber) {
        if (strNumber == null){
            return 0D;
        }
        String number = strNumber.replaceAll(",", ".");
        if (isNumeric(number)){
            return Double.parseDouble(number);
        }
        return 0D;
    }

    private boolean isNumeric(String strNumber) {
        if (strNumber == null){
            return false;
        }
        String number = strNumber.replaceAll(",", ".");

        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
