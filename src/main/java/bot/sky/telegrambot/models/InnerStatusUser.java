package bot.sky.telegrambot.models;


//Класс-заглушка.
//Класс нужен для другого класса-заглушки TestUserForMenu
//Этот класс надо удалить когда будет настроенный класс User

public enum InnerStatusUser {
    NEW_VISITOR("посетитель, который не выбрал тип приюта"),
    NOT_REGISTERED_USER("посетитель, который выбрал тип приюта, но не прошел регистрацию"),
    REGISTERED_USER("посетитель, который прошел регистрацию, но еще не взял питомца"),
    USER_WITH_PET("посетитель, который прошел регистрацию, и взял взял питомца");

    String aboutStatus;

    InnerStatusUser(String aboutStatus) {
        this.aboutStatus = aboutStatus;
    }
}
