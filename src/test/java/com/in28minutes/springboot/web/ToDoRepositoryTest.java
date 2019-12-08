package com.in28minutes.springboot.web;

import com.in28minutes.springboot.web.model.Todo;
import com.in28minutes.springboot.web.service.TodoRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Ignore
public class ToDoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        Todo alexToDo = new Todo(1, "alex", "creating unit test", Date.from(Instant.now()),false);
        entityManager.persist(alexToDo);
        entityManager.flush();

        // when
        List<Todo> found = todoRepository.findByUser("alex");

        // then
        assertThat(found.get(0).getUser())
                .isEqualTo("alex");
    }
}
