import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author: zhangliangx
 * @Date: 2020/9/1 10:39
 * @Description:
 */


@Slf4j
public class WxCardFixTest {

  @Test
  public void test(){
    BufferedInputStream  bis = null;
    try {
      FileInputStream fi = new FileInputStream("C:\\Users\\Administrator\\Desktop\\msg_weixin_app.json");
      bis = new BufferedInputStream(fi);
      byte[] cbuf = new byte[1024];
      int len;

      while((len = bis.read(cbuf)) != -1){
        System.out.print(new String(cbuf, StandardCharsets.UTF_8));
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (Objects.nonNull(bis)){
          bis.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Test
  public void test0() throws IOException {
    String jsonFilePath ="C:\\Users\\Administrator\\Desktop\\msg_weixin_app.json";
    File file = new File(jsonFilePath );
    String input = FileUtils.readFileToString(file,"UTF-8");
    System.out.println(input);

    MsgWxDto msgWxDto = JSONObject.parseObject(input, MsgWxDto.class);
    System.out.println();

    List<Msg> records = msgWxDto.getRECORDS();
    String fpqqlshList = records.stream().map(Msg::getFpqqlsh).map(i -> {
      StringBuilder sb = new StringBuilder("'");
      sb.append(i).append("'");
      return sb.toString();
    }).collect(Collectors.joining(","));
    System.out.println(fpqqlshList);
  }
  @Test
  public void test2() throws IOException {
    String jsonFilePath ="C:\\Users\\Administrator\\Desktop\\msg_weixin_app.json";
    File file = new File(jsonFilePath );
    String input = FileUtils.readFileToString(file,"UTF-8");
    System.out.println(input);

    MsgWxDto msgWxDto = JSONObject.parseObject(input, MsgWxDto.class);
    System.out.println();

    List<Msg> records = msgWxDto.getRECORDS();
    String fpqqlshList = records.stream().map(Msg::getFpqqlsh).map(i -> {
      StringBuilder sb = new StringBuilder("'");
      sb.append(i).append("'");
      return sb.toString();
    }).collect(Collectors.joining(","));
    System.out.println(fpqqlshList);

    String einvoicePath = "C:\\Users\\Administrator\\Desktop\\einvoice_his.json";
    File file1 = new File(einvoicePath);
    String einvoiceString = FileUtils.readFileToString(file1, "UTF-8");

    Record record = JSONObject.parseObject(einvoiceString, Record.class);
    List<Einvoice> einvoiceList = record.getRECORDS();

    Map<String, Einvoice> fpqqlsh2Einvoice = einvoiceList.stream()
        .collect(Collectors.toMap(Einvoice::getFpqqlsh, einvoice -> einvoice));

    //https://me.yonyoucloud.com/wxservice/yon-weixin/card/insertCard?fpqqlsh=6973014670772347161&orderId=6973014670772347161&appId=wx7236f713dae31126
    String URL_FIX = "https://me.yonyoucloud.com/wxservice/yon-weixin/card/insertCard?";
    for (Msg msg : records) {
      StringBuilder sb =new StringBuilder(URL_FIX);
      sb.append("fpqqlsh=").append(msg.getFpqqlsh()).append("&");
      sb.append("orderId=").append(msg.getWxorderid()).append("&");
      sb.append("appId=").append(msg.getWxappid());
      Einvoice einvoice = fpqqlsh2Einvoice.get(msg.getFpqqlsh());
      // header属性
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);

      HttpEntity<Einvoice> formEntity = new HttpEntity<>(einvoice, headers);

      RestTemplate restTemplate = new RestTemplate();
      restTemplate.postForEntity(sb.toString(),formEntity,String.class);
      try {
        ResponseEntity<String> stringResponseEntity = restTemplate
            .postForEntity(sb.toString(), formEntity, String.class);
        log.info("发票申请为{},插卡结果{}",JSON.toJSONString(einvoice),stringResponseEntity.getBody());
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }catch (Exception e){
        e.printStackTrace();
      }

    }


  }




}

@Data
class MsgWxDto{
  private List<Msg> RECORDS;
}

@Data
class Msg{
  private String fpqqlsh;
  private String wxappid;
  private String wxorderid;
}

@Data
class Record{
  private List<Einvoice> RECORDS;
}
@Data
class Einvoice {

  @JSONField(name = "FP_DM")
  private String fpDM;
  @JSONField(name = "FP_HM")
  private String fPHM;
  @JSONField(name = "FPQQLSH")
  private String fpqqlsh;
  @JSONField(name = "GMF_MC")
  private String gMF_MC;
  @JSONField(name = "HJJE")
  private BigDecimal hJJE;
  @JSONField(name = "HJSE")
  private BigDecimal hJSE;
  @JSONField(name = "JSHJ")
  private BigDecimal jSHJ;
  @JSONField(name = "JYM")
  private String jYM;
  @JSONField(name = "KPRQ")
  private String kPRQ;
  @JSONField(name = "XSF_DZDH")
  private String xSF_DZDH;
  @JSONField(name = "XSF_MC")
  private String xSF_MC;
  @JSONField(name = "XSF_NSRSBH")
  private String xSF_NSRSBH;
}
