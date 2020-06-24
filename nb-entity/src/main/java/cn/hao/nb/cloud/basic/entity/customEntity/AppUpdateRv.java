package cn.hao.nb.cloud.basic.entity.customEntity;

import cn.hao.nb.cloud.basic.entity.SysAppVersion;
import cn.hao.nb.cloud.common.util.CheckUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hao
 * @Date: 2020/6/14 17:01
 * @Description:
 */
@Data
public class AppUpdateRv implements Serializable {

    private static final long serialVersionUID = 1L;

    Integer Code;
    String Msg;
    Integer UpdateStatus;
    Integer VersionCode;
    String VersionName;
    String ModifyContent;
    String DownloadUrl;
    Long ApkSize;
    String ApkMd5;

    public static AppUpdateRv toAppUpdateRv(SysAppVersion appVersion) {
        if (CheckUtil.objIsEmpty(appVersion))
            return null;
        AppUpdateRv data = new AppUpdateRv();
        data.setUpdateStatus(appVersion.getIsMust() == 1 ? 2 : 1);
        data.setVersionCode(appVersion.getVersionNum());
        data.setVersionName(appVersion.getAppVersionName());
        data.setModifyContent(appVersion.getUpdateDesc());
        data.setDownloadUrl(appVersion.getDownLoadUrl());
        return data;
    }

}
