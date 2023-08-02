package com.anervea.employee.utility;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.anervea.employee.exception.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;

@Component
public class FileOperation {
    private static final Logger LOG = LoggerFactory.getLogger(com.anervea.employee.utility.FileOperation.class);
//    private final Path fileStorageLocation;

    @Value("${aws.s3.bucket}")
    private String awsS3Bucket;

    @Value("${pre_filename}")
    private String preFilename;

    private AmazonS3 amazonS3;

    @Autowired
    public FileOperation(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider, String awsS3Bucket) {
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion.getName()).build();
        this.awsS3Bucket = awsS3Bucket;
    }

    public String uploadFile(MultipartFile multipartFile) throws Exception {
 FileUploadResponse fileUploadResponse = new FileUploadResponse();

        if (multipartFile == null) {
            return null;
        }

        // Normalize file name
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        //Check FileType If Pdf OR JPG OR PNG Then Save Otherwise Return null
        if (extension.equalsIgnoreCase("pdf") ||
                extension.equalsIgnoreCase("jpg") ||
                extension.equalsIgnoreCase("png") ||
                extension.equalsIgnoreCase("jpeg") ||
                extension.equalsIgnoreCase("webp") ||
                extension.equalsIgnoreCase("blob") ||
                extension.equalsIgnoreCase("xlsx")||
                extension.equalsIgnoreCase("xls") ||
                extension.equalsIgnoreCase("doc") ||
                extension.equalsIgnoreCase("docx") ||
                extension.equalsIgnoreCase("mp4") ||
                extension.equalsIgnoreCase("txt") || extension.equalsIgnoreCase("webm")||
                extension.equalsIgnoreCase("csv")){
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            try {
                String generateFileName = preFilename + "_" + Instant.now().toEpochMilli() + "." + extension;
                InputStream fileData = multipartFile.getInputStream();

                PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3Bucket, generateFileName,
                        fileData, new ObjectMetadata());

                this.amazonS3.putObject(putObjectRequest);

                String fileUrl = amazonS3.getUrl(awsS3Bucket, generateFileName).toExternalForm();

                fileUploadResponse.setFileName(generateFileName);
                fileUploadResponse.setFilePath(fileUrl);

                LOG.info(fileName + "  URL IS  " + fileUrl);
                return generateFileName;
            } catch (AmazonServiceException | IOException ex) {
                LOG.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
            }

        }else {
            //return fileUploadResponse;
            throw new Exception("File extension " + extension + " not supported!");
        }
        return null;
    }


    FileUploadResponse storeFile(MultipartFile multipartFile, String fileType) {
        FileUploadResponse fileUploadResponse = new  FileUploadResponse();

        if (multipartFile == null) {
            return fileUploadResponse;
        }

        // Normalize file name
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        //Check FileType If Pdf OR JPG OR PNG Then Save Otherwise Return null
        if (extension.equalsIgnoreCase("pdf") ||
                extension.equalsIgnoreCase("jpg") ||
                extension.equalsIgnoreCase("png") ||
                extension.equalsIgnoreCase("jpeg") ||
                extension.equalsIgnoreCase("blob") ||
                extension.equalsIgnoreCase("xlsx")||
                extension.equalsIgnoreCase("xls") ||
                extension.equalsIgnoreCase("doc")||
                extension.equalsIgnoreCase("docx")||
                extension.equalsIgnoreCase("txt")) {

            // Check if the file's name contains invalid characters
            if (extension.equals("blob")) {
                extension = "png";
            }
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            try {
                String generateFileName = fileType + "_" + Instant.now().toEpochMilli() + "." + extension;
                InputStream fileData = multipartFile.getInputStream();

                PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3Bucket, generateFileName,
                        fileData, new ObjectMetadata());

                this.amazonS3.putObject(putObjectRequest);

                String fileUrl = amazonS3.getUrl(awsS3Bucket, generateFileName).toExternalForm();

                fileUploadResponse.setFileName(generateFileName);
                fileUploadResponse.setFilePath(fileUrl);

                LOG.info(fileName + "  URL IS  " + fileUrl);

            } catch (AmazonServiceException | IOException ex) {
                LOG.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
            }

        }
        return fileUploadResponse;
    }

   FileUploadResponse storeVideo(MultipartFile multipartFile, String fileType) {
    FileUploadResponse fileUploadResponse = new FileUploadResponse();

        if (multipartFile == null) {
            return fileUploadResponse;
        }

        // Normalize file name
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        //Check FileType If Pdf OR JPG OR PNG Then Save Otherwise Return null
        if (extension.equalsIgnoreCase("mp4") || extension.equalsIgnoreCase("docx") ||
                extension.equalsIgnoreCase("pdf") || extension.equalsIgnoreCase("png")) {

            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            try {
                String generateFileName = fileType + "#" + Instant.now().toEpochMilli() + "." + extension;
                InputStream fileData = multipartFile.getInputStream();

                PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3Bucket, generateFileName,
                        fileData, new ObjectMetadata());

                this.amazonS3.putObject(putObjectRequest);

                String fileUrl = amazonS3.getUrl(awsS3Bucket, generateFileName).toExternalForm();

                fileUploadResponse.setFileName(generateFileName);
                fileUploadResponse.setFilePath(fileUrl);

                LOG.info(fileName + "  URL IS  " + fileUrl);

            } catch (AmazonServiceException | IOException ex) {
                LOG.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
            }
        }
        return fileUploadResponse;
    }

    FileUploadResponse storeMediaDetails(MultipartFile multipartFile, String fileType) throws Exception {
     FileUploadResponse fileUploadResponse = new FileUploadResponse();

        if (multipartFile == null) {
            return fileUploadResponse;
        }

        // Normalize file name
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        //Check FileType If Pdf OR JPG OR PNG Then Save Otherwise Return null
        String mediaType = checkForMediaTypeExists(extension);
        // Check if the file's name contains invalid characters
        if (fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }

        try {
            String generateFileName = fileType + "_" + Instant.now().toEpochMilli() + "." + extension;
            InputStream fileData = multipartFile.getInputStream();

            PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3Bucket, generateFileName,
                    fileData, new ObjectMetadata());

            this.amazonS3.putObject(putObjectRequest);

            String fileUrl = amazonS3.getUrl(awsS3Bucket, generateFileName).toExternalForm();

            fileUploadResponse.setFileName(generateFileName);
            fileUploadResponse.setFilePath(fileUrl);
            fileUploadResponse.setMediaType(mediaType);
            LOG.info(fileName + "  URL IS  " + fileUrl);

        } catch (AmazonServiceException | IOException ex) {
            LOG.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
        }


        return fileUploadResponse;
    }

    private String checkForMediaTypeExists(String extension) throws Exception {
        extension = extension.toLowerCase();
        switch (extension) {
            //Image Type Below
            case "tif":
                return "image";
            case "jpg":
                return "image";
            case "gif":
                return "image";
            case "png":
                return "image";
            //VideoTypes Below
            case "mp4":
                return "video";
            case "mov":
                return "video";
            case "wmv":
                return "video";
            case "avi":
                return "video";
            case "avchd":
                return "video";
            case "mkv":
                return "video";
            case "webm":
                return "video";
            case "mpeg":
                return "video";
            default:
                throw new Exception("No Media Specified For the Upload");
        }
    }

    public FileUploadResponse storeJobCommubicatonFile(MultipartFile multipartFile, String fileType) {
     FileUploadResponse fileUploadResponse = new FileUploadResponse();

        if (multipartFile == null) {
            return fileUploadResponse;
        }

        // Normalize file name
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        System.out.println("file name:-"+fileName);
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        //Check FileType If Pdf OR JPG OR PNG Then Save Otherwise Return null
        if (extension.equalsIgnoreCase("pdf") ||
                extension.equalsIgnoreCase("jpg") ||
                extension.equalsIgnoreCase("png") ||
                extension.equalsIgnoreCase("jpeg") ||
                extension.equalsIgnoreCase("blob") ||
                extension.equalsIgnoreCase("xlsx")||
                extension.equalsIgnoreCase("xls") ||
                extension.equalsIgnoreCase("doc")||
                extension.equalsIgnoreCase("docx")||
                extension.equalsIgnoreCase("txt")) {

            // Check if the file's name contains invalid characters
            if (extension.equals("blob")) {
                extension = "png";
            }
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            try {
                String generateFileName = fileName;
                InputStream fileData = multipartFile.getInputStream();

                PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3Bucket, generateFileName,
                        fileData, new ObjectMetadata());

                this.amazonS3.putObject(putObjectRequest);

                String fileUrl = amazonS3.getUrl(awsS3Bucket, generateFileName).toExternalForm();

                fileUploadResponse.setFileName(generateFileName);
                fileUploadResponse.setFilePath(fileUrl);

                LOG.info(fileName + "  URL IS  " + fileUrl);

            } catch (AmazonServiceException | IOException ex) {
                LOG.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
            }

        }
        return fileUploadResponse;
    }
}
