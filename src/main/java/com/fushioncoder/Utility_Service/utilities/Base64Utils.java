package com.fushioncoder.Utility_Service.utilities;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;

@Slf4j
public class Base64Utils {

    public static String bufferedToBase64String(
            final BufferedImage img,
            final String formatName
    ) {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try (final OutputStream b64os = Base64.getEncoder().wrap(os)) {
            ImageIO.write(img, formatName, b64os);
        } catch (final IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
        return os.toString();
    }


    public static File buildFileFromString(
            String selfieImage,
            String[] allowedMimeTypes
    )
            throws Exception {
        HashMap<String, Object> fileDetails = doFileValidation(
                selfieImage,
                allowedMimeTypes
        );
        String fileExtension = (String) fileDetails.get("fileExtension");

        Path file = Files.createTempFile(
                "NEW" + System.currentTimeMillis(),
                "." + fileExtension
        );
        byte[] fileBytes = (byte[]) fileDetails.get("fileBytes");

        FileUtils.writeByteArrayToFile(file.toFile(), fileBytes);
        return file.toFile();
    }

    public static HashMap<String, Object> doFileValidation(
            String selfieImage,
            String[] allowedMimeTypes
    )
            throws Exception {
        HashMap<String, Object> fileDetails = null;
        try {
            fileDetails = getFileAndFileExtension(selfieImage);
            String fileExtension = "";
            if (fileDetails != null) {
                fileExtension = (String) fileDetails.get("fileExtension");
                if (!Arrays.asList(allowedMimeTypes).contains(fileExtension)) {
                    return fileDetails;
                }
            } else {
                throw new Exception("");
            }
        } catch (Exception e) {
            throw new Exception("");
        }
        return fileDetails;
    }

    public static HashMap<String, Object> getFileAndFileExtension(
            String base64ImageString
    ) {
        try {
            HashMap<String, Object> fileDet = new HashMap<>();
            String delims = "[,]";
            String[] parts = base64ImageString.split(delims);
            String imageString = parts.length > 1 ? parts[1] : parts[0];
            byte[] imageByteArray = Base64.getDecoder().decode(imageString);

            InputStream is = new ByteArrayInputStream(imageByteArray);

            String fileExtension = null;
            String mimeType = URLConnection.guessContentTypeFromStream(is);
            mimeType = mimeType != null ? mimeType : "jpg";
            String delimiter = "[/]";
            String[] tokens = mimeType.split(delimiter);
            System.out.println(Arrays.toString(tokens));
            fileExtension = tokens.length > 1 ? tokens[1] : tokens[0];
            fileDet.put("fileExtension", fileExtension);
            fileDet.put("fileBytes", imageByteArray);
            return fileDet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String comppressedImageString(
            String base64Img,
            String[] allowedMimeTypes
    ) {
        File newFile = null;
        try {
            newFile = buildFileFromString(base64Img, allowedMimeTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedImage compressedImage = resizeImage(newFile);

        return bufferedToBase64String(
                compressedImage,
                "jpg"
        );
    }

    public static BufferedImage compressImage(
            String imageString,
            int targetWidth,
            int targetHeight
    )
            throws Exception {
        BufferedImage bufferedImage = new BufferedImage(
                targetWidth,
                targetHeight,
                BufferedImage.TYPE_INT_RGB
        );

        Path newFile = Files.createTempFile(
                "NEW" + System.currentTimeMillis(),
                "." + "jpg"
        );
        File writeToFile = newFile.toFile();
        FileUtils.writeByteArrayToFile(writeToFile, imageString.getBytes());

        ImageIO.write(bufferedImage, "jpg", writeToFile);

        return Scalr.resize(bufferedImage, targetWidth);
    }

    public static BufferedImage resizeImage(File sourceFile) {
        try {
            BufferedImage bufferedImage = ImageIO.read(sourceFile);
            BufferedImage outputImage = Scalr.resize(bufferedImage, 400);
            Path path = Files.createTempFile(
                    FilenameUtils.getBaseName(sourceFile.getName()) +
                            "_" +
                            "400".toString(),
                    "." + FilenameUtils.getExtension(sourceFile.getName())
            );
            File newImageFile = path.toFile();
            ImageIO.write(outputImage, "jpg", newImageFile);
            outputImage.flush();
            return outputImage;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
