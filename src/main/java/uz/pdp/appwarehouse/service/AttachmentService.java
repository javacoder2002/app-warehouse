package uz.pdp.appwarehouse.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.appwarehouse.entity.Attachment;
import uz.pdp.appwarehouse.entity.AttachmentContent;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.AttachmentContentRepository;
import uz.pdp.appwarehouse.repository.AttachmentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    @SneakyThrows
    public Result uploadFile(MultipartHttpServletRequest request) {

        //FILE NOMLARINI OLISH
        Iterator<String> fileNames = request.getFileNames();

        //fileNames.next() nomli file ni olish
        MultipartFile file = request.getFile(fileNames.next());

        //file attachment
        Attachment attachment = new Attachment();
        attachment.setName(file.getOriginalFilename());
        attachment.setSize(file.getSize());
        attachment.setContentType(file.getContentType());
        Attachment saveAttachment = attachmentRepository.save(attachment);

        //file content
        AttachmentContent attachmentContent = new AttachmentContent();
        attachmentContent.setBytes(file.getBytes());
        attachmentContent.setAttachment(saveAttachment);
        attachmentContentRepository.save(attachmentContent);

        return new Result(
                true,
                "attachment saved!",
                attachment.getId()
        );
    }

    public List<Attachment> getAll(){
        return attachmentRepository.findAll();
    }

    public Attachment getById(Integer id){
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        return optionalAttachment.isEmpty() ? new Attachment() : optionalAttachment.get();
    }

    @SneakyThrows
    public Result edite(Integer id, MultipartHttpServletRequest request){

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isEmpty())
            return new Result(false, "attachment not found!");

        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(id);
        if (optionalAttachmentContent.isEmpty())
            return new Result(false, "attachmentConnect not found");

        //FILE NOMLARINI OLISH
        Iterator<String> fileNames = request.getFileNames();

        //fileNames.next() nomli file ni olish
        MultipartFile file = request.getFile(fileNames.next());

        //file attachment
        Attachment attachment = optionalAttachment.get();
        attachment.setName(file.getOriginalFilename());
        attachment.setSize(file.getSize());
        attachment.setContentType(file.getContentType());
        Attachment saveAttachment = attachmentRepository.save(attachment);

        //file content
        AttachmentContent attachmentContent = optionalAttachmentContent.get();
        attachmentContent.setBytes(file.getBytes());
        attachmentContent.setAttachment(saveAttachment);
        attachmentContentRepository.save(attachmentContent);

        return new Result(
                true,
                "attachment edited!",
                attachment.getId()
        );

    }

    public Result delete(Integer id){
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isEmpty())
            return new Result(false, "attachment not found!");

        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(id);
        if (optionalAttachmentContent.isEmpty())
            return new Result(false, "attachmentContent not found");

        attachmentContentRepository.delete(optionalAttachmentContent.get());

        attachmentRepository.deleteById(id);
        return new Result(true, "attachment deleted!");
    }

}
