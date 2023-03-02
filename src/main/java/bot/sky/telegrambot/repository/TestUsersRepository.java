package bot.sky.telegrambot.repository;

import bot.sky.telegrambot.models.TestUserForMenu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestUsersRepository extends CrudRepository<TestUserForMenu, Long>{
}
