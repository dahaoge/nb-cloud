package cn.hao.nb.cloud.common.penum;

import cn.hao.nb.cloud.common.entity.PEnum;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Auther: hao
 * @Date: 2019-12-08 15:20
 * @Description:
 */
public enum ELoginChannelScop implements PEnum<String> {
    CClient(Lists.newArrayList(ESourceClient.app2c, ESourceClient.web2c), "C端"),
    BClient(Lists.newArrayList(ESourceClient.app2b, ESourceClient.web2b), "B端"),
    manageClient(Lists.newArrayList(ESourceClient.app2Manager, ESourceClient.web2Manager), "管理端");

    private String desc;
    private List<ESourceClient> clients;

    ELoginChannelScop(List<ESourceClient> clients, String desc) {
        this.clients = clients;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return name();
    }

    @Override
    public String toString() {
        return name();
    }

    @Override
    public String toChString() {
        return desc;
    }

    public List<ESourceClient> getClients() {
        return clients;
    }
}