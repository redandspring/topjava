package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {

        save(new User(null, "Jacob", "zzzzz@email.com", "1234", Role.ROLE_ADMIN));
        save(new User(null, "Jacob", "james@email.com", "1234", Role.ROLE_USER));
        save(new User(null, "Ethan", "ethan@email.com", "1234", Role.ROLE_USER));
        save(new User(null, "Logan", "logan@email.com", "1234", Role.ROLE_USER));

    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return (repository.remove(id) != null);
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        LOG.info("save " + user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        return repository.values().stream()
                .sorted((o1, o2) -> {
                    int comp = o1.getName().compareToIgnoreCase(o2.getName());
                    return (comp == 0) ? o1.getEmail().compareToIgnoreCase(o2.getEmail()) : comp;

                })
                .collect(Collectors.toList());

    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        return repository.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElseGet(() -> null);
    }
}
