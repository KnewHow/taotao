package taotao.com.test;

import org.junit.Test;

public class SplitTest {

    
    @Test
    public void test_1(){
       String ids = "1,2,3,4";
       String[] id = ids.split(",");
       for(String s:id){
           System.out.println(s);
       }
    }
}
