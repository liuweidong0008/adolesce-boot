package com.boot.jdk8Speciality;

import com.boot.entity.Address;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.annotation.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.*;

/**
 * JDK1.8新特性
 * @author Lwd
 * 一、Lambda 表达式
 * 		1、Lambda 表达式，也可称为闭包，它是推动 Java 8 发布的最重要新特性。
 * 		2、Lambda 允许把函数作为一个方法的参数（函数作为参数传递进方法中）。
 * 		3、使用 Lambda 表达式可以使代码变的更加简洁紧凑。
 * 
 * 		4、语法:
 *			lambda 表达式的语法格式如下：
 *			(parameters) -> expression 或 (parameters) ->{ statements; }
 *	
 * 		5、重要特征
 *			1）、可选类型声明：不需要声明参数类型，编译器可以统一识别参数值。
 *			2）、可选的参数圆括号：一个参数无需定义圆括号，但多个参数需要定义圆括号。
 *			3）、可选的大括号：如果主体只包含了一个语句，就不需要使用大括号。
 *			4）、可选的返回关键字：如果主体只有一个表达式返回值则编译器会自动返回值，大括号需要指定明表达式返回了一个数值。
 *
 *
 * 二、函数式接口
 * 		1、有且仅有一个抽象方法，但是可以有多个非抽象方法的接口。可以被隐式转换为 lambda 表达式。
 *  	2、JDK 1.8 之前已有的函数式接口:
 *			1)、java.lang.Runnable
 *			2)、java.util.concurrent.Callable
 *			3)、java.security.PrivilegedAction
 *			4)、java.util.Comparator
 *			5)、java.io.FileFilter
 *			6)、java.nio.file.PathMatcher
 *			7)、java.lang.reflect.InvocationHandler
 *			8)、java.beans.PropertyChangeListener
 *			9)、java.awt.event.ActionListener
 *			10)、javax.swing.event.ChangeListener
 *  	3、JDK 1.8 新增加的函数接口：
 *			java.util.function, 它包含了很多类，用来支持 Java的 函数式编程  详见(http://www.runoob.com/java/java8-functional-interfaces.html)
 *
 *
 * 三、方法引用
 *  	1、方法引用通过方法的名字来指向一个方法。
 *		2、方法引用可以使语言的构造更紧凑简洁，减少冗余代码。
 *		3、方法引用使用一对冒号 :: 。
 *  		1)、构造器引用：它的语法是Class::new
 *  		2)、静态方法引用：它的语法是Class::static_method
 *  		3)、特定类的任意对象的方法引用：它的语法是Class::method
 *  		4)、特定对象的方法引用：它的语法是instance::method
 *  
 *  
 * 四、默认方法
 * 		简单说，默认方法就是接口可以有实现方法，而且不需要实现类去实现其方法。
 * 		我们只需在方法名前面加个default关键字即可实现默认方法。
 * 
 * 
 */
public class LambdaAndFunctionInterfaceTest {
	public LambdaAndFunctionInterfaceTest() {
	}

	//定义函数式接口，1.8新注解,主要用于编译级错误检查，加上该注解，当你写的接口不符合函数式接口定义的时候，编译器会报错。
	@FunctionalInterface
	interface Mathoperation{
		//只允许有一个抽象方法
	    int operation(int a, int b);
	    //函数式接口里是可以包含默认方法，因为默认方法不是抽象方法，其有一个默认实现，所以是符合函数式接口的定义的；
	    default int addition(int a, int b){
	        return a+b;
	    }  
	    //函数式接口里是可以包含静态方法，因为静态方法不能是抽象方法，是一个已经实现了的方法，所以是符合函数式接口的定义的；
	    static void printHello(){
	        System.out.println("Hello");
	    }
	    //函数式接口里是可以包含Object里的public方法，这些方法对于函数式接口来说，不被当成是抽象方法（虽然它们是抽象方法）；
		//因为任何一个函数式接口的实现，默认都继承了 Object 类，包含了来自 java.lang.Object 里对这些抽象方法的实现；
	    @Override
	    boolean equals(Object obj);
	}

	@FunctionalInterface
	interface GreetingServer{
	    void print(String message);
	}

	private static int operator(int a,int b,Mathoperation mathoperation){
		return mathoperation.operation(a,b);
    }

	public int thisadd(int a,int b){
		return a + b;
	}

	/**
	 * 测试自定义的函数式接口
	 */
	@Test
	public void test1(){
		//带有类型声明的表达式
		//这个表达式的目标类型必须是一个函数接口,只有一个方法的接口叫函数接口,这样的接口可以隐式地转换成lambda表达式
		Mathoperation add = (int a,int b) -> a + b;
		
	    //没有类型声明的表达式
	    Mathoperation sub = (a,b) -> a - b;

	    //带有大括号，带有返回语句的表达式
	    Mathoperation mul = (int a,int b) -> {return a * b;};

	    //没有大括号和return语句的表达式
	    Mathoperation div = (int a,int b) -> a / b;

	    //没有类型声明的表达式
		//也可以写成： (info) -> System.out.println(info); 或(String info) ->System.out.println(info);
	    GreetingServer printInfo =  info -> System.out.println(info);

	    //函数式接口/Lamdba表达式作为方法参数传递
	    printInfo.print("10 + 5 =" + operator(10,5,(int a,int b) -> a + b));
	    printInfo.print("10 - 5 =" + operator(10,5,sub));
	    printInfo.print("10 * 5 =" + operator(10,5,mul));
	    printInfo.print("10 / 5 =" + operator(10,5,div));
	    
	    LambdaAndFunctionInterfaceTest test = new LambdaAndFunctionInterfaceTest();
	    Mathoperation thisadd = test::thisadd;
	    System.out.println(thisadd.addition(2, 3));
	}


	/**
	 * 测试打印、排序等几种操作jdk8的写法
	 * @param s
	 */
	public static void print(String s){
		System.err.println(s);
	}
	@Test
	public void test2(){
		List<String> names = new ArrayList<String>();
		names.add("ab");
		names.add("a");
		names.add("abc");
		
		Consumer<String> comsumer1 = s -> System.out.println(s); 
		Consumer<String> comsumer2 = System.out::println; 
		Consumer<String> comsumer3 = LambdaAndFunctionInterfaceTest::print;
		names.forEach(comsumer3);

		names.forEach(this::myforEach);
		names.stream().forEach(this::myforEach);

		//JDK7 排序写法
	    /*Collections.sort(names, new Comparator<String>() {
	         @Override
	         public int compare(String s1, String s2) {
	            return s1.compareTo(s2);
	         }
	      });*/
	    //JDK8 排序写法
	    Collections.sort(names, (s1, s2) -> s1.compareTo(s2));
	    
	    GreetingServer printInfo = System.out::println;
	    printInfo.print(names.toString());
	    
	    String[]str = {"1","5","2","100","25","66"};
		String[]str1 = {"1","5","2","1","4","66"};
	    Arrays.sort(str1, String::compareTo);
	    //[1, 100, 2, 25, 5, 66]
	    System.out.println(Arrays.asList(str1).toString());
	    System.out.println("123");

	}

	public void myforEach(String str){
		String str2= str + "你好";
		System.out.println(str2);
	}

	/**
	 * lambda 表达式只能引用标记了 final 的外层局部变量，这就是说不能在 lambda 内部修改定义在域外的局部变量，否则会编译错误。
	 */
	@Test
	public void test3(){
		 final int num = 1;	//可以不用声明为 final，但是必须不可被后面的代码修改（即隐性的具有 final 的语义）
		 GreetingServer printInfo = s -> System.out.println(num+s);
		 printInfo.print("23");
	}
	
	/**
	 * JDK 1.8 新增加的函数接口：java.util.function 测试
	 * http://www.runoob.com/java/java8-functional-interfaces.html
	 */
	@Test
	public void test4(){
		//BiConsumer<T,U>代表了一个接受两个输入参数的操作，并且不返回任何结果
		BiConsumer<Integer,String> bigConsumer = (a,b) -> System.out.println(a+b);
		bigConsumer.accept(12, "34");

		//BiFunction<T,U,R>代表了一个接受两个输入参数的方法，并且返回一个结果
		BiFunction<Integer,String,String> biFunction= (a,b) -> a+b;
		System.out.println(biFunction.apply(12, "34"));

		//BinaryOperator<T>代表了一个作用于于两个同类型操作符的操作，并且返回了操作符同类型的结果
		BinaryOperator<String> binaryOperator = (a,b) -> a+b;
		System.out.println(binaryOperator.apply("12", "34"));

		//BiPredicate<T,U>代表了一个两个参数的boolean值方法
		BiPredicate<Integer,Integer> biPredicate = (a,b) -> a>b;
		System.out.println(biPredicate.test(1, 2));

		//Consumer<T>代表了接受一个输入参数并且无返回的操作
		Consumer<String> consumer = System.out::println;

		//Function<T,R>接受一个输入参数，返回一个结果。
		Function<String,String> function = a -> a.toString();

		//Predicate<T> 接受一个输入参数，返回一个布尔值结果。
		Predicate<String> predicate = a -> a.equals("aaa");

		//Supplier<T> 无参数，返回一个结果。
		Supplier<String> supplier = () -> "aa";

		//UnaryOperator<T> 接受一个参数为类型T,返回值类型也为T。
		UnaryOperator<Address> unaryOperator = a -> a;
	}
	
	@Test
	public void test6() {
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");

		String name = "Runoob";
		Integer result = null;

		try {
			nashorn.eval("print('" + name + "')");
			result = (Integer) nashorn.eval("10 + 2");

		} catch (ScriptException e) {
			System.out.println("执行脚本错误: " + e.getMessage());
		}

		System.out.println(result.toString());
	}

	
    /**
     * 数组并行（parallel）操作
     */
    @Test
    public void testParallel(){
        long[] arrayOfLong = new long [ 20000 ];        
        //1.给数组随机赋值
        Arrays.parallelSetAll( arrayOfLong, 
            index -> ThreadLocalRandom.current().nextInt( 1000000 ) );
        
        Arrays.stream(arrayOfLong).forEach(System.out::println);
        //2.打印出前10个元素
       /* Arrays.stream( arrayOfLong ).limit( 10 ).forEach( 
            i -> System.out.print( i + " " ) );
        System.out.println();
        //3.数组排序
        Arrays.parallelSort( arrayOfLong );     
        //4.打印排序后的前10个元素
        Arrays.stream( arrayOfLong ).limit( 10 ).forEach( 
            i -> System.out.print( i + " " ) );
        System.out.println();*/
    }
    
    
    
    
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Filters {
		Filter[] value();
	}

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Repeatable(Filters.class)
	public @interface Filter {
		String value();

		String value2();
	};

	@Filter(value = "filter1", value2 = "111")
	@Filter(value = "filter2", value2 = "222")
	// @Filters({@Filter( value="filter1",value2="111" ),@Filter(value="filter2", value2="222")}).注意：JDK8之前：1.没有@Repeatable2.采用本行“注解容器”写法
	public interface Filterable {
	}
	
	@Test
	public void test7(){
		// 获取注解后遍历打印值
		for (Filter filter : Filterable.class.getAnnotationsByType(Filter.class)) {
			System.out.println(filter.value() + filter.value2());
		}
	}
	
	@Test
	public void test8(){
		//允许为空值，当值为空时也不会报错
		Optional< String > name = Optional.ofNullable( null );
		System.out.println( "name是否有值 " + name.isPresent() );        
		System.out.println( "如果名称为空: " + name.orElseGet( () -> "代替名称" ) ); 
		System.out.println( name.map( s -> "名称为： " + s  ).orElse( "名称为空" ) );

		//不允许为空值，当值为空时会报错
		Optional< String > firstName = Optional.of( "name" );
		System.out.println( "firstName是否有值 " + firstName.isPresent() );        
		System.out.println( "如果名称为空: " + firstName.orElseGet( () -> "代替名称" ) ); 
		System.out.println( firstName.map( s -> "名称为： " + s  ).orElse( "名称为空" ) );
	}
}
