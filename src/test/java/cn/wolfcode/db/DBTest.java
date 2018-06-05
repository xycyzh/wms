package cn.wolfcode.db;

import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.Test;

public class DBTest {
    @Test
    public void testDB() throws Exception {
        String admin = ConfigTools.encrypt("admin");
        System.out.println(admin);
    }

    @Test
    public void test1() {
        String imagePath = "2e9448df-be75-41c5-90ea-cd0ba54ec68e.png";
        String prefix = imagePath.substring(0, imagePath.lastIndexOf("."));
        String suffix = imagePath.substring(imagePath.lastIndexOf("."));
        System.out.println(prefix + "_small" + suffix);
    }

    @Test
    public void test2() {
        String a = new String("e.name");
        String b = new String("e.name");
        System.out.println(a==b);
    }
}
