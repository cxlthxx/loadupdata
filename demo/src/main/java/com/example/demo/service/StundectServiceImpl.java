package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.Stundent;


@Service
public class StundectServiceImpl {
	
	@Autowired
	private StundentService stundentService;
	
	
	public List<Stundent> findAll(List<Stundent> list) {
		
		return stundentService.saveAll(list);
	}
	
	public List<Stundent> getStundent() {
		return stundentService.findAll();
	}

}
