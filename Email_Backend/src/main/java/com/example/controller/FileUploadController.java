package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.entity.UploadedFile;
import com.example.repository.UploadedFileRepository;
import com.example.services.FileStorageService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:4200")
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UploadedFileRepository uploadedFileRepository;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        if (file.isEmpty()) {
            response.put("message", "Please select a file to upload.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            // Check if a file with the same name already exists
            if (uploadedFileRepository.existsByFileName(file.getOriginalFilename())) {
                response.put("message", "A file with this name already exists. Please use a unique file name.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Store the file and get the path
            String filePath = fileStorageService.storeFile(file);

            // Save file information in database
            UploadedFile uploadedFile = new UploadedFile(file.getOriginalFilename(), filePath);
            uploadedFileRepository.save(uploadedFile);

            response.put("message", "File uploaded successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IOException e) {
            response.put("message", "Could not store the file. Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


 // Get file names
    @GetMapping("/names")
    public ResponseEntity<List<String>> getFileNames() {
        List<UploadedFile> files = uploadedFileRepository.findAll();
        List<String> fileNames = new ArrayList<>();
        for (UploadedFile file : files) {
            fileNames.add(file.getFileName());
        }
        return ResponseEntity.ok(fileNames);
    }

    // Get data of a specific file
    @GetMapping("/data/{fileName}")
    public ResponseEntity<List<List<String>>> getFileData(@PathVariable String fileName) throws IOException {
        List<UploadedFile> files = uploadedFileRepository.findAllByFileName(fileName);

        if (files.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        String filePath = files.get(0).getFilePath();
        List<List<String>> data = fileStorageService.readDataFromExcel(filePath);
        return ResponseEntity.ok(data);
    }
    
    
}