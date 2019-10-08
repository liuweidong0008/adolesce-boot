package com.boot.demo.controller;

import com.alibaba.fastjson.JSON;
import com.boot.common.properties.DemoProperties;
import com.boot.common.properties.MyProperties;
import com.boot.common.properties.PersonProperties;
import com.boot.users.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
//@Controller+@ResponseBody
@RestController
@EnableConfigurationProperties({DemoProperties.class})
public class DemoController {
	@Autowired
	private DemoProperties demoProperties;
	@Autowired
	private PersonProperties personProperties;
	@Autowired
	private MyProperties myProperties;

	/**
	 * 跳转页面
	 *
	 * @param model
	 * @param name 姓名 非必传
	 * @return
	 */
	@RequestMapping({"/hello"})
	public String hello(Model model,
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		Users users = new Users();
		users.setId(1456L);
		users.setUserName("lwd");
		users.setAge(12);
		List<String> books = new ArrayList<String>();
		books.add("book1");
		books.add("book2");
		books.add("book3");
		model.addAttribute("name", name);
		model.addAttribute("user", users);
		model.addAttribute("books", books);
		return "demo/hello";
	}

	/**
	 * demoProperties取值
	 *
	 * @return
	 */
	@RequestMapping("demoProperties")
	public String demoProperties() {
		return this.demoProperties.getOne() + this.demoProperties.getTwo() + this.demoProperties.getThree();
	}

	/**
	 * personProperties取值
	 *
	 * @return
	 */
	@RequestMapping("personProperties")
	public String psersonProperties() {
		return this.personProperties.getName() + this.personProperties.getAge() + this.personProperties.getAddress();
	}

	/**
	 * myProperties取值
	 *
	 * @return
	 */
	@RequestMapping(value = "myProperties")
	public String myProperties() {
		return this.myProperties.getName() + this.myProperties.getAge() + this.myProperties.getAddress();
	}

	/**
	 * 跳转至上传页面
	 *
	 * @return
	 */
	@GetMapping({ "/uploadPage" })
	public String uploadPage() {
		return "demo/upload";
	}

	/**
	 * 开始上传
     *
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/upload")
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:uploadStatus";
		}
		try {
			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			String dir = "D://filetest/springbootupload/";
			if(!new File(dir).exists()){
				new File(dir).mkdirs();
			}
			Path path = Paths.get("D://filetest/springbootupload/" + file.getOriginalFilename());
			Files.write(path, bytes);
			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded '" + file.getOriginalFilename() + "'");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:uploadStatus";
	}

	/**
	 * 跳转上传结果页面
     *
	 * @return
	 */
	@GetMapping({ "uploadStatus" })
	public String uploadStatus() {
		return "demo/uploadStatus";
	}

	/**
	 * 根据id删除
     *
	 * @param id
	 */
	@RequestMapping(value="/delete/{id}")
	public void delete(@PathVariable("id") Long id) {
	}

	/**
	 * 重定向跳转
     *
	 * @param redirectUrl
	 * @return
	 */
	@GetMapping("myredirect")
	public String myredirect(@RequestParam String redirectUrl) {
		log.debug("redirectUrl:{}",redirectUrl);
		return "redirect:"+redirectUrl;
	}

	/**
	 * 测试get方式String传参接参
     *
	 * @param name
	 * @param address
	 * @return
	 * @throws UnsupportedEncodingException
	 *
	 * 调用：http://localhost:8081/spring_boot/testGetStringRequest?name=刘威东
	 */
	//@RequestParam(value = "name", required = false, defaultValue = "World")
	//@RequestParam用于给参数起别名，并且可设置参数是否是必传
	@GetMapping("testGetStringRequest")
	public String testGetStringRequest(@RequestParam String name,
								  @RequestParam(value = "aa",required = false) String address,String aa){
		log.debug("name:{},address:{},aa:{}",name,address,aa);
		//name = new String(name.getBytes("ISO-8859-1"),"utf-8");
		return name;
	}

	/**
	 * 测试Get方式String传参map接参数
     *
	 * @param paramMap
	 * @return
	 *
	 * 调用：http://localhost:8081/spring_boot/testGetMapRequest?userName=lwd&age=12&address=123123
	 */
	@GetMapping("testGetMapRequest")
	public String testGetMapRequest(@RequestParam Map<String,Object> paramMap) throws InvocationTargetException, IllegalAccessException {
		log.debug("paramMap:{}", JSON.toJSON(paramMap));
		Users users = new Users();
		BeanUtils.populate(users,paramMap);
        System.out.println(users);
		return JSON.toJSONString(paramMap);
	}

	/**
	 * 测试Get方式String传参entity接参数
	 *
	 * @param users
	 * @return
	 *
	 * 调用：http://localhost:8081/spring_boot/testGetEntityRequest?userName=lwd&age=12&address=123123
	 */
	@GetMapping("testGetEntityRequest")
	public String testGetEntityRequest(Users users){
		log.debug("users:{}", JSON.toJSON(users));
		return JSON.toJSONString(users);
	}

    /**
     * 测试Post方式String接参
     *
     * @param name
     * @return
	 *
	 * 调用：http://localhost:8081/spring_boot/testPostStringRequest
     */
    @PostMapping("testPostStringRequest")
    public String testPostStringRequest(@RequestParam String name,String address){
        log.debug("name:{},address{}", name,address);
        return name;
    }

	/**
	 * 测试Post方式map接参
	 *
	 * @param paramMap
	 * @return
	 *
	 * 调用：http://localhost:8081/spring_boot/testPostMapRequest
	 */
	@PostMapping("testPostMapRequest")
	public String testPostMapRequest(@RequestParam Map<String,Object> paramMap) throws InvocationTargetException,IllegalAccessException {
		log.debug("paramMap:{}", JSON.toJSON(paramMap));
		Users users = new Users();
		BeanUtils.populate(users,paramMap);
		System.out.println(users);
		return JSON.toJSONString(paramMap);
	}

	/**
	 * 测试Post方式entity接参数
	 *
	 * @param users
	 * @return
	 *
	 * 调用：http://localhost:8081/spring_boot/testPostEntityRequest
	 */
	@PostMapping("testPostEntityRequest")
	public String testPostEntityRequest(Users users){
		log.debug("users:{}", JSON.toJSON(users));
		return JSON.toJSONString(users);
	}


	/**
	 * 测试Post方式json格式数据map接参（Body raw 最右边选JSON）
	 *
	 * @param paramMap
	 * @return
	 *
	 * 调用：http://localhost:8081/spring_boot/testPostMapJsonRequest
	 */
	//@RequestBody用于post请求，不能用于get请求
	//@RequestBody注解可以接收json格式的数据，并将其转换成对应的数据类型。
	@PostMapping("testPostMapJsonRequest")
	public String testPostMapJsonRequest(@RequestBody Map<String,Object> paramMap) throws InvocationTargetException,IllegalAccessException {
		log.debug("paramMap:{}", JSON.toJSON(paramMap));
		Users users = new Users();
		BeanUtils.populate(users,paramMap);
		System.out.println(users);
		return JSON.toJSONString(paramMap);
	}

	/**
	 * 测试Post方式json格式数据entity接参数
	 *
	 * @param users
	 * @return
	 *
	 * 调用：http://localhost:8081/spring_boot/testPostEntityJsonRequest
	 */
	@PostMapping("testPostEntityJsonRequest")
	public String testPostEntityJsonRequest(@RequestBody Users users){
		log.debug("users:{}", JSON.toJSON(users));
		return JSON.toJSONString(users);
	}

}