package com.example.jedisspring1519version.aoppoint;

import org.aspectj.lang.annotation.Pointcut;

/**
 * <p>AopPointCut class.</p>
 *
 * @author Administrator
 * @version $Id: $Id
 */
public class AopPointCut {


    /**
     * <p>mainPointcut.</p>
     */
    @Pointcut("execution(* redis.clients.jedis.BinaryJedis..*.*(..))")
    public void mainPointcut1() {
    }

    @Pointcut("execution(* redis.clients.jedis.Jedis..*.*(..))")
    public void mainPointcut2() {
    }

    @Pointcut("execution(* redis.clients.jedis.Client..*.*(..))")
    public void mainPointcut3() {
    }

    @Pointcut("execution(* redis.clients.jedis.Protocol..*.*(..))")
    public void mainPointcut4() {
    }

    @Pointcut("execution(* redis.clients.jedis.PipelineBase..*.*(..))")
    public void mainPointcut5() {
    }

    @Pointcut("execution(* redis.clients.jedis.MultiKeyPipelineBase..*.*(..))")
    public void mainPointcut6() {
    }

    @Pointcut("execution(* redis.clients.jedis.Pipeline..*.*(..))")
    public void mainPointcut7() {
    }


    /**
     * <p>All.</p>
     */
    @Pointcut("mainPointcut7()" +
            "|| mainPointcut6() || mainPointcut5() || mainPointcut4() || mainPointcut3() || mainPointcut2() ||" +
            "mainPointcut1() ")
    public void All() {
    }



}
