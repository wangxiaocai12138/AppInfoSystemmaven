package cn.appsys.dao.devuser;

import cn.appsys.pojo.DevUser;
import org.apache.ibatis.annotations.Param;

public interface DevUserMapper {
    /**
     * 开发者登录
     * @param devCode
     * @param devPassword
     * @return
     */
    DevUser getLoginUser(@Param("devCode") String devCode,
                         @Param("devPassword") String devPassword);
}
