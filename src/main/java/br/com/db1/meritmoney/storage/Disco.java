package br.com.db1.meritmoney.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.processing.FilerException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class Disco {

    @Value("${usuario.disco.raiz}")
    private String raiz;

    @Value("${usuario.disco.diretorio-foto}")
    private String diretorioFotos;

    public String salvarFoto(MultipartFile foto) throws IOException {
        return this.salvar(this.diretorioFotos, foto);
    }

    public String salvar(String diretorio, MultipartFile arquivo) throws IOException {
        Path diretorioPath = Paths.get(this.raiz, diretorio);
        Path arquivoPath = diretorioPath.resolve(arquivo.getOriginalFilename()
                .replaceAll(" ", "_"));

        try {
            Files.createDirectories(diretorioPath);
            arquivo.transferTo(arquivoPath.toFile());
            return arquivoPath.toString();
        } catch(IOException e) {
            throw new FilerException("");
        }
    }
}
