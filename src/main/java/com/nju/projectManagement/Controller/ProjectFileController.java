package com.nju.projectManagement.Controller;

import com.nju.projectManagement.Service.BL.ProjectFileService;
import com.nju.projectManagement.VO.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * @author Toby Fu
 * @date 2022/4/16
 **/
@Tag(name = "project file",description = "项目文件上传下载等API")
@RestController
@RequestMapping(value = "/api/projectFile")
public class ProjectFileController {

    @Autowired
    ProjectFileService projectFileService;

    @Operation(summary = "上传文件")
    @PostMapping(value = "/uploadFile")
    public ResponseVO<Boolean> uploadFile(@RequestParam Integer projectId, @RequestParam Integer userId, @RequestParam MultipartFile file) throws IOException {
        return projectFileService.uploadFile(new UploadFileForm(projectId, userId, file));
    }

    @Operation(summary = "删除文件（管理员）")
    @PostMapping(value = "/deleteFile", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<Boolean> deleteFile(@RequestBody DeleteFileForm deleteFileForm) {
        return projectFileService.deleteFile(deleteFileForm);
    }

    @Operation(summary = "下载文件")
    @GetMapping(value = "/downloadFile")
    public void downloadFile(@RequestParam Integer fileId, HttpServletResponse res) throws Exception {
        projectFileService.downloadFile(fileId, res);
    }

    @Operation(summary = "查看项目文件列表, 会返回该用户有权限查看的文件的文件信息列表")
    @PostMapping(value = "/getFileList", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<List<ProjectFileInfoVO>> getFileList(@RequestBody ProjectIdAndUserIdForm projectIdAndUserIdForm) {
        return projectFileService.getFileList(projectIdAndUserIdForm);
    }

    @Operation(summary = "管理员修改文件权限")
    @PostMapping(value = "/modifyFilePermission", consumes={ "application/json"}, produces={ "application/json"})
    public ResponseVO<Boolean> modifyFilePermission(@RequestBody ModifyFilePermissionVO modifyFilePermissionVO) {
        return projectFileService.modifyFilePermission(modifyFilePermissionVO);
    }


}
