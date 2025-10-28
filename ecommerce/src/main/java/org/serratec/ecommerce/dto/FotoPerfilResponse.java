package org.serratec.ecommerce.dto;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

public class FotoPerfilResponse {
    private Resource resource;
    private MediaType mediaType;

    public FotoPerfilResponse(Resource resource, MediaType mediaType) {
        this.resource = resource;
        this.mediaType = mediaType;
    }

    public Resource getResource() {
        return resource;
    }

    public MediaType getMediaType() {
        return mediaType;
    }
}
