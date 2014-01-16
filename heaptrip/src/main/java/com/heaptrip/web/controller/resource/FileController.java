package com.heaptrip.web.controller.resource;

import com.heaptrip.domain.service.image.ImageService;
import com.heaptrip.util.http.Ajax;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.model.file.FileMeta;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 15.01.14
 * Time: 10:40
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class FileController extends ExceptionHandlerControler {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    LinkedHashMap<String, FileMeta> files = new LinkedHashMap<String, FileMeta>();
    FileMeta fileMeta = null;


    /**
     * ************************************************
     * URL: /rest/controller/upload
     * upload(): receives files
     *
     * @param request  : MultipartHttpServletRequest auto passed
     * @param response : HttpServletResponse auto passed
     * @return LinkedList<FileMeta> as json format
     *         **************************************************
     */
    @RequestMapping(value = "upload", headers = "content-type=multipart/*", method = {RequestMethod.POST, RequestMethod.HEAD})
    public
    @ResponseBody
    Map<String, ? extends Object> upload(MultipartHttpServletRequest request, HttpServletResponse response) {

        //1. build an iterator
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = null;


        //2. get each file
        while (itr.hasNext()) {


            //2.1 get next MultipartFile
            mpf = request.getFile(itr.next());
            System.out.println(mpf.getOriginalFilename() + " uploaded! " + files.size());

            //2.2 if files > 10 remove the first from the list
           // if (files.size() >= 10)
           //     files.pop();


            try {
                //fileMeta.setBytes(mpf.getBytes());

                // copy file to local disk (make sure the path "e.g. D:/temp/files" exists)
                //FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream("D:/temp/files/"+mpf.getOriginalFilename()));

                String id = imageService.saveImage(mpf.getOriginalFilename(), new ByteArrayInputStream(mpf.getBytes()));

                //2.3 create new fileMeta
                fileMeta = new FileMeta();
                fileMeta.setName(mpf.getOriginalFilename());
                fileMeta.setSize(mpf.getSize() / 1024 + " Kb");
                fileMeta.setType(mpf.getContentType());
                fileMeta.setUrl("./rest/get/" + id);
                fileMeta.setThumbnailUrl("./rest/get/" + id);
                fileMeta.setDeleteUrl("./rest/get/" + id);
                fileMeta.setDeleteType("DELETE");

                files.put(id, fileMeta);


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //2.4 add to files


        }
        // result will be like this
        // [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]

        Map result = new HashMap<String, Object>();

        result.put("files", files.values());

        return result;
    }


    /**
     * ************************************************
     * URL: /rest/controller/upload
     * upload(): receives files
     *
     * @param request  : MultipartHttpServletRequest auto passed
     * @param response : HttpServletResponse auto passed
     * @return LinkedList<FileMeta> as json format
     *         **************************************************
     */
    @RequestMapping(value = "upload", method = {RequestMethod.GET, RequestMethod.HEAD})
    public
    @ResponseBody
    Map<String, ? extends Object> upload() {

        Map result = new HashMap<String, Object>();

        result.put("files", files.values());

        return result;
    }

    /**
     * ************************************************
     * URL: /rest/controller/get/{value}
     * get(): get file as an attachment
     *
     * @param response : passed by the server
     * @param value    : value from the URL
     * @return void
     *         **************************************************
     */
    @RequestMapping(value = "get/{value}", method = {RequestMethod.POST, RequestMethod.GET, RequestMethod.HEAD})
    public void get(HttpServletResponse response, @PathVariable String value) {
        FileMeta getFile = files.get(value);
        try {
            response.setContentType(getFile.getType());
            response.setHeader("Content-disposition", "attachment; filename=\"" + getFile.getName() + "\"");


            InputStream in = imageService.getImage(value);

            FileCopyUtils.copy(IOUtils.toByteArray(in), response.getOutputStream());


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}



