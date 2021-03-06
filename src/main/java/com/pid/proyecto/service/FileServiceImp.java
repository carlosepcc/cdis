package com.pid.proyecto.service;

import com.pid.proyecto.auxiliares.FileService;
import java.io.File;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;

@Service
public class FileServiceImp implements FileService {

    //Nombre de la carpeta donde vamos a almacenar los archivos
    //Se crea a nivel de raiz la carpeta
    private final Path root = Paths.get("ARCHIVOS");
    private final Path rootCasos = Paths.get("ARCHIVOS/CASOS_IDCOMISION_IDDENUNCIA");
    private final Path rootResoluciones = Paths.get("ARCHIVOS/RESOLUCIONES");

    @Autowired
    CasoService casoService;

    @Override
    public void init() {

        File fileCasos = rootCasos.toFile();
        File fileResoluciones = rootResoluciones.toFile();
        File file = root.toFile();
        
        if (!file.exists()) {
            try {
                Files.createDirectory(root);
            } catch (IOException ex) {
                throw new RuntimeException("NO SE PUEDE INICIALIZAR LA CARPETA: " + file.getName());
            }
        }

        if (!fileCasos.exists()) {
            try {
                Files.createDirectory(rootCasos);
            } catch (IOException ex) {
                throw new RuntimeException("NO SE PUEDE INICIALIZAR LA CARPETA: " + fileCasos.getName());
            }
        }
        if (!fileResoluciones.exists()) {
            try {
                Files.createDirectory(rootResoluciones);
            } catch (IOException ex) {
                throw new RuntimeException("NO SE PUEDE INICIALIZAR LA CARPETA: " + fileResoluciones.getName());
            }
        }
    }

    @Override
    public void save(MultipartFile file, Path ruta) {
        try {
            //copy (que queremos copiar, a donde queremos copiar)
            Files.copy(file.getInputStream(),
                    ruta.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException("YA EXISTE ESTE ARCHIVO " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = rootCasos.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("No se puede leer el archivo ");
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll(Path root) {
        FileSystemUtils.deleteRecursively(root.toFile());
    }
    @Override
    public Stream<Path> loadAll() {
        //Files.walk recorre nuestras carpetas (uploads) buscando los archivos
        // el 1 es la profundidad o nivel que queremos recorrer
        // :: Referencias a metodos
        // Relativize sirve para crear una ruta relativa entre la ruta dada y esta ruta
        try {
            return Files.walk(this.rootCasos, 1).filter(path -> !path.equals(this.rootCasos))
                    .map(this.rootCasos::relativize);
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException("No se pueden cargar los archivos ");
        }
    }

    @Override
    public String deleteFile(String filename) {
        try {
            Boolean delete = Files.deleteIfExists(this.root.resolve(filename));
            return "Borrado";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error Borrando ";
        }
    }

}
