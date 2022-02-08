package com.nju.projectManagement.VO;

import com.nju.projectManagement.DO.UserDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Toby Fu
 * @date 2022/2/6
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIdAndNameVO {
    String name;
    Integer id;

    public UserIdAndNameVO(UserDO userDO){
        this.name = userDO.getName();
        this.id = userDO.getId();
    }
}
