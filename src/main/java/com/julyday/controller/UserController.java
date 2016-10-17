package com.julyday.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.julyday.entity.User;
import com.julyday.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	private static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	// 本方法将处理/user/hello
	@RequestMapping(value = "hello")
	public String hello() {
		return "hello";
	}

	// 本方法将处理 /user/view?userId=1 形式的URL,/user/view?userId=1&format=json,返回json
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String view(
			@RequestParam(value = "userId", required = true) Integer id,
			Model model) {
		log.debug("UserController view {}", id);
		User user = userService.findByid(id);
		model.addAttribute(user);
		return "user/view";
	}

	// /user/restful/123
	@RequestMapping("/restful/{userId}")
	public String restful(@PathVariable("userId") Integer id,
			Map<String, Object> model) {
		log.debug("UserController restful {}", id);
		User user = userService.findByid(id);
		model.put("user", user);
		return "user/view";
	}

	// /user/restful2/123/hello
	@RequestMapping("/restful2/{userId:[0-9]+}/{param}")
	public String restful2(@PathVariable("userId") Integer id,
			@PathVariable("param") String param, Map<String, Object> model) {
		log.debug("UserController restful id: {} and param: {}", id, param);
		User user = userService.findByid(id);
		model.put("user", user);
		return "user/view";
	}

	// /user/request?userId=123
	@RequestMapping("request")
	public String request(HttpServletRequest request) {
		Integer id = Integer.valueOf(request.getParameter("userId"));
		log.debug("UserController request {}", id);
		User user = userService.findByid(id);
		request.setAttribute("user", user);
		return "user/view";
	}

	// /user/all
	@RequestMapping("all")
	public @ResponseBody String findAll() {
		List<User> list = userService.findAll();
		return JSON.toJSONString(list);
	}

	// /user/all2
	@RequestMapping("all2")
	public ResponseEntity<List<User>> findAll2() {
		List<User> list = userService.findAll();
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}

	// /user/params?add=123
	@RequestMapping(value = "params", params = "add")
	public ModelAndView params(HttpServletRequest request, ModelAndView model) {
		System.out.println("params:" + request.getParameter("add"));
		model.setViewName("user/add");
		return model;
	}

	@RequestMapping(value = "regist", method = RequestMethod.POST)
	public ModelAndView regist(User user, ModelAndView model) {
		log.debug("UserController regist {}", user);
		model.setViewName("user/view");
		return model;
	}

	// /user/upload
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView showUploadPage(ModelAndView model) {
		model.setViewName("upload");
		return model;
	}

	// /user/upload2
	@RequestMapping(value = "/upload2", method = RequestMethod.GET)
	public ModelAndView showUploadPage2(ModelAndView model) {
		model.setViewName("upload");
		return model;
	}

	@RequestMapping(value = "/doUpload", method = RequestMethod.POST)
	public String doUploadFile(@RequestParam("file") MultipartFile file)
			throws IOException {
		if (!file.isEmpty()) {
			log.debug("doUpload file: {}", file.getOriginalFilename());
			FileUtils.copyInputStreamToFile(
					file.getInputStream(),
					new File("c:\\temp\\", System.currentTimeMillis()
							+ file.getOriginalFilename()));
		}
		return "success";
	}
}
