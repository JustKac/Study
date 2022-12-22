package br.com.segsat.restwhitspringbootandjava.util;

import br.com.segsat.restwhitspringbootandjava.exceptions.UnsupportedMathoperationException;

public class MathOperations {
    

    public static Double sum(Double numberOne,
            Double  numberTwo){

        return numberOne + numberTwo;
    }

    public static Double sub(Double numberOne,
            Double  numberTwo){

        return numberOne - numberTwo;
    }

    public static Double mult(Double numberOne,
            Double  numberTwo){

        return numberOne * numberTwo;
    }

    public static Double div(Double numberOne,
            Double  numberTwo){

        return numberOne / numberTwo;
    }

    public static Double mean(Double numberOne,
            Double  numberTwo){

        return (numberOne + numberTwo) / 2;
    }

   
    public static Double sqrt(Double number){

        if (number < 0) {
            throw new UnsupportedMathoperationException("Please set a positive number!");
        }

        return Math.sqrt(number);
    }

}
