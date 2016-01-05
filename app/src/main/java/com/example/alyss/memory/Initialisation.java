package com.example.alyss.memory;


import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jazz on 09/11/15.
 */
public class Initialisation {



    public ArrayList init() {


        ArrayList l = new ArrayList();
        for(int j=0;j<2;j++) {
            for (int i = 0; i < 10; i++) {
                l.add(i);
            }
        }
        Collections.shuffle(l);


        return  l;
    }





}
