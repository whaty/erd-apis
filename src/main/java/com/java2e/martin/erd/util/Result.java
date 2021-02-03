package com.java2e.martin.erd.util;

import com.java2e.martin.erd.Constant;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T>
 * @author 狮少
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private T data;

    public Result() {
        super();
    }

    public Result(T body) {
        super();
        this.status = Constant.SUCCESS;
        this.data = body;
    }

    public Result success(T body) {
        this.status = Constant.SUCCESS;
        this.data = body;
        return this;
    }


    public Result error(T body) {
        this.status = Constant.FAIL;
        this.data = body;
        return this;
    }

}
