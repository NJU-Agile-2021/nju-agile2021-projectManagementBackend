package com.nju.projectManagement.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author Toby Fu
 * @date 2022/4/16
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileForm {

    private Integer projectId;

    private Integer uploadUserId;

    private MultipartFile multipartFile;
}
