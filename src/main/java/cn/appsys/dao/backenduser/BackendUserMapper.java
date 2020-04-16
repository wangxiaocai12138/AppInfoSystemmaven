package cn.appsys.dao.backenduser;

import cn.appsys.pojo.BackendUser;
import org.apache.ibatis.annotations.Param;

public interface BackendUserMapper {
    /**
     * 登录
     * @param userCode
     * @param userPassword
     * @return
     */
    BackendUser getUser(@Param("userCode") String userCode,
                        @Param("userPassword") String userPassword);
}
