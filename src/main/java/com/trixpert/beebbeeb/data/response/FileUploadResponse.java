package com.trixpert.beebbeeb.data.response;

import com.trixpert.beebbeeb.data.to.PhotoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileUploadResponse {
    private PhotoDTO photo;
}
