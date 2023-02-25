package bot.sky.telegrambot.models;

public enum UserRoles {
    GUEST("Гость"), PARENT("Усыновитель"), ADMINISTRATOR("Администратор");

    private String roles;

    UserRoles(String roles) {
        this.roles = roles;
    }
}
