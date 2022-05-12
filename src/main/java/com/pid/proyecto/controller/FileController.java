package com.pid.proyecto.controller;

import com.pid.proyecto.Json.Crear.JsonCrearDeclaracion;
import com.pid.proyecto.Json.JsonFile;
import com.pid.proyecto.auxiliares.FileService;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.auxiliares.SesionDetails;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Declaracion/modificar")
@CrossOrigin("*")
public class FileController {
    //Inyectamos el servicio

    @Autowired
    FileService fileService;

    @Autowired
    SesionDetails sesionDetails;

    @PostMapping("/upload")
    public ResponseEntity<Mensaje> uploadFiles(
            @RequestParam("files") List<MultipartFile> files
    ) {
        List<String> message = new LinkedList<>();

        String nombreCarpeta = sesionDetails.getUsuario();

        Path root = Paths.get("uploads/" + nombreCarpeta);

        File carpeta = root.toFile();

        if (!carpeta.exists()) {
            try {
                Files.createDirectory(root);
            } catch (IOException ex) {
                throw new RuntimeException("No se puede inicializar la carpeta " + nombreCarpeta);
            }
        }

        List<String> fileNames = new ArrayList<>();

        files.stream().forEach(file -> {
            fileService.save(file, root);
            fileNames.add(LocalDate.now().toString() + file.getOriginalFilename());
        });

        message.add("Se subieron los archivos correctamente " + fileNames);

        return ResponseEntity.status(HttpStatus.OK).body(new Mensaje(message.toString()));

    }

    @GetMapping("/files")
    public ResponseEntity<List<JsonFile>> getFiles() {
        List<JsonFile> fileInfos = fileService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFile",
                    path.getFileName().toString()).build().toString();
            return new JsonFile(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileService.load(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/delete/{filename:.+}")
    public ResponseEntity<Mensaje> deleteFile(@PathVariable String filename) {
        String message = "";
        try {
            message = fileService.deleteFile(filename);
            return ResponseEntity.status(HttpStatus.OK).body(new Mensaje(message));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Mensaje(message));
        }
    }

}
