package com.test.samba.bean;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.test.samba.util.S3IO;;

@ManagedBean(name = "servidor", eager = true)
@ApplicationScoped
public class ServidorBean {

	public ServidorBean() {
		S3IO configuracao = new S3IO(new File("/home/ubuntu/config.properties"));
	}

	@PostConstruct
	private void inicializar() {
		System.out.println("Servidor inicializado");

	}
}
