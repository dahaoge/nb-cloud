import cn.hao.nb.cloud.auth.AuthApplication;
import cn.hao.nb.cloud.auth.service.IUUserInfoService;
import cn.hao.nb.cloud.common.util.AliSmsUtil;
import cn.hao.nb.cloud.common.util.IDUtil;
import com.google.common.collect.Lists;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Auther: hao
 * @Date: 2019-12-08 15:14
 * @Description:
 */
@SpringBootTest(classes = AuthApplication.class)
@RunWith(SpringRunner.class)
public class Test {

    @Autowired
    IUUserInfoService userInfoService;
    @Autowired
    AliSmsUtil smsUtil;
    @Autowired
    IDUtil idUtil;

    public static void main(String[] args) {
        List list = Lists.newArrayList("a", "b");
        System.out.println(list.contains("a"));
        System.out.println("17686688903".substring(3));
        System.out.println("aaa$$bbb".indexOf("$$"));
    }

    @org.junit.Test
    public void test() {
//        smsUtil.sendLoginCheckCode("17686688903");
        System.out.println(idUtil.nextId());
    }
}
