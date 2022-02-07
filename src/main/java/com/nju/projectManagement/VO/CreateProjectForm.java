package com.nju.projectManagement.VO;

import com.nju.projectManagement.DO.ProjectDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Toby Fu
 * @date 2022/2/6
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectForm {
    private Integer userId;
    private String name;
    private String description;

    public boolean valid(){
        return name!=null&&name.length()>0&&userId>0;
    }

    public ProjectDO toProject(){
        ProjectDO ans=new ProjectDO();
        ans.setName(name);
        ans.setDescription(description);
        return ans;
    }
}
