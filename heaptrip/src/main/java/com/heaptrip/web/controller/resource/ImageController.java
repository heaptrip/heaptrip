package com.heaptrip.web.controller.resource;

import com.heaptrip.domain.service.image.ImageService;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Controller
public class ImageController extends ExceptionHandlerControler {


    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @RequestMapping("/image")
    public ResponseEntity<byte[]> getImage(@RequestParam(value = "imageId") String imageId) throws IOException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        InputStream in = imageService.getImage(imageId);
        return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "image/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    ModelMap uploadImage(MultipartHttpServletRequest multipart) throws IOException {
        Map<String, MultipartFile> files = multipart.getFileMap();
        String result = null;
        for (MultipartFile file : files.values()) {
            logger.info("Received image: {}, size {}", file.getOriginalFilename(), file.getSize());
            result = imageService.saveImage(file.getOriginalFilename(), file.getInputStream());
            break;
        }

        return new ModelMap("fileId", result);
    }

}
