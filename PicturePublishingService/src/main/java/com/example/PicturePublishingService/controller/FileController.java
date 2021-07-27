package com.example.PicturePublishingService.controller;

import com.example.PicturePublishingService.entity.Pictrue;
import com.example.PicturePublishingService.entity.UserInfo;
import com.example.PicturePublishingService.models.ErrorResponse;
import com.example.PicturePublishingService.models.UploadFileResponse;
import com.example.PicturePublishingService.repository.PictrueRepository;
import com.example.PicturePublishingService.repository.UserRepository;
import com.example.PicturePublishingService.services.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@RestController
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileStorageService fileStorageService;

    private final PictrueRepository pictrueRepository;

    private final UserRepository userRepository;

    @Autowired
    public FileController(FileStorageService fileStorageService, PictrueRepository pictrueRepository, UserRepository userRepository) {
        this.fileStorageService = fileStorageService;
        this.pictrueRepository = pictrueRepository;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    @RequestMapping(method = RequestMethod.POST, consumes = {"multipart/form-data"},path = "/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("description") String description,
                                         @RequestParam("category") String category,
                                         @RequestParam("user") Long userid
                                         ){
        if(file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png") || file.getContentType().equals("image/gif") ) {
        String fileName = fileStorageService.storeFile(file);
            logger.info(fileName);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

            Optional<UserInfo> pictureUser = userRepository.findById(userid);
            if(pictureUser.isEmpty())
                throw new IllegalStateException("No User Found") ;
            logger.info(pictureUser.get().toString());
            pictrueRepository.save(Pictrue.builder()
                    .description(description).category(category).fileurl(fileDownloadUri).userInfo(pictureUser.get()).status("pending")
                    .build());

            return new UploadFileResponse(fileName, fileDownloadUri,
                    file.getContentType(), file.getSize());
        }else{
                return new UploadFileResponse("This File not accepted", "Un Supported Type",
                    file.getContentType(), file.getSize());
      }
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, Files.probeContentType(resource.getFile().toPath())+"; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
