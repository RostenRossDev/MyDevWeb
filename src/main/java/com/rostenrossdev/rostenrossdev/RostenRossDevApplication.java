package com.rostenrossdev.rostenrossdev;

import com.rostenrossdev.rostenrossdev.models.service.IUploadFileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RostenRossDevApplication implements CommandLineRunner {
    @Autowired
    IUploadFileService uploadService;

	public static void main(String[] args) {
		SpringApplication.run(RostenRossDevApplication.class, args);
	}

     @Override
    public void run(String... args) throws Exception {
        uploadService.deleteAll();
        uploadService.init();

    }
}
