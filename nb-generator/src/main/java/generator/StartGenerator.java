package generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 生成启动类
 */
public class StartGenerator {

    /**
     * 数据库url
     */
    public static String url = "jdbc:mysql://47.96.174.93:13306/eee?serverTimezone=GMT%2B8&characterEncoding=utf8&zeroDateTimeBehavior" +
            "=convertToNull";
    /**
     * 用户名
     */
    public static String userName = "root";
    /**
     * 密码
     */
    public static String userPwd = "WXfengguang88";

    /**
     * 数据库url
     */
    public static String url1 = "jdbc:mysql://222.79.184.36:13306/marketing?serverTimezone=GMT%2B8&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
    /**
     * 用户名
     */
    public static String userName1 = "root";
    /**
     * 密码
     */
    public static String userPwd1 = "root";


    /**
     * 启动函数
     *
     * @param args
     */
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/nb-generator/src/main/java");
        gc.setAuthor("hao@179314039@qq.com");
        gc.setOpen(false);
        gc.setSwagger2(true);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);

        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(StartGenerator.url);
        dsc.setUsername(StartGenerator.userName);
        dsc.setPassword(StartGenerator.userPwd);

        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
//        pc.setModuleName("core");
        pc.setParent("cn.hao.nb.cloud");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/nb-generator/src/main/resources/mapper/" + pc.getModuleName() + "/" + tableInfo.getEntityName() + "Mapper"
                        + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(null);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setSuperControllerClass(null);
//        strategy.setInclude(scanner("表名"));
        strategy.setInclude(
//                "u_user_info",
//                "u_login_channel",
                "sys_menu",
                "sys_role",
                "sys_permission",
                "sys_role_menu",
                "sys_role_permission",
                "u_user_role"
//                "u_user_dept",
//                "sys_dept"
        );
        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");

        strategy.setVersionFieldName("version");
        strategy.setLogicDeleteFieldName("deleted");
        strategy.setEntityColumnConstant(true);

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
}
