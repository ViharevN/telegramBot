package bot.sky.telegrambot.repository;

import bot.sky.telegrambot.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<User, Long>{
}
