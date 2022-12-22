package br.com.segsat.restwhitspringbootandjava.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.segsat.restwhitspringbootandjava.exceptions.UnsupportedMathoperationException;
import br.com.segsat.restwhitspringbootandjava.util.MathOperations;
import br.com.segsat.restwhitspringbootandjava.util.NumberConverter;

@RestController

public class MathController {

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(@PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo) throws Exception {

        return MathOperations.sum(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    }

    @GetMapping("/sub/{numberOne}/{numberTwo}")
    public Double sub(@PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo) throws Exception {

        return MathOperations.sub(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    }

    @GetMapping("/mult/{numberOne}/{numberTwo}")
    public Double mult(@PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo) throws Exception {

        return MathOperations.mult(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    }

    @GetMapping("/div/{numberOne}/{numberTwo}")
    public Double div(@PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo) throws Exception {

        return MathOperations.div(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    }

    @GetMapping("/mean/{numberOne}/{numberTwo}")
    public Double mean(@PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo) throws Exception {

        return MathOperations.mean(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    }

    @GetMapping("/sqrt/{number}")
    public Double sqrt(@PathVariable(value = "number") String number) throws Exception {

        if (NumberConverter.convertToDouble(number) < 0) {
            throw new UnsupportedMathoperationException("Please set a positive number!");
        }

        return MathOperations.sqrt(NumberConverter.convertToDouble(number));
    }

}
