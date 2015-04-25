package com.love320.stats;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.love320.stats.filter.ZString;
import com.love320.stats.filter.Zbase;

/**
 * App
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	ApplicationContext  context = new ClassPathXmlApplicationContext("classpath:spring-root.xml");
    	System.out.println("Start ...");
    }
}
