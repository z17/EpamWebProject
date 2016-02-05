-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Время создания: Фев 05 2016 г., 15:37
-- Версия сервера: 5.6.16
-- Версия PHP: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База данных: `epam_restaurant`
--

-- --------------------------------------------------------

--
-- Структура таблицы `bill`
--

CREATE TABLE IF NOT EXISTS `bill` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` int(11) unsigned NOT NULL,
  `paid` tinyint(1) NOT NULL DEFAULT '0',
  `sum` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Структура таблицы `group`
--

CREATE TABLE IF NOT EXISTS `group` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Дамп данных таблицы `group`
--

INSERT INTO `group` (`id`, `name`) VALUES
(1, 'user'),
(2, 'admin');

-- --------------------------------------------------------

--
-- Структура таблицы `item`
--

CREATE TABLE IF NOT EXISTS `item` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `in_stock` tinyint(1) NOT NULL,
  `price` int(11) unsigned NOT NULL,
  `description` text,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Дамп данных таблицы `item`
--

INSERT INTO `item` (`id`, `name`, `in_stock`, `price`, `description`, `image`) VALUES
(1, 'Филадельфия с авокадо', 1, 199, 'Ролл с творожным сыром Филадельфия, лососем и авокадо', 'http://spb.delivery-club.ru/pcs/4451/8302844_b.jpg?1453284917'),
(2, 'Альфредо 730 г. 35 см (Тонкое тесто)', 1, 649, 'Сливочно-шпинатный соус, бекон, шампиньоны, ветчина, томаты, сыр Моцарелла', 'http://spb.delivery-club.ru/pcs/6426/7882899_b.jpg?1453283118'),
(3, 'Шашлык филе курицы', 1, 260, 'Нежное филе курицы в специальном маринаде. Аппетитный, сочный шашлык подходит даже детям! Доставка еды на дом порадует Вас и Ваших малышей!', 'http://spb.delivery-club.ru/pcs/1033/7948370_b.jpg?1453277716'),
(4, 'Сибас на вертеле', 1, 400, 'Доставка еды, шашлыка на дом предоставляет отличную возможность отведать пряную Сибас на вертеле. Прекрасная белая рыба из семейства сиговых. Блюдо, которое оценит любой гурман.', 'http://spb.delivery-club.ru/pcs/1033/7948374_b.jpg?1453277716');

-- --------------------------------------------------------

--
-- Структура таблицы `order`
--

CREATE TABLE IF NOT EXISTS `order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `price` int(11) unsigned NOT NULL,
  `status` int(11) unsigned NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Дамп данных таблицы `order`
--

INSERT INTO `order` (`id`, `user_id`, `price`, `status`, `time`) VALUES
(1, 2, 1958, 1, '2016-02-02 21:30:49'),
(2, 2, 400, 1, '2016-02-04 23:53:58'),
(3, 2, 260, 1, '2016-02-04 23:55:07');

-- --------------------------------------------------------

--
-- Структура таблицы `order_item`
--

CREATE TABLE IF NOT EXISTS `order_item` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` int(11) unsigned NOT NULL,
  `item_id` int(11) unsigned NOT NULL,
  `count` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `item_id` (`item_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Дамп данных таблицы `order_item`
--

INSERT INTO `order_item` (`id`, `order_id`, `item_id`, `count`) VALUES
(1, 1, 2, 2),
(2, 1, 4, 1),
(3, 1, 3, 1),
(4, 2, 4, 1),
(5, 3, 3, 1);

-- --------------------------------------------------------

--
-- Структура таблицы `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `group_id` int(11) unsigned NOT NULL,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `address` text,
  PRIMARY KEY (`id`),
  KEY `group_id` (`group_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Дамп данных таблицы `user`
--

INSERT INTO `user` (`id`, `group_id`, `login`, `password`, `name`, `email`, `phone`, `address`) VALUES
(1, 2, 'admin', 'b0e613a27562b20cdfab56f34988735a919b747691514e7e2778be674a5a3cd', 'Максим', 'sdgsdg@asdg.ru', '3464626', 'Парк победы 2'),
(2, 1, 'test', 'b0e613a27562b20cdfab56f34988735a919b747691514e7e2778be674a5a3cd', 'Тест', NULL, NULL, NULL);

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `bill`
--
ALTER TABLE `bill`
  ADD CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`);

--
-- Ограничения внешнего ключа таблицы `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `order_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Ограничения внешнего ключа таблицы `order_item`
--
ALTER TABLE `order_item`
  ADD CONSTRAINT `order_item_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
  ADD CONSTRAINT `order_item_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

--
-- Ограничения внешнего ключа таблицы `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
