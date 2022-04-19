package com.spreadme.pdfsignator.service;

import com.spreadme.pdfsignator.pdfbox.signature.CreateSignature;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class PdfSignService {

    public File sign(MultipartFile pdfFile, MultipartFile certFile, String certificatePassword) throws IOException, GeneralSecurityException {
        var input = File.createTempFile("input_", pdfFile.getOriginalFilename());
        pdfFile.transferTo(input);
        var certificateFile = File.createTempFile("certificate_", certFile.getOriginalFilename());
        certFile.transferTo(certificateFile);
        File output = File.createTempFile("pdf_signatory_", ".pdf");
        CreateSignature.sign(certificateFile.getAbsolutePath(), certificatePassword, input.getAbsolutePath(), output.getAbsolutePath());
        return output;
    }

    public static File demo() throws IOException, GeneralSecurityException {
        var input = new File("/Users/sagar.panchasara/Downloads/sample.pdf");
        var certificateFile = new File("/Users/sagar.panchasara/Downloads/sign.pfx");
        File output = File.createTempFile("pdf_signatory_", ".pdf");

        String certificatePassword = "123";
        CreateSignature.sign(certificateFile.getAbsolutePath(), certificatePassword, input.getAbsolutePath(), output.getAbsolutePath());
        return output;
    }

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        System.out.println(demo());
    }
}
