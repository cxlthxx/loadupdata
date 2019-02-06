package com.example.demo.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dao.Stundent;

public interface StundentService extends JpaRepository<Stundent,Integer>{

}
