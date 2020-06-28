package br.com.db1.meritmoney.storage;

import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Component
public class ImagesService {

    private final String bucketImages;
    private final MinioClient minioClient;

    protected ImagesService(@Value("${application.minio.bucket.images}") String bucketImages,
                            MinioClient minioClient) {
        this.bucketImages = bucketImages;
        this.minioClient = minioClient;
    }

    public String salvarFoto(MultipartFile foto, String id, EImagesNames imagesName) {
        confirmBucket();
        return uploadFile(foto, id, imagesName);
    }

    private String uploadFile(MultipartFile foto, String id, EImagesNames imagesName) {
        try {
            String objectName = createImageName(id, imagesName);
            minioClient.putObject(bucketImages, objectName, foto.getInputStream(), getOptions(foto));
            return minioClient.getObjectUrl(bucketImages, objectName);
        } catch (Exception e) {
            //todo mudar para uma exception especifica
            throw new RuntimeException(e);
        }
    }

    private PutObjectOptions getOptions(MultipartFile foto) {
        PutObjectOptions putObjectOptions = new PutObjectOptions(foto.getSize(), 0);
        putObjectOptions.setContentType(foto.getContentType());
        HashMap<String, String> headers = new HashMap<>();
        headers.put("original_file_name", foto.getOriginalFilename());
        putObjectOptions.setHeaders(headers);
        return putObjectOptions;
    }

    private String createImageName(String id, EImagesNames imagesName) {
        String folder = imagesName.getDesc();
        String objectName = imagesName.getDesc() + "_" + id;
        return folder + "." + objectName;
    }

    private void confirmBucket() {
        try {
            boolean isExist = minioClient.bucketExists(bucketImages);
            if(!isExist) {
                minioClient.makeBucket(bucketImages);
            }
        } catch (Exception e) {
            //todo mudar para uma exception especifica
            throw new RuntimeException(e);
        }
    }
}
