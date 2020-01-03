package com.boot.adolesce;

import com.boot.adolesce.framework.mail.MailSendService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {

	@Autowired
	private MailSendService mailSendService;

	@Test
	public void sendSimpleMail() {
		this.mailSendService.sendSimpleMail("liuweidong0008@163.com", "test simple mail", "内容~~");
	}

	@Test
	public void sendHtmlMail() {
		String content = "<html>\n<body>\n    <h3>hello world ! 这是一封Html邮件!</h3>\n</body>\n</html>";

		this.mailSendService.sendMail("liuweidong0008@163.com", "test html mail", content, null, null);
	}

	@Test
	public void sendAttachmentsMail() {
		List<String> attachmentPaths = new ArrayList<String>();
		attachmentPaths.add("D://1.zip");
		attachmentPaths.add("D://邮件签名_刘威东.png");
		this.mailSendService.sendMail("liuweidong0008@163.com", "test attachment mail", "有附件，请查收！", attachmentPaths,
				null);
	}

	@Test
	public void sendInlineResourceMail() {
		String rscId1 = "001";
		String rscId2 = "002";

		StringBuffer content = new StringBuffer();
		content.append("<html><body>这是有图片的邮件：").append("<img src='cid:").append(rscId1).append("' >")
				.append("<img src='cid:").append(rscId2).append("' >").append("</body></html>");

		Map<String, String> inLineMap = new HashMap<String, String>();
		inLineMap.put(rscId1, "D://中国政区2500.jpg");
		inLineMap.put(rscId2, "D://邮件签名_刘威东.png");
		this.mailSendService.sendMail("liuweidong0008@163.com", "test inline mail", content.toString(), null,
				inLineMap);
	}
	
}