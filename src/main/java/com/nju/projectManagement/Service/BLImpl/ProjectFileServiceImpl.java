package com.nju.projectManagement.Service.BLImpl;

import com.nju.projectManagement.DO.ProjectFileDO;
import com.nju.projectManagement.DO.ProjectFileDOExample;
import com.nju.projectManagement.DO.ProjectMemberDO;
import com.nju.projectManagement.DO.ProjectMemberDOExample;
import com.nju.projectManagement.Mapper.ProjectFileMapper;
import com.nju.projectManagement.Mapper.ProjectMemberMapper;
import com.nju.projectManagement.Service.BL.ProjectFileService;
import com.nju.projectManagement.VO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Toby Fu
 * @date 2022/4/16
 **/
@Component
public class ProjectFileServiceImpl implements ProjectFileService {

    private static final String PATH = "/root/agile/files/";
//    private static final String PATH = "D:/files/";

    @Autowired
    ProjectFileMapper projectFileMapper;

    @Autowired
    ProjectMemberMapper projectMemberMapper;

    @Override
    public ResponseVO<Boolean> uploadFile(UploadFileForm uploadFileForm) throws IOException {

        Integer userId = uploadFileForm.getUserId();
        Integer projectId = uploadFileForm.getProjectId();
        MultipartFile multipartFile = uploadFileForm.getFile();

        ProjectFileDO projectFileDO = new ProjectFileDO();
        projectFileDO.setProjectId(projectId);
        projectFileDO.setUploadUserId(userId);

        ProjectMemberDOExample projectMemberDOExample = new ProjectMemberDOExample();
        projectMemberDOExample.createCriteria().andUserIdEqualTo(userId).andProjectIdEqualTo(projectId);
        List<ProjectMemberDO> projectMemberDOS = projectMemberMapper.selectByExample(projectMemberDOExample);
        if(projectMemberDOS==null || projectMemberDOS.size() == 0) {
            return ResponseVO.buildFailure("???????????????????????????");
        }

        ProjectMemberDO member = projectMemberDOS.get(0);
        projectFileDO.setPermission(member.getRole()==0?0:1);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateStr = sdf.format(date);
        String fileName = multipartFile.getOriginalFilename() + "-" + dateStr;
        projectFileDO.setFileName(fileName);

        File file = new File(PATH + fileName);
        multipartFile.transferTo(file);
        int id = projectFileMapper.insert(projectFileDO);
        if(id == 0){
            file.delete();
            return ResponseVO.buildFailure("??????????????????");
        }

        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO<Boolean> deleteFile(DeleteFileForm deleteFileForm) {

        Integer projectId = deleteFileForm.getProjectId();
        Integer userId = deleteFileForm.getUserId();
        Integer fileId = deleteFileForm.getFileId();

        ProjectMemberDOExample projectMemberDOExample = new ProjectMemberDOExample();
        projectMemberDOExample.createCriteria().andProjectIdEqualTo(projectId).andUserIdEqualTo(userId);

        List<ProjectMemberDO> projectMemberDOList = projectMemberMapper.selectByExample(projectMemberDOExample);
        if(projectMemberDOList==null || projectMemberDOList.size() == 0) {
            return ResponseVO.buildFailure("???????????????????????????");
        }

        ProjectMemberDO member = projectMemberDOList.get(0);
        if(member.getRole() == 0) {
            return ResponseVO.buildFailure("?????????????????????????????????");
        }

        ProjectFileDOExample projectFileDOExample = new ProjectFileDOExample();
        projectFileDOExample.createCriteria().andIdEqualTo(fileId).andProjectIdEqualTo(projectId);
        List<ProjectFileDO> projectFileDOList = projectFileMapper.selectByExample(projectFileDOExample);
        if(projectFileDOList==null || projectFileDOList.size() == 0) {
            return ResponseVO.buildFailure("???????????????");
        }
        ProjectFileDO projectFileDO = projectFileDOList.get(0);
        String fileName = projectFileDO.getFileName();

        File file = new File(PATH + fileName);


        if(file.exists() && file.isFile()){
            boolean res = file.delete();
            if(!res) {
                return  ResponseVO.buildFailure("??????????????????");
            }
            projectFileMapper.deleteByPrimaryKey(deleteFileForm.getFileId());
        }


        return ResponseVO.buildSuccess(true);
    }

    @Override
    public void downloadFile(Integer fileId, HttpServletResponse resp) throws Exception {
        ProjectFileDO projectFileDO = projectFileMapper.selectByPrimaryKey(fileId);
        if(projectFileDO == null){
           throw new Exception("??????????????????");
        }
        String fileName = projectFileDO.getFileName();
        writeFileToResponse(fileName, resp);
    }

    @Override
    public ResponseVO<List<ProjectFileInfoVO>> getFileList(ProjectIdAndUserIdForm projectIdAndUserIdForm) {
        int userId = projectIdAndUserIdForm.getUserId();
        int projectId = projectIdAndUserIdForm.getProjectId();
        ProjectMemberDOExample projectMemberDOExample = new ProjectMemberDOExample();
        projectMemberDOExample.createCriteria().andUserIdEqualTo(userId).andProjectIdEqualTo(projectId);
        List<ProjectMemberDO> projectMemberDOList = projectMemberMapper.selectByExample(projectMemberDOExample);

        if(projectMemberDOList==null || projectMemberDOList.size() == 0) {
            return ResponseVO.buildFailure("???????????????????????????");
        }

        ProjectMemberDO projectMemberDO = projectMemberDOList.get(0);
        int role = projectMemberDO.getRole();


        ProjectFileDOExample projectFileDOExample = new ProjectFileDOExample();

        if(role == 0){
            projectFileDOExample.createCriteria().andProjectIdEqualTo(projectId).andPermissionEqualTo(0);
        }
        else{
            projectFileDOExample.createCriteria().andProjectIdEqualTo(projectId);
        }
        List<ProjectFileDO> projectFileDOList = projectFileMapper.selectByExample(projectFileDOExample);
        List<ProjectFileInfoVO> res = projectFileDOList.stream().map(ProjectFileInfoVO::new).collect(Collectors.toList());


        return ResponseVO.buildSuccess(res);
    }

    @Override
    public ResponseVO<Boolean> modifyFilePermission(ModifyFilePermissionVO modifyFilePermissionVO) {
        int userId = modifyFilePermissionVO.getUserId();
        int projectId = modifyFilePermissionVO.getProjectId();
        int fileId = modifyFilePermissionVO.getFileId();


        ProjectMemberDOExample projectMemberDOExample = new ProjectMemberDOExample();
        projectMemberDOExample.createCriteria().andUserIdEqualTo(userId).andProjectIdEqualTo(projectId);
        List<ProjectMemberDO> projectMemberDOList = projectMemberMapper.selectByExample(projectMemberDOExample);

        if(projectMemberDOList==null || projectMemberDOList.size() == 0) {
            return ResponseVO.buildFailure("???????????????????????????");
        }

        ProjectMemberDO projectMemberDO = projectMemberDOList.get(0);
        int role = projectMemberDO.getRole();
        if(role == 0){
            return  ResponseVO.buildFailure("??????????????????????????????????????????");
        }

        ProjectFileDOExample projectFileDOExample = new ProjectFileDOExample();
        projectFileDOExample.createCriteria().andIdEqualTo(fileId).andProjectIdEqualTo(projectId);
        List<ProjectFileDO> projectFileDOList = projectFileMapper.selectByExample(projectFileDOExample);
        if(projectFileDOList==null || projectFileDOList.size() == 0) {
            return ResponseVO.buildFailure("???????????????");
        }
        ProjectFileDO projectFileDO = projectFileDOList.get(0);
        projectFileDO.setPermission(modifyFilePermissionVO.getPermission());
        projectFileMapper.updateByPrimaryKey(projectFileDO);

        return ResponseVO.buildSuccess(true);
    }

    private void writeFileToResponse(String fileName, HttpServletResponse resp) throws IOException {
        File file = new File(PATH + fileName);
        resp.setHeader("content-type", "application/octet-stream");
        resp.setContentType("application/octet-stream");

        fileName = fileName.substring(0, fileName.lastIndexOf("-"));

        resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];

        OutputStream os = resp.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        int i = bis.read(buff);
        while (i != -1) {
            os.write(buff, 0, buff.length);
            os.flush();
            i = bis.read(buff);
        }
        bis.close();
    }
}
