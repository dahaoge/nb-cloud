import cn.hao.nb.cloud.common.penum.ESourceClient;
import cn.hao.nb.cloud.common.util.AesUtil;
import com.google.common.collect.Lists;

import java.util.Calendar;
import java.util.List;

/**
 * @Auther: hao
 * @Date: 2019-12-06 22:10
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        List list = Lists.newArrayList(ESourceClient.webManageClient, ESourceClient.clientApp);
        System.out.println(list.contains(ESourceClient.webManageClient));

    }

    public static void aes() {
        String ak = "CHPGTP6OQ6AYVWMV";
        String sk = "AI7KMUMW8CJ3PEPT";
        long timeSalt = Calendar.getInstance().getTimeInMillis();
        String sign = AesUtil.encrypt(sk + "$$" + timeSalt, sk);
        String decodeSign = AesUtil.decrypt(sign, sk);
        System.out.println(ak);
        System.out.println(sk);
        System.out.println(timeSalt);
        System.out.println(sign);
        System.out.println(decodeSign);
        System.out.println(ESourceClient.valueOf("enterpriseApp"));
    }
}
