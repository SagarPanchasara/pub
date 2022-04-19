package com.spreadme.pdfsignator.controller;

import com.spreadme.pdfsignator.service.PdfSignService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public class PdfSignController {

    private final PdfSignService pdfSignService;

    @PostMapping("/sign")
    public ResponseEntity<Resource> sign(@RequestParam("pdf") MultipartFile pdfFile,
                                         @RequestParam("cert") MultipartFile certFile,
                                         @RequestParam("password") String password) throws GeneralSecurityException, IOException {
        var file = pdfSignService.sign(pdfFile, certFile, password);
        var resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
