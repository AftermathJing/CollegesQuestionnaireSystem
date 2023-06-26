package com.github.aftermathjing.questionnaire.api.result;

import com.github.aftermathjing.questionnaire.common.enumeration.ServiceResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.NonNull;

@AllArgsConstructor
public class ServiceResult<Data> {

    /**
     * 服务响应码
     */
    @Getter
    ServiceResultCode code;

    /**
     * 服务响应信息
     */
    @Getter
    String message;

    /**
     * 服务响应数据 (可空)
     */
    @Getter
    Data data;

    public static <Data> ServiceResult<Data> of(@NonNull ServiceResultCode code, @NonNull String message) {
        return new ServiceResult<>(code, message, null);
    }

    public static <Data> ServiceResult<Data> of(@NonNull ServiceResultCode code, @NonNull String message,
                                                @NonNull Data data) {
        return new ServiceResult<>(code, message, data);
    }

    public <T> ServiceResult<T> as(@NonNull T obj) {
        return new ServiceResult<>(this.code, this.message, obj);
    }
    public ServiceResult<Data> with(@NonNull String message) {
        return new ServiceResult<Data>(this.code, message, this.data);
    }

    public static <Data> ServiceResult<Data> ofOK(@NonNull String message,
                                                  @NonNull Data data) {
        return new ServiceResult<>(ServiceResultCode.OK, message, data);
    }

    public static <Data> ServiceResult<Data> ofOK(@NonNull String message) {
        return new ServiceResult<>(ServiceResultCode.OK, message, null);
    }

    public static <Data> ServiceResult<Data> ofDeprecated() {
        return new ServiceResult<>(ServiceResultCode.METHOD_DEPRECATED, "请求的方法被弃用", null);
    }


}
