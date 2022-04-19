package com.spreadme.pdfsignator.service;

import com.spreadme.pdfsignator.pdfbox.signature.CreateSignature;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class PdfSignService {

    public File sign(MultipartFile pdfFile, MultipartFile certFile, String certificatePassword) throws IOException, GeneralSecurityException {
        File input = File.createTempFile("input_", pdfFile.getOriginalFilename());
        pdfFile.transferTo(input);
        File certificateFile = File.createTempFile("certificate_", certFile.getOriginalFilename());
        certFile.transferTo(certificateFile);
        File output = File.createTempFile("pdf_signatory_", ".pdf");
        CreateSignature.sign(certificateFile.getAbsolutePath(), certificatePassword, input.getAbsolutePath(), output.getAbsolutePath());
        return output;
    }

}
