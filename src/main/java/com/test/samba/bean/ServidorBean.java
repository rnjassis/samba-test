package com.test.samba.bean;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.test.samba.util.S3IO;
import com.test.samba.util.ZencoderConfig;;

/**
 * Bean that load the configuration (S3 and Zencoder)
 * @author silva
 *
 */
@ManagedBean(name = "servidor", eager = true)
@ApplicationScoped
public class ServidorBean {

	public ServidorBean() {
		File propertiesFile = new File("/home/ubuntu/config.properties");
		
		S3IO s3io = new S3IO(propertiesFile);
		ZencoderConfig zencoderconfig = new ZencoderConfig(propertiesFile);
	}

	@PostConstruct
	private void inicializar() {
		System.out.println("Servidor samba-test inicializado");

	}
}
