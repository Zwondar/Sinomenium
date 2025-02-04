package person.sinomenium;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@SpringBootTest

class SinomeniumApplicationTests {

    @Test
    void contextLoads() {

    }
    @Test
    void time() {
        long epochMilli = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println(epochMilli);
    }
//1728550232379
    //     1728115204000
@Test
public void bloomfilter(){
    BloomFilter<CharSequence> bloomFilter = BloomFilter
            .create(Funnels.stringFunnel(Charset.forName("utf-8")), 100, 0.001);
    for (int i = 0; i < 100; i++) {
        bloomFilter.put(""+i);
    }
    System.out.println("数据加入完毕");
    for (int i = 0; i < 200; i++) {
        if(bloomFilter.mightContain(""+i)){
            System.out.println(i+"存在");
        }
    }
}
}
