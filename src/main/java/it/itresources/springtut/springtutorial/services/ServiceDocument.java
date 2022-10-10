package it.itresources.springtut.springtutorial.services;

import it.itresources.springtut.springtutorial.entity.DocumentEntity;
import it.itresources.springtut.springtutorial.model.dto.DocumentListDTO;
import it.itresources.springtut.springtutorial.model.request.FileUploadRequest;

import java.util.List;
import java.util.Optional;

public interface ServiceDocument {
    public Optional<DocumentEntity> uploadDocument(DocumentEntity document);
    public Optional<DocumentEntity> downloadDocument(Long id);
    public Boolean teacherUpload(FileUploadRequest request, Long id);
    public List<DocumentListDTO> downloadDocumentsList (Long id);


}
