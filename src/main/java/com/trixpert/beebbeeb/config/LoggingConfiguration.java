package com.trixpert.beebbeeb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


@Configuration
public class LoggingConfiguration {

    @Bean
    public String setupOutputFile() throws FileNotFoundException {
        PrintStream out = new PrintStream(new FileOutputStream("logs/output.txt", true), true);
        System.setOut(out);
        return "DONE";
    }

    @Bean
    public String setupTraceFile() throws FileNotFoundException {
        PrintStream out = new PrintStream(new FileOutputStream("logs/trace.txt", true), true);
        System.setErr(out);
        return "DONE";
    }


}
