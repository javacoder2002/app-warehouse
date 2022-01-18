package uz.pdp.appwarehouse.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.appwarehouse.entity.Attachment;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.AttachmentService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @SneakyThrows
    @PostMapping("/upload")
    public Result upload(MultipartHttpServletRequest request) {
        return attachmentService.uploadFile(request);
    }

    @GetMapping
    public List<Attachment> getAll() {
        return attachmentService.getAll();
    }

    @GetMapping("/{id}")
    public Attachment getById(@PathVariable Integer id) {
        return attachmentService.getById(id);
    }

    @PutMapping("/{id}")
    public Result edite(@PathVariable Integer id, MultipartHttpServletRequest request) {
        return attachmentService.edite(id, request);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return attachmentService.delete(id);
        //return new Result(false, "Attachment product o'chsagina o'chadi!");
    }

}
