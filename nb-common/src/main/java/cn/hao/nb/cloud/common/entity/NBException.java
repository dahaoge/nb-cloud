package cn.hao.nb.cloud.common.entity;


import cn.hao.nb.cloud.common.penum.EErrorCode;
import lombok.Data;


/**
 * panggj 权限异常
 */
@Data
public class NBException extends RuntimeException {

    // 定义枚举
    private EErrorCode errorCode;
    // 定义描述
    private String meMessage;

    /**
     * 构造函数
     *
     * @param errorCode
     */
    public NBException(EErrorCode errorCode) {
        super(errorCode.toChString());

        this.errorCode = errorCode;
        this.meMessage = errorCode.toChString();
    }

    /**
     * 构造函数
     *
     * @param errorCode
     * @param meMessage
     */
    public NBException(EErrorCode errorCode, String meMessage) {
        super(meMessage);

        this.errorCode = errorCode;
        this.meMessage = meMessage;
    }

    public NBException(Exception e) {
        super(e);

        this.errorCode = EErrorCode.unkown;
        this.meMessage = "";
    }

    public NBException(String meMessage) {
        super(meMessage);

        this.errorCode = EErrorCode.unkown;
        this.meMessage = "";
    }

    /**
     * 构造函数
     *
     * @param errorCode
     * @param meMessage
     */
    public NBException(EErrorCode errorCode, String meMessage, Exception e) {
        super(meMessage, e);

        this.errorCode = errorCode;
        this.meMessage = meMessage;
    }

    /**
     * 构造函数
     *
     * @param meMessage
     */
    public NBException(String meMessage, Exception e) {
        super(meMessage, e);

        this.errorCode = EErrorCode.c500;
        this.meMessage = meMessage;
    }

    public static NBException create(EErrorCode errorCode) {
        return new NBException(errorCode);
    }

    public static NBException create(EErrorCode errorCode, String meMessage) {
        return new NBException(errorCode, meMessage);
    }

    public static NBException create(Exception e) {
        return new NBException(e);
    }

    public static NBException create(String meMessage) {
        return new NBException(meMessage);
    }

    /**
     * 是否skip or repet error
     *
     * @return
     */
    public boolean isSkipOrRepet() {
        return errorCode == EErrorCode.skip || errorCode == EErrorCode.repeat || errorCode == EErrorCode.beUsed;
    }

    /**
     * 打印日志
     *
     * @return
     */
    public String printLog() {
        return new StringBuilder().append("PAuth exception, type:").append(this.errorCode.toChString()).append(" message:").append(this.meMessage)
                .toString();
    }
}
