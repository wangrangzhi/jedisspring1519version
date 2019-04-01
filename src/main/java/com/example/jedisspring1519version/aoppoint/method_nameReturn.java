package com.example.jedisspring1519version.aoppoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import feign.FeignException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.UUID;


/**
 * <p>method_nameReturn class.</p>
 *
 * @author Administrator
 * @version $Id: $Id
 */
@Component
@Aspect
//声明切面
@ComponentScan  //组件自动扫描
//spring自动切换JDK动态代理和CGLIB

@Configuration
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
public class method_nameReturn {

    static final Logger LOG = LoggerFactory.getLogger(method_nameReturn.class);

    /**
     * <p>Constructor for method_nameReturn.</p>
     *
     * @throws UnknownHostException if any.
     */
    public method_nameReturn() throws UnknownHostException {
    }


    /**
     * 打印类method的名称以及参数
     *
     * @param point 切面
     * @param tempTime a {@link String} object.
     * @return a {@link com.alibaba.fastjson.JSONObject} object.
     * @throws Exception if any.
     */
    public static JSONObject printMethodParams(JoinPoint point, String tempTime) throws Exception {
        if (point == null) {
            return null;
        }


        String cmn = point.getSignature().toString();
        String classnameRaw = point.getTarget().getClass().getName();
        String classname = null;
        if (classnameRaw.contains("$$")) {

            String[] a = classnameRaw.split("\\$\\$");
            classname = a[0];
        } else {
            classname = classnameRaw;
        }

        if (classname == null) {
            throw new Exception("null classname");
        }


        JSONObject paramJson = new JSONObject();

        if (classname.contains("com.sun.proxy")) {
            cmn = point.getSignature().toString();

        }

        paramJson.put("cmn", cmn);


        Object[] method_args = point.getArgs();

        try {

            Signature signature = point.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            String[] ParameterNames = methodSignature.getParameterNames();


            String param_name = logParam(ParameterNames, method_args);

            String unescapeJava = StringEscapeUtils.unescapeJava(param_name.toString());

            LOG.info("inputpParam_name:" + unescapeJava);


        } catch (Exception e) {

            e.printStackTrace();


        }
        return paramJson;
    }

    /**
     * <p>isJSONValid.</p>
     *
     * @param test a {@link String} object.
     * @return a boolean.
     */
    public final static boolean isJSONValid(String test) {
        try {
            JSONObject.parseObject(test);
        } catch (JSONException ex) {
            try {
                JSONObject.parseArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }


    /**
     * 判断是否为基本类型：包括String
     *
     * @param clazz clazz
     * @return true：是;     false：不是
     */
    private static boolean isPrimite(Class<?> clazz) {
        if (clazz.isPrimitive() || clazz == String.class) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 打印方法参数值  基本类型直接打印，非基本类型需要重写toString方法
     *
     * @param paramsArgsName  方法参数名数组
     * @param paramsArgsValue 方法参数值数组
     */
    private static String logParam(String[] paramsArgsName, Object[] paramsArgsValue) {
        StringBuffer buffer = new StringBuffer();
        if (ArrayUtils.isEmpty(paramsArgsName) || ArrayUtils.isEmpty(paramsArgsValue)) {
            buffer.append("{\"param\": \"no param\"}");
            return buffer.toString();
        }
        for (int i = 0; i < paramsArgsName.length; i++) {

            String name = paramsArgsName[i];

            Object value = paramsArgsValue[i];
            buffer.append("\"" + name + "\":");
            if (isPrimite(value.getClass())) {
                buffer.append("\"" + value + "\",");
            } else {


                try {
                    if (value != null) {
                        buffer.append(JSON.toJSONString(value) + ",");
                    }


                } catch (Exception e) {
                    // e.printStackTrace();

                    return "{\"param\": \" can't print param\"}";

                }
            }

        }

        return "{" + buffer.toString().substring(0, buffer.toString().length() - 1) + "}";
    }


    /**
     * 定义一个切入点.
     * 解释下：
     * <p>
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 定义在web包或者子包
     * ~ 第三个 * 任意方法
     * ~ .. 匹配任意数量的参数.
     *
     * @param joinPoint a {@link ProceedingJoinPoint} object.
     * @return a {@link Object} object.
     * @throws Throwable if any.
     */


   @Around("com.example.jedisspring1519version.aoppoint.AopPointCut.All()")
    public Object doAroundf(ProceedingJoinPoint joinPoint) throws Throwable {
        //long a1 = System.currentTimeMillis();

        JSONObject jsonreturn = new JSONObject();
        long start = System.currentTimeMillis();

        try {

            jsonreturn = getTheReturnJson(joinPoint, String.valueOf(start));
            jsonreturn.put("sparkAnalyseIdentifyStringBefore", "v1");

            UUID uuid = UUID.randomUUID();

            jsonreturn.put("UU", uuid.toString());

            LOG.info(jsonreturn.toJSONString());
         //   long a2 = System.currentTimeMillis();
         //   System.out.println("before: "+(a2-a1)+"ms");

            Object result = joinPoint.proceed();
         //   long a3 = System.currentTimeMillis();
            long end = System.currentTimeMillis();

            jsonreturn.put("sparkAnalyseIdentifyStringAfter", "v1");
            jsonreturn.remove("sparkAnalyseIdentifyStringBefore");
            jsonreturn.put("CT", end - start);
            jsonreturn.put("ST", "10001");


            LOG.info(jsonreturn.toJSONString());

            long a4 = System.currentTimeMillis();
        //    System.out.println("after: "+(a3-a4)+"ms");
            return result;

        } catch (Exception e) {


            if (e instanceof FeignException) {
                jsonreturn.put("ST", ((FeignException) e).status());
            }


            long end = System.currentTimeMillis();


            jsonreturn.put("CT", end - start);


            jsonreturn.put("getMessage", StringEscapeUtils.unescapeJava(e.getMessage()));

            Writer resultgetCause = new StringWriter();
            PrintWriter printWriter = new PrintWriter(resultgetCause);
            e.printStackTrace(printWriter);

            jsonreturn.put("getCause", StringEscapeUtils.unescapeJava(resultgetCause.toString()));
            jsonreturn.put("sparkAnalyseIdentifyStringAfter", "v1");
            jsonreturn.remove("sparkAnalyseIdentifyStringBefore");
            LOG.error(jsonreturn.toJSONString());


            throw e;
        }

    }


    static String TheNameAdStartTimeAndIpAndPort;



    private JSONObject getTheReturnJson(ProceedingJoinPoint joinPoint, String tempTime) throws Exception {

        JSONObject paramJson = printMethodParams(joinPoint, String.valueOf(tempTime));


        JSONObject returnJson = new JSONObject();

        if (paramJson != null) {
            returnJson.put("cmn", paramJson.get("cmn"));
        }


        //returnJson.put("SpringInstance", getTheNameAndStartTimeAndIpAndPort());


        returnJson.put("ip", IP);
        returnJson.put("port", port);
//        returnJson.put("uri", geturi());
//        returnJson.put("url", geturl());

//        returnJson.put("httpMethodConfig", gethttpMethodConfig());


//        returnJson.put("trace", MDC.get("X-B3-TraceId"));
//        returnJson.put("span", MDC.get("X-B3-SpanId"));
//        returnJson.put("parent", MDC.get("X-B3-ParentSpanId"));


//        returnJson.put("pid", ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
//        returnJson.put("thread", Thread.currentThread().getName());


//
//        Signature signature = joinPoint.getSignature();
//        MethodSignature methodSignature = (MethodSignature) signature;
//        Method targetMethod = methodSignature.getMethod();
//
//        Annotation[] AnnList = targetMethod.getDeclaredAnnotations();
//        Map<String, Annotation> Anno = new HashMap<>();
//        if (AnnList.length > 0) {
//
//
//            for (Annotation a : AnnList) {
//                Anno.put(AnnList[0].annotationType().getName(), a);
//            }
//
//
//        }
//
//        returnJson.put("Annotation", Anno);


//        String chars = "abcdefghijklmnopqrstuvwxyz";
//        chars.charAt((int) (Math.random() * 26));
//
//
//        String a = String.valueOf(chars.charAt((int) (Math.random() * 26)));
//        String b = String.valueOf(chars.charAt((int) (Math.random() * 26)));
        returnJson.put("loginUserId", "aa");


        return returnJson;


    }

    private String gethttpMethodConfig() {

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        if (sra == null) {
            return "null";
        }

        HttpServletRequest request = sra.getRequest();


        String method = request.getMethod();


        return method;
    }

    @Value("${spring.application.name}")
    String springapplicationname;


    private String geturl() {

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        if (sra == null) {
            return "null";
        }

        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();


        return url;
    }

    private String geturi() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        if (sra == null) {
            return "null";
        }

        HttpServletRequest request = sra.getRequest();


        String uri = request.getRequestURI();


        return uri;
    }

    @Value("${server.port}")
    String port;

    public String IP = InetAddress.getLocalHost().getHostAddress();




    /**
     * <p>Getter for the field <code>port</code>.</p>
     *
     * @return a {@link String} object.
     */
    public String getPort() {
        Yaml yaml = new Yaml();
        Map<String, Object> ret = (Map<String, Object>) yaml.load(this.getClass().getClassLoader()
                .getResourceAsStream("application.yml"));


        return ((Map<String, Object>) (ret.get("server"))).get("port").toString();

    }
}
