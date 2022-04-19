package com.nju.projectManagement.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class UserWorkProgessVo {

    private UserIdAndNameVO user;

    private List<TaskVO> completed;

    private List<TaskVO> inProgress;

    private List<TaskVO> postponed;

    public UserWorkProgessVo(){
        this.completed=new ArrayList<>();
        this.inProgress=new ArrayList<>();
        this.postponed=new ArrayList<>();
    }

}
