package com.test.samba.bean;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.test.samba.util.S3IO;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ FacesContext.class })
public class WatchBeanTest {

	@Before
	public void setUp() throws Exception {
		File propertiesFile = new File("/home/ubuntu/config.properties");

		S3IO s3io = new S3IO(propertiesFile);
	}

	@Test
	public void testGetStreamNormal() {
		PowerMockito.mockStatic(FacesContext.class);
		
		Map<String, Object> session = new HashMap<String, Object>();
		session.put("video", "testfile.mp4");

		FacesContext context = FacesContextMocker.mockFacesContext();
		ExternalContext ext = Mockito.mock(ExternalContext.class);
		Mockito.when(ext.getSessionMap()).thenReturn(session);
		Mockito.when(context.getExternalContext()).thenReturn(ext);
		Mockito.when(FacesContext.getCurrentInstance()).thenReturn(context);

		WatchBean watch = new WatchBean();
		try {
			assertNotNull(watch.getStream());
		} catch (IOException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}

	}

	@Test(expected = IOException.class)
	public void testGetStreamFileNotFound() throws IOException {
		PowerMockito.mockStatic(FacesContext.class);
		
		Map<String, Object> session = new HashMap<String, Object>();
		session.put("video", "anynamethatgetsanerror.mp4");

		FacesContext context = FacesContextMocker.mockFacesContext();
		ExternalContext ext = Mockito.mock(ExternalContext.class);
		Mockito.when(ext.getSessionMap()).thenReturn(session);
		Mockito.when(context.getExternalContext()).thenReturn(ext);
		Mockito.when(FacesContext.getCurrentInstance()).thenReturn(context);

		WatchBean watch = new WatchBean();
		watch.getStream();		
	}
}

abstract class FacesContextMocker extends FacesContext {
	private FacesContextMocker() {
	}

	private static final Release RELEASE = new Release();

	private static class Release implements Answer<Void> {
		@Override
		public Void answer(InvocationOnMock invocation) throws Throwable {
			setCurrentInstance(null);
			return null;
		}
	}

	public static FacesContext mockFacesContext() {
		FacesContext context = Mockito.mock(FacesContext.class);
		setCurrentInstance(context);
		Mockito.doAnswer(RELEASE).when(context).release();
		return context;
	}
}
