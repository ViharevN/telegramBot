package bot.sky.telegrambot.bot.commands.dog_shelter;

import lombok.Data;
import lombok.NoArgsConstructor;

//Класс надо удалить - текстовые данные перенесены в файл

@Data
@NoArgsConstructor
public class AdoptDogCommand {
    private final String ADOPT_ANIMAL_MENU_TXT = """
            Здесь Вы можете узнать как взять питомца из приюта.
            Выберите один из вариантов запроса:
            
            /meeting_with_pet - знакомство с питомцем в приюте
            
            /documents_for_adopting - список документов, для оформления питомца
            
            /recommendations_for_transporting - рекомендации по перевозке питомца
            
            /improvement_home_for_puppy - список рекомендаций по обустройству дома для щенка
            
            /improvement_home_for_adult_dog - список рекомендаций по обустройству дома для взрослой собаки
            
            /improvement_home_for_disabled_dog - список рекомендаций по обустройству дома для собаки с ограниченными возможностями (зрение, передвижение)

            /initial_appeal_with_dog - советы кинолога по первичному обращению с собакой
            
            /contacts_of_cynologists - контакты опытных кинологов
            
            /reasons_for_rejection - список причин, почему могут отказать и не дать разрешение на оформление питомца

            /volunteer - пообщаться с волонтером если есть еще вопросы""";}
