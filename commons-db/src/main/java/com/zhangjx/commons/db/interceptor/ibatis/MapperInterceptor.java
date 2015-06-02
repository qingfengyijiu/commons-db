package com.zhangjx.commons.db.interceptor.ibatis;

import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.context.ConfigurableApplicationContext;

@Intercepts({@Signature(type=MapperProxy.class, method="invoke", args={Object.class, Method.class, Object[].class})})
public class MapperInterceptor implements Interceptor {
	
	private Set<String> baseMethods = new TreeSet<String>();
	
	//private Set<SoftReference<>>
	
	private static final String BASE_METHOD_PROPERTY = "baseMethod";

	public Object intercept(Invocation invocation) throws Throwable {
		MapperProxy mapperProxy = (MapperProxy)invocation.getTarget();
		Object[] proxyArgs = invocation.getArgs();
		Object proxy = proxyArgs[0];
		Method method = (Method)proxyArgs[1];
		Object[] args = (Object[])proxyArgs[2];
		String methodName = method.getName();
		if(this.baseMethods.contains(methodName)) {
			
		}
		return invocation.proceed();
	}

	public Object plugin(Object target) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setProperties(Properties properties) {
		String methods = properties.getProperty(BASE_METHOD_PROPERTY);
		StringTokenizer st = new StringTokenizer(methods, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
		while(st.hasMoreTokens()) {
			String baseMethod = st.nextToken();
			if(StringUtils.isNotBlank(baseMethod)) {
				this.baseMethods.add(baseMethod);
			}
		}
	}

}
