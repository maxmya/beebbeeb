package com.trixpert.beebbeeb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

@Configuration
public class LoggingConfigs {


    @Bean
    public String configStackTraceLogOutputFile() throws FileNotFoundException {
        PrintStream out = new PrintStream(new FileOutputStream("/home/beebbeeb/logs/trace.txt", true), true);
        System.setErr(out);
        return "DONE";
    }


}
