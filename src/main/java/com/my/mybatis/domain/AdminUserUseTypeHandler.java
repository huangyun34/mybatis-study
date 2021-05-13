package com.my.mybatis.domain;

import com.my.mybatis.typehandler.bo.AESObject;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminUserUseTypeHandler {

    private static final long serialVersionUID = 1L;

    protected Long autoPk;
    /**
     * 业务ID
     */
    protected Long id;
    /**
     * 创建时间（默认）
     */
    protected LocalDateTime createdAt;
    /**
     * 更新时间（默认）
     */
    protected LocalDateTime updatedAt;
    /**
     * 逻辑删除的标记位（0：否，1:是）
     */
    protected Integer isDelete;

    private String createdBy;

    private String updatedBy;

    /**
     * 姓名
     */
    private String nickname;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号
     */
    private AESObject mobile;
    /**
     * 用户状态
     */
    private Integer state;
    /**
     * 最近登陆时间
     */
    private LocalDateTime lastLoginTime;

    private Integer roleType;

    /**
     * 扩展字段
     */
    private String ext;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 第三方id
     */
    private Long thirdAccountId;
}
