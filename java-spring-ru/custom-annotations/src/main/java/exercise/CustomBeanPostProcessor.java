package exercise;

import java.lang.reflect.Proxy;

import exercise.calculator.Calculator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// BEGIN
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor{
    private String logLevel;
    private Object inspectBean;
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Inspect.class)) {
            inspectBean = bean;
            logLevel = bean.getClass().getAnnotation(Inspect.class).level();
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean == inspectBean && bean.getClass().getName().equals("exercise.calculator.CalculatorImpl")){
            Calculator proxyInstance = (Calculator) Proxy.newProxyInstance(
                    bean.getClass().getClassLoader(),
                    bean.getClass().getInterfaces(),
                    (proxy, method, args) -> {
                        Logger logger = LoggerFactory.getLogger(bean.getClass());
                        String argsStr = "[";
                        if (args != null && args.length !=0) {
                            argsStr += args[0].toString();
                            for (int i = 1; i < args.length; i++){
                                argsStr += ", " + args[i].toString();
                            }
                        }
                        argsStr += "]";
                        if (logLevel.equals("info")) {
                            logger.info("Was called method: " + method.getName() + "() with arguments: " + argsStr);
                        }
                        if (logLevel.equals("debug")) {
                            logger.debug("Was called method: " + method.getName() + "() with arguments: " + argsStr);
                        }

                        return method.invoke(bean, args);
                    });
            return proxyInstance;
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
// END
