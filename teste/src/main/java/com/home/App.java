package com.home;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        List<Integer> meu = new ArrayList<>();

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 2, 3, 2, 5, 4);

        for (Integer n : list) {
            if(!meu.contains(n)){
                meu.add(n);
            }
        }

        for (Integer i : meu) {
            int count = 0;
            for (Integer j : list) {
                if (i == j){
                    count++;
                }
            }

            System.out.println(String.format("%s - %s", i, count));
        }

    }
}
