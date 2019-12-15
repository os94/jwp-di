package nextstep.di.factory;

import nextstep.stereotype.Controller;
import nextstep.stereotype.Repository;
import nextstep.stereotype.Service;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public class ClasspathBeanScanner {
    private static final Logger logger = LoggerFactory.getLogger(ClasspathBeanScanner.class);

    private ClasspathBeanFactory beanFactory;
    private Reflections reflections;

    public ClasspathBeanScanner(ClasspathBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ClasspathBeanScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
    }

    public void doScan(Object... basePackage) {
        reflections = new Reflections(basePackage);
        // do more task to beanFactory
    }

    public Set<Class<?>> getPreInstantiateClass() {
        return getTypesAnnotatedWith(Controller.class, Service.class, Repository.class);
    }

    @SuppressWarnings("unchecked")
    private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
        Set<Class<?>> preInstantiateBeans = new HashSet<>();
        for (Class<? extends Annotation> annotation : annotations) {
            preInstantiateBeans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        logger.debug("Scan Beans Type : {}", preInstantiateBeans);
        return preInstantiateBeans;
    }
}
