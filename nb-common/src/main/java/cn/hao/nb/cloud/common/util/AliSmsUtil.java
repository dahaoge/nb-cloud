package cn.hao.nb.cloud.common.util;

import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Qd;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.penum.ESmsCheckCodeChannel;
import cn.hao.nb.cloud.common.penum.ESmsType;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @Auther: hao
 * @Date: 2019-12-13 21:48
 * @Description:
 */
@Slf4j
@Configuration
public class AliSmsUtil {

    @Autowired
    RedisUtil redisUtil;

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    @Value("${nb.ali.sms.accessKey:demo}")
    private String accessKeyId;

    @Value("${nb.ali.sms.accessSecret:demo}")
    private String accessKeySecret;

    @Value("${nb.ali.sms.signName:demo}")
    private String signName;


    /**
     * 短信验证码获取控制key
     */
    String REDIS_SMS_SEND_LIMIT = "_REDIS_SMS_LIMIT_";

    /**
     * 短信验证码发送控制过期时间
     */
    int REDIS_SMS_SEND_LIMIT_EXPIRE_TIME = 60;

    /**
     * 短信验证码有效时间
     */
    int REDIS_SMS_CHECKCODE_EXPIRE_TIME = 300;

    /**
     * 验证码头
     */
    String REDIS_SMS_CHECKCODE = "_R_SMS_CODE_";

    @Data
    public static class BuildingReportModelParams {
        String brokerName;
        String communityName;
    }

    /**
     * 发送语音消息
     *
     * @param phone       电话
     * @param modelId     语音模板id(需要在阿里云申请)
     * @param showPhone   显示的电话号码(需要在阿里云注册)
     * @param outId       关联id(本地业务逻辑id)
     * @param modelParams 语音模板参数
     * @return
     */
    public Boolean phoneCallMsg(String phone, String modelId, String showPhone, String outId, Object modelParams) {
        log.info("发送语音通知====>phone:{}--modelId:{}--showPhone:{}--outId:{}--modelParams:{}", phone, modelId, showPhone, outId, modelParams);
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dyvmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SingleCallByTts");
        request.putQueryParameter("CalledShowNumber", showPhone);
        request.putQueryParameter("CalledNumber", phone);
        request.putQueryParameter("TtsCode", modelId);
        request.putQueryParameter("TtsParam", JSON.toJSONString(modelParams));
        request.putQueryParameter("OutId", outId);

        try {
            CommonResponse response = client.getCommonResponse(request);
            log.info("语音通知发送结果:{}", response.getData());
        } catch (ServerException e) {
            log.error("发送语音通知报错:{}", e);
        } catch (ClientException e) {
            log.error("发送语音通知报错:{}", e);
        }
        return true;
    }

    /**
     * 获取6位随机验证码
     *
     * @return
     */
    public static String getValidationCode() {
        return RandomUtil.getRandomSaltL(6);
    }

    public Boolean sendSms(String phoneNum, ESmsType templateType, Map jsonTemplateParams, String outId) {

        CheckUtil.isPhone(phoneNum, true);
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            log.error("aly sms api ClientException {}  {}", e.getErrCode(), e.getMessage());
            throw NBException.create(EErrorCode.apiErr, "短信发送接口调用失败");
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phoneNum);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateType.getModelCode());
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(JSON.toJSONString(jsonTemplateParams));

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId(outId);

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            log.error("aly sms api ClientException {}  {}", e.getErrCode(), e.getMessage());
            throw NBException.create(EErrorCode.apiErr, "短信发送接口调用失败");
        }

        if (!"OK".equals(sendSmsResponse.getCode())) {
            log.error("sendSms faild {}", JSON.toJSONString(sendSmsResponse));
            if ("isv.BUSINESS_LIMIT_CONTROL".equals(sendSmsResponse.getCode()))
                throw NBException.create(EErrorCode.apiErr, "触发短信发送限制");
            throw NBException.create(EErrorCode.apiErr, sendSmsResponse.getMessage());
        }

        return "OK".equals(sendSmsResponse.getCode());
    }

    /**
     * 验证验证码
     *
     * @param phone
     * @param code
     * @return
     */
    public boolean checkSms(String phone, String code) {
        CheckUtil.isPhone(phone, true);
        // TODO 验证码后门关闭
        if ("10086".equals(code))
            return Boolean.TRUE;
        if (CheckUtil.objIsEmpty(phone, code)) {
            throw NBException.create(EErrorCode.missingArg);
        }

        if (!redisUtil.hasKey(this.REDIS_SMS_CHECKCODE + phone)) {
            return Boolean.FALSE;
        } else if (!code.equals((String) redisUtil.get(this.REDIS_SMS_CHECKCODE + phone))) {
            return Boolean.FALSE;
        }

        redisUtil.del(this.REDIS_SMS_CHECKCODE, phone);

        return Boolean.TRUE;
    }

    public Boolean sendCheckCode(String phone, ESmsCheckCodeChannel checkCodeChannel) {
        CheckUtil.isPhone(phone, true);

        if (redisUtil.hasKey(this.REDIS_SMS_SEND_LIMIT + checkCodeChannel.getValue() + phone)) {
            throw NBException.create(EErrorCode.apiRstError, "验证码每" + this.REDIS_SMS_SEND_LIMIT_EXPIRE_TIME + "秒可获取一次");
        }

        String code = this.createCode(phone);

        if (this.sendSms(phone, ESmsType.phoneCheck, Qd.create().add("code", code), phone)) {
            redisUtil.set(this.REDIS_SMS_SEND_LIMIT + checkCodeChannel.getValue() + phone, phone, this.REDIS_SMS_SEND_LIMIT_EXPIRE_TIME);
            return true;
        } else {
            return false;
        }
    }

    public Boolean sendLoginCheckCode(String phone) {
        return sendCheckCode(phone, ESmsCheckCodeChannel.login);
    }

    public Boolean sendRegisteCheckCode(String phone) {
        return sendCheckCode(phone, ESmsCheckCodeChannel.registe);
    }

    /**
     * 获取验证码
     *
     * @param phone
     * @return
     */
    public String createCode(String phone) {

        CheckUtil.isPhone(phone, true);

        if (CheckUtil.objIsEmpty(phone)) {
            throw NBException.create(EErrorCode.missingArg);
        }

        String code = null;

        if (redisUtil.hasKey(this.REDIS_SMS_CHECKCODE + phone)) {
            code = (String) redisUtil.get(this.REDIS_SMS_CHECKCODE + phone);
            redisUtil.expire(this.REDIS_SMS_CHECKCODE + phone, this.REDIS_SMS_CHECKCODE_EXPIRE_TIME);
        } else {
            code = AliSmsUtil.getValidationCode();
            redisUtil.set(this.REDIS_SMS_CHECKCODE + phone, code, this.REDIS_SMS_CHECKCODE_EXPIRE_TIME);
        }
        return code;
    }

}