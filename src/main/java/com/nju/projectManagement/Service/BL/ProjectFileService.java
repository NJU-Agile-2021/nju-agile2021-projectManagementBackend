package com.nju.projectManagement.Service.BL;
import com.nju.projectManagement.VO.*;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Toby Fu
 * @date 2022/4/16
 **/
@Service
public interface ProjectFileService {

    public ResponseVO<Boolean> uploadFile(UploadFileForm uploadFileForm) throws IOException;

    public ResponseVO<Boolean> deleteFile(DeleteFileForm deleteFileForm);


    public void downloadFile(Integer fileId, HttpServletResponse res) throws Exception;

    public ResponseVO<List<ProjectFileInfoVO>> getFileList(ProjectIdAndUserIdForm projectIdAndUserIdForm);


    public ResponseVO<Boolean> modifyFilePermission(ModifyFilePermissionVO modifyFilePermissionVO);
}
