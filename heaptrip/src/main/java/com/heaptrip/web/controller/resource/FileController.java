package com.heaptrip.web.controller.resource;

import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.service.image.GridFileService;
import com.heaptrip.domain.service.image.ImageService;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.model.file.FileMeta;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private ImageService imageService;

    @Autowired
    private GridFileService gridFileService;

    /**
     * ************************************************
     * URL: /rest/controller/upload
     * upload(): receives files
     *
     * @param request : MultipartHttpServletRequest auto passed
     * @return LinkedList<FileMeta> as json format
     *         **************************************************
     */
    @RequestMapping(value = "upload", headers = "content-type=multipart/*", method = {RequestMethod.POST, RequestMethod.HEAD})
    public
    @ResponseBody
    Map<String, ? extends Object> upload(MultipartHttpServletRequest request) throws Exception {

        Stack<FileMeta> files = new Stack<>();

        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf;

        List<FileMeta> fileMetaList = new ArrayList<>();

        if (itr.hasNext()) {

            while (itr.hasNext()) {

                FileMeta fileMeta = new FileMeta();

                mpf = request.getFile(itr.next());


                String imageType = request.getParameter("imageType");


                if (imageType != null) {
                    Image image = imageService.addImage(ImageEnum.valueOf(imageType), mpf.getOriginalFilename(), new ByteArrayInputStream(mpf.getBytes()));
                    fileMeta.setName(mpf.getOriginalFilename());
                    fileMeta.setSize(mpf.getSize() / 1024 + " Kb");
                    fileMeta.setType(mpf.getContentType());
                    fileMeta.setUrl("./rest/get/" + image.getRefs().getMedium());
                    fileMeta.setThumbnailUrl("./rest/get/" + image.getRefs().getSmall());
                    if (image.getRefs().getFull() != null)
                        fileMeta.setHighResolutionUrl("./rest/get/" + image.getRefs().getFull());
                    fileMeta.setDeleteUrl("./rest/del/" + image.getId());
                    fileMeta.setDeleteType("DELETE");
                    fileMeta.setId(image.getId());
                }


                fileMetaList.add(fileMeta);

                files.push(fileMeta);

                System.out.println(mpf.getOriginalFilename() + " uploaded! " + files.size());

                return Collections.singletonMap("files", fileMetaList);
            }
        }

        return Collections.singletonMap("files", fileMetaList);
    }


    /* /**
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
    Map<String, ? extends Object> upload(@RequestParam(value = "ids") String ids) {

        if (ids == null || ids.isEmpty())
            return Collections.singletonMap("files", Collections.emptyList());

        List<FileMeta> fileMetaList = new ArrayList<>();

        for (String id : ids.split(",")) {
            FileMeta fileMeta = new FileMeta();

            Image image = imageService.getImageById(id);

            fileMeta.setName(id);
            fileMeta.setSize(1000 / 1024 + " Kb");
            fileMeta.setType("none");
            fileMeta.setUrl("./rest/get/" + image.getRefs().getMedium());
            fileMeta.setThumbnailUrl("./rest/get/" + image.getRefs().getSmall());
            if (image.getRefs().getFull() != null)
                fileMeta.setHighResolutionUrl("./rest/get/" + image.getRefs().getFull());
            fileMeta.setDeleteUrl("./rest/del/" + image.getId());
            fileMeta.setDeleteType("DELETE");

            fileMeta.setId(id);

            fileMetaList.add(fileMeta);
        }


        return Collections.singletonMap("files", fileMetaList);


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
        // FileMeta getFile = files.get(value);
        try {
            //response.setContentType(getFile.getType());
            response.setHeader("Content-disposition", "attachment; filename=\"" + value + "\"");

            InputStream in = gridFileService.getFile(value);

            FileCopyUtils.copy(IOUtils.toByteArray(in), response.getOutputStream());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}



