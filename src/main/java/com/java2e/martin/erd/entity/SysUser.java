package com.java2e.martin.erd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author shishao
 * @version 1.0
 * @date 2020-11-10
 * @describtion
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUser implements Serializable {


    @TableId(value = "id", type = IdType.UUID)
    private String  id;

    private String username;

    private String password;


}
