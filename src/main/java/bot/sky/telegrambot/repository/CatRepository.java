package bot.sky.telegrambot.repository;

<<<<<<< HEAD
public interface CatRepository {

=======
import bot.sky.telegrambot.models.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
>>>>>>> develop
}
