package com.pid.proyecto;

import com.pid.proyecto.auxiliares.FileService;
import javax.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.pid.proyecto"})
public class ProyectoApplication implements CommandLineRunner {

    @Resource
    FileService fileService;

    public static void main(String[] args) {
        SpringApplication.run(ProyectoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        fileService.deleteAll();
        fileService.init();
    }


}
