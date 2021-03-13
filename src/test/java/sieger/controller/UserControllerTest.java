package sieger.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserControllerTest{
	
	
    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext ctx;
    
    private UserController userController;
	
	private String token;
	
	private String userId;
	
	@Before
    public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx).build();
        Map<String,Object> m = new HashMap<String,Object>();
   
        m.put("email","1790073685@qq.com");
        m.put("password","password");
        m.put("returnSecureToken",true);
       
        String result =retrieveTokenFromGoogle("https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyPassword?key=AIzaSyDIIa-WlH1k9UMsheSc-kU7BSkpEbMRVXM",
        		new JSONObject(m).toString());
        JSONObject  obj = new JSONObject(result);
        token = obj.get("idToken").toString();
        userId = obj.get("localId").toString();
       
    }
	
	private String retrieveTokenFromGoogle(String url, String data) {
        OutputStreamWriter out = null;
	    BufferedReader in = null;
	    String result = "";
	    try {
	        URL realUrl = new URL(url);
	        HttpURLConnection conn = null;
	        // 打开和URL之间的连接
	        conn = (HttpURLConnection) realUrl.openConnection();
	        // 发送POST请求必须设置如下两行
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.setRequestMethod("POST");    // POST方法

	        // 设置通用的请求属性
	        conn.setRequestProperty("accept", "*/*");
	        conn.setRequestProperty("connection", "Keep-Alive");
	        conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	        conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
	        conn.connect();
	        // 获取URLConnection对象对应的输出流
	        out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
	        // 发送请求参数
	        out.write(data);
	        // flush输出流的缓冲
	        out.flush();
	        // 定义BufferedReader输入流来读取URL的响应
	        in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line;
	        while ((line = in.readLine()) != null) {
	            result += line;
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    //使用finally块来关闭输出流、输入流
	    finally {
	        try {
	            if (out != null) {
	                out.close();
	            }
	            if (in != null) {
	                in.close();
	            }
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }
	 
	    return result;
	}

	@Test
	@Ignore
	@Rollback()
	public void test_createNewUser_success() throws Exception {
        String requestBody = "{\r\n"
        		+ "    \"username\" : \"1013\",\r\n"
        		+ "    \"surname\" : \"102\",\r\n"
        		+ "    \"forename\" : \"1012\",\r\n"
        		+ "    \"userId\" : \"" + userId + "\"\r\n"
        		+ "}";
	
			mockMvc.perform(MockMvcRequestBuilders
			        .post("/users")
			        .contentType(MediaType.APPLICATION_JSON_VALUE)
			        .content(requestBody.getBytes()) //传json参数
			        .header("Authorization","Bearer " + token)
			).andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print())
            .andReturn();
		
         
	}

}
