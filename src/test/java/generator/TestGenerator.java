package generator;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ShellRunner;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class TestGenerator {
    
    /**
     * 参考org.mybatis.generator.api.ShellRunner
     * 
     * @throws IOException
     * @throws XMLParserException
     * @throws InvalidConfigurationException
     * @throws SQLException
     * @throws InterruptedException
     */
    @Test
    public void generate() {
        List<String> warnings = new ArrayList<String>();
        
        try {
            boolean overwrite = true;
            File configurationFile = new File("src/main/resources/generatorConfig.xml");
            
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configurationFile);
            DefaultShellCallback shellCallback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
            myBatisGenerator.generate(null);
        } catch (XMLParserException e) {
            writeLine(getString("Progress.3"));
            writeLine();
            for (String error : e.getErrors()) {
                writeLine(error);
            }
            
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (InvalidConfigurationException e) {
            writeLine(getString("Progress.16"));
            for (String error : e.getErrors()) {
                writeLine(error);
            }
            return;
        } catch (InterruptedException e) {
        }
        
        for (String warning : warnings) {
            writeLine(warning);
        }
        
        if (warnings.size() == 0) {
            writeLine(getString("Progress.4"));
        } else {
            writeLine();
            writeLine(getString("Progress.5"));
        }
    }
    
    /**
     * 打印信息
     * 
     * @param message
     */
    private void writeLine(String message) {
        System.out.println(message);
    }
    
    /**
     * 打印空行
     */
    private static void writeLine() {
        System.out.println();
    }
    
    @Test
    public void generate2() {
        String[] args = new String[3];
        args[0] = "-configfile";
        args[1] = "src/generatorConfig.xml";
        args[2] = "-overwrite";
        ShellRunner.main(args);
    }
}
