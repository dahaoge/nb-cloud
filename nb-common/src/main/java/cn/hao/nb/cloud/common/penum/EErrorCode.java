package cn.hao.nb.cloud.common.penum;

import cn.hao.nb.common.entity.PEnum;

/**
 * 错误码枚举
 *
 * @Author: scootXin
 * @Date: 2018/11/29 17:47
 */
public enum EErrorCode implements PEnum<Integer> {
    //成功标志
    success(0, "成功"),
    //参数及数据错误
    missingArg(100, "缺少参数"),
    argCheckErr(101, "验参失败"),
    noData(102, "数据错误"),
    dataParseError(103, "数据转换错误"),
    beUsed(104, "已被使用"),
    skip(105,
            "因某些原因跳过操作"),
    repeat(106, "重复操作"),
    versionError(107, "版本已改变，请重新获取数据"),
    sysError(108, "系统错误"),//权限错误
    authErr(200, "权限错误"),
    missingAuthArgs(201, "请求头缺少权限参数"),
    authDecodeError(202, "权限参数转码错误"),
    noAuth(203, "无授权"),
    authDenied(204, "拒绝访问"),
    authIdentityErr(205, "身份验证失败"),
    gatewayDeny(206, "网关拒绝"),
    tokenErr(207, "token格式错误"),
    tokenExpire(208, "token过期"),//http通用错误
    apiErr(300, "第三方api通用错误"),
    apiArgErr(301, "第三方api参数错误"),
    apiRstError(302, "第三方api返回状态错误"),
    c400(400, "参数错误"),
    c401(401, "权限错误"),
    c403(403,
            "拒绝访问"),
    c404(404, "页面不存在"),
    c500(500, "服务器错误"),
    noHandlerError(501, "无处理器错误"),
    paymentErr(800, "支付错误"),
    paymentStatusErr(801,
            "支付状态错误"),
    userDetailMissingErr(900, "缺失用户明细信息"),
    missWechat(901, "微信数据未绑定"),
    missPhone(902, "电话未绑定"),
    serverUpdating(999, "服务器升级中,有可能会持续很长时间,要不要先坐下来喝杯咖啡..."),
    appVersionTooLower(209, "您的APP版本过低,请前往应用市场下载最新版本的APP"),
    unkown(-1, "未知错误");

    int value;
    String desc;

    EErrorCode(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name();
    }

    @Override
    public String toChString() {
        return desc;
    }
}
