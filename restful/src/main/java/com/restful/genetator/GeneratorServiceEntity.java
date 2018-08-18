package com.restful.genetator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * <p>
 * mybatis-plus
 * 代码生成器
 * </p>
 *
 * @author  *
 *   ┏ ┓   ┏ ┓
 *  ┏┛ ┻━━━┛ ┻┓
 *  ┃         ┃
 *  ┃    ━    ┃
 *  ┃  ┳┛  ┗┳ ┃
 *  ┃         ┃
 *  ┃    ┻    ┃
 *  ┃         ┃
 *  ┗━━┓    ┏━┛
 *     ┃    ┃神兽保佑
 *     ┃    ┃代码无BUG！
 *     ┃    ┗━━━━━━━┓
 *     ┃            ┣┓
 *     ┃            ┏┛
 *     ┗┓┓┏━━━━━━┳┓┏┛
 *      ┃┫┫      ┃┫┫
 *      ┗┻┛      ┗┻┛
 * @since 2018-08-12
 */
public class GeneratorServiceEntity {
    public static void main(String[] args) {
        com.baomidou.mybatisplus.generator.config.GlobalConfig config = new com.baomidou.mybatisplus.generator.config.GlobalConfig();
        String dbUrl = "jdbc:mysql://localhost:3306/auth2?useSSL=false";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("1234")
                .setDriverName("com.mysql.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(false)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel);
        config.setActiveRecord(false)
                .setEnableCache(false)
                .setAuthor(" * \n" +
                        " *   ┏ ┓   ┏ ┓\n" +
                        " *  ┏┛ ┻━━━┛ ┻┓\n" +
                        " *  ┃         ┃\n" +
                        " *  ┃    ━    ┃\n" +
                        " *  ┃  ┳┛  ┗┳ ┃\n" +
                        " *  ┃         ┃\n" +
                        " *  ┃    ┻    ┃\n" +
                        " *  ┃         ┃\n" +
                        " *  ┗━━┓    ┏━┛\n" +
                        " *     ┃    ┃神兽保佑\n" +
                        " *     ┃    ┃代码无BUG！\n" +
                        " *     ┃    ┗━━━━━━━┓\n" +
                        " *     ┃            ┣┓\n" +
                        " *     ┃            ┏┛\n" +
                        " *     ┗┓┓┏━━━━━━┳┓┏┛\n" +
                        " *      ┃┫┫      ┃┫┫\n" +
                        " *      ┗┻┛      ┗┻┛")
                // 这里就直接输出到项目里面，不用再复制进来
//                .setOutputDir("restful\\src\\main\\java")
                .setOutputDir("/Users/baily/data/generator")
                .setFileOverride(true)
                .setServiceName("%sService");
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent("com.restful")
                                .setController("controller")
                                .setEntity("entity")
                ).execute();
    }
}
