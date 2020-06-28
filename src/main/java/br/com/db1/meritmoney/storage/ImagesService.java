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

    public ImageFileVO salvarFoto(MultipartFile foto, String id, EImagesNames imagesName) {
        confirmBucket();
        return uploadFile(foto, id, imagesName);
    }

    private ImageFileVO uploadFile(MultipartFile foto, String id, EImagesNames imagesName) {
        ImageFileVO imageFileVO = new ImageFileVO();
        imageFileVO.setContentType(foto.getContentType());
        imageFileVO.setSize(foto.getSize());
        imageFileVO.setOriginalName(foto.getOriginalFilename());
        String objectName = createImageName(id, imagesName, foto);
        imageFileVO.setName(objectName);

        try {
            PutObjectOptions options = getOptions(imageFileVO);
            minioClient.putObject(bucketImages, objectName, foto.getInputStream(), options);
            String fullUrl = minioClient.presignedGetObject(bucketImages, objectName);
            String relativePath = getRelativePath(bucketImages, fullUrl);
            imageFileVO.setUrl(fullUrl);
            imageFileVO.setRelativePath(relativePath);
            return imageFileVO;
        } catch (Exception e) {
            //todo mudar para uma exception especifica
            throw new RuntimeException(e);
        }
    }

    private String getRelativePath(String bucketName, String fullUrl) {
        int indexOfRelative = fullUrl.indexOf("/" + bucketName);
        return fullUrl.substring(indexOfRelative);
    }

    private PutObjectOptions getOptions(ImageFileVO imageFileVO) {
        PutObjectOptions putObjectOptions = new PutObjectOptions(imageFileVO.getSize(), 0);
        putObjectOptions.setContentType(imageFileVO.getContentType());
        HashMap<String, String> headers = new HashMap<>();
        headers.put("original_file_name", imageFileVO.getOriginalName());
        putObjectOptions.setHeaders(headers);
        return putObjectOptions;
    }

    private String createImageName(String id, EImagesNames imagesName, MultipartFile foto) {
        String ext = getFileExt(foto.getOriginalFilename());
        String folder = imagesName.getDesc();
        String objectName = imagesName.getDesc() + "_" + id;
        return folder + "/" + objectName + ext;
    }

    private String getFileExt(String name) {
        int dotIndex = name.lastIndexOf(".");
        if(dotIndex > -1) {
            return name.substring(dotIndex);
        }
        return "";
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
