/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.umass.ciir.dexp;

import java.io.PrintStream;
import java.util.List;

/**
 *
 * @author wkong
 */
public abstract class AppFunction {

    public abstract String getName();

    public abstract String getHelpString();

    public abstract List<Tuple> getParametersList(String [] args);
    
    public void splitRun(String [] args, PrintStream output) {
        
    }
    

    public void run(String[] args, PrintStream output) throws Exception {
        Tuple p = new Tuple();

        if (args.length == 1) {
            output.print(this.getHelpString());
            return;
        }

        splitRun(args, output);
    }

}
