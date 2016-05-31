package my.practice.y2016;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface CustomTest {
	String author();
	String jira();
	String[] steps();
	Version version();
}
