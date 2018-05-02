import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 28029 on 2018/4/23.
 */
public class test {
    private Logger logger = LoggerFactory.getLogger("a");
    public void test(){
        logger.info("hello test");
    }
    public static void main(String[] args) {
         test test = new test();
         test.test();
         ThreadLocal<String> str;

    }
}
