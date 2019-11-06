package nextstep.di.factory;

import nextstep.di.factory.example.JdbcUserRepository;
import nextstep.di.factory.example.MyQnaService;
import nextstep.di.factory.example.QnaController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BeanFactoryTest {
    private BeanFactory beanFactory;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setup() {
        Set<Class<?>> preInstanticateClazz = new BeanScanner("nextstep.di.factory.example").getPreInstantiateClass();
        beanFactory = new BeanFactory(preInstanticateClazz);
        beanFactory.initialize();
    }

    @Test
    public void di() throws Exception {
        QnaController qnaController = beanFactory.getBean(QnaController.class);

        assertNotNull(qnaController);
        assertNotNull(qnaController.getQnaService());

        MyQnaService qnaService = qnaController.getQnaService();
        assertNotNull(qnaService.getUserRepository());
        assertNotNull(qnaService.getQuestionRepository());
    }

    @Test
    @DisplayName("Bean들 중에서 Controller를 가져오는지 확인")
    void getControllers() {
        Map<Class<?>, Object> controllers = beanFactory.getControllers();

        assertThat(controllers.containsKey(QnaController.class)).isTrue();
        assertThat(controllers.containsKey(MyQnaService.class)).isFalse();
        assertThat(controllers.containsKey(JdbcUserRepository.class)).isFalse();
    }
}
