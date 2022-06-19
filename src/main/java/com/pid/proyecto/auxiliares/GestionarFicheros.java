package com.pid.proyecto.auxiliares;

import com.pid.proyecto.Json.Crear.JsonCrearDictamen;
import com.pid.proyecto.entity.Caso;
import com.pid.proyecto.entity.CasoPK;
import com.pid.proyecto.entity.Declaracion;
import com.pid.proyecto.entity.DeclaracionPK;
import com.pid.proyecto.entity.Resolucion;
import com.pid.proyecto.service.DeclaracionService;
import com.pid.proyecto.service.FileServiceImp;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class GestionarFicheros {

    @Autowired
    DeclaracionService declaracionService;

    @Autowired
    SesionDetails sesionDetails;

    @Autowired
    FileServiceImp fileServiceImp;

    public Declaracion GestionarModificarDeclaracion(Declaracion declaracion, List<MultipartFile> files) {
        // RUTA HACIA LA CARPETA DE NUESTRA DECLARACION
        Path root = Paths.get("ARCHIVOS/CASOS_IDCOMISION_IDDENUNCIA/"
                + "CASO_" + declaracion.getDeclaracionPK().getCasocomision() + "_"
                + declaracion.getDeclaracionPK().getCasodenuncia() + "/DECLARACIONES/" + sesionDetails.getUsuario());

        // CREAMOS UNA CARPETA CON NUESTRA RUTA
        File carpeta = root.toFile();

        // VERIFICAMOS QUE YA EXISTA ESA CARPETA, SI NO EXISTE LA CREAMOS
        if (!carpeta.exists()) {
            try {
                Files.createDirectory(root);
            } catch (IOException ex) {
                throw new RuntimeException("NO SE PUEDE CREAR LA CARPETA: " + carpeta.getName());
            }
        }

        files.stream().forEach(file -> {
            fileServiceImp.save(file, root);
        });
        declaracion.setDeclaracion(carpeta.getPath());
        return declaracion;
    }

    public Caso GestionarCrearDictamen(Caso caso, List<MultipartFile> files) {
        CasoPK PK = caso.getCasoPK();
        String carpetaDictamen = "DICTAMEN";
        Path root = Paths.get("ARCHIVOS/CASOS_IDCOMISION_IDDENUNCIA/" + "CASO_" + PK.getComision() + "_" + PK.getDenuncia() + "/" + carpetaDictamen);

        File carpeta = root.toFile();

        if (!carpeta.exists()) {
            try {
                Files.createDirectory(root);
            } catch (IOException ex) {
                throw new RuntimeException("NO SE PUEDE CREAR LA CARPETA: " + carpetaDictamen);
            }
        }

        files.stream().forEach(file -> {
            fileServiceImp.save(file, root);
        });

        caso.setDictamen(carpeta.getPath());
        return caso;

    }

    public String CrearCarpetaCaso(Caso caso) {
        String carpetaCaso = "CASO_" + caso.getCasoPK().getComision() + "_" + caso.getCasoPK().getDenuncia();
        Path root = Paths.get("ARCHIVOS/CASOS_IDCOMISION_IDDENUNCIA/" + carpetaCaso);

        File carpeta = root.toFile();

        if (!carpeta.exists()) {
            try {
                Files.createDirectory(root);
            } catch (IOException ex) {
                throw new RuntimeException("NO SE PUEDE CREAR LA CARPETA: " + carpetaCaso);
            }
        }

        return carpetaCaso;

    }

    public void CrearCarpetaDeclaracionesExpedientes(Declaracion declaracion) {
        String carpetaCaso = "CASO_" + declaracion.getDeclaracionPK().getCasocomision() + "_" + declaracion.getDeclaracionPK().getCasodenuncia();

        Path root = Paths.get("ARCHIVOS/CASOS_IDCOMISION_IDDENUNCIA/" + carpetaCaso + "/DECLARACIONES");

        File carpeta = root.toFile();

        if (!carpeta.exists()) {
            try {
                Files.createDirectory(root);
            } catch (IOException ex) {
                throw new RuntimeException("NO SE PUEDE CREAR LA CARPETA: " + "DECLARACIONES");
            }
        }

        root = Paths.get("ARCHIVOS/CASOS_IDCOMISION_IDDENUNCIA/" + carpetaCaso + "/EXPEDIENTES");
        carpeta = root.toFile();
        if (!carpeta.exists()) {
            try {
                Files.createDirectory(root);
            } catch (IOException ex) {
                throw new RuntimeException("NO SE PUEDE CREAR LA CARPETA: " + "EXPEDIENTES");
            }
        }
    }

    public Declaracion GestionarCrearExpediente(Declaracion declaracion, List<MultipartFile> files) {

        DeclaracionPK PK = declaracion.getDeclaracionPK();
        String carpetaDeclaracion = PK.getUsuario();
        Path root = Paths.get("ARCHIVOS/CASOS_IDCOMISION_IDDENUNCIA/" + "CASO_" + PK.getCasocomision() + "_" + PK.getCasodenuncia() + "/EXPEDIENTES/" + carpetaDeclaracion);

        File carpeta = root.toFile();

        if (!carpeta.exists()) {
            try {
                Files.createDirectory(root);
            } catch (IOException ex) {
                throw new RuntimeException("NO SE PUEDE CREAR LA CARPETA: " + carpetaDeclaracion);
            }
        }

        files.stream().forEach(file -> {
            fileServiceImp.save(file, root);
        });

        declaracion.setDeclaracion(carpeta.getPath());
        
        return declaracion;
    }

    public Resolucion GestionarCrearResolucion(String ano, MultipartFile file) {

        Resolucion resolucion = new Resolucion();
        
        Path root = Paths.get("ARCHIVOS/RESOLUCIONES/RESOLUCION_" + ano);

        File carpeta = root.toFile();

        if (!carpeta.exists()) {
            try {
                Files.createDirectory(root);
            } catch (IOException ex) {
                throw new RuntimeException("NO SE PUEDE CREAR LA CARPETA: " + carpeta);
            }
        }

        
        
        fileServiceImp.save(file, root);
        
        resolucion.setUrl(carpeta.getPath());
        return resolucion;
    }

}
