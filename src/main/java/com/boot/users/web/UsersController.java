package com.boot.users.web;

import com.boot.users.entity.Users;
import com.boot.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping({ "/users" })
public class UsersController {

	@Autowired
	private UsersRepository usersRepository;

	@RequestMapping({ "/" })
	public String index() {
		return "redirect:list";
	}

	@RequestMapping({ "/list" })
	public String list(Model model) {
		List users = this.usersRepository.findAll();
		model.addAttribute("users", users);
		return "users/list";
	}

	@RequestMapping({ "/toAdd" })
	public String toAdd() {
		return "users/usersAdd";
	}

	@RequestMapping({ "/add" })
	public String add(Users users) {
		this.usersRepository.save(users);
		return "redirect:list";
	}

	@RequestMapping({ "/toEdit" })
	public String toEdit(Model model, Long id) {
		Users users = this.usersRepository.findById(id.longValue());
		model.addAttribute("users", users);
		return "users/usersEdit";
	}

	@RequestMapping({ "/edit" })
	public String edit(Users users) {
		this.usersRepository.save(users);
		return "redirect:list";
	}

	@RequestMapping({ "/delete" })
	public String delete(Long id) {
		this.usersRepository.deleteById(id);
		return "redirect:list";
	}
}