package com.heaptrip.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heaptrip.domain.entity.CategoryEntity;
import com.heaptrip.domain.repository.CategoryRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoryRepositoryTest {

	List<CategoryEntity> categories = new ArrayList<>();

	@Before
	public void init() {
		categories.add(new CategoryEntity("1", null, "Познавательные туры", "Informative tours"));
		categories.add(new CategoryEntity("1.1", "1", "Осмотр достопримечательностей", "Sightseeing"));
		categories.add(new CategoryEntity("1.2", "1", "Экзотические туры", "Exotic tours"));
		categories.add(new CategoryEntity("1.3", "1", "Экотуризм", "Ecotourism"));
		categories.add(new CategoryEntity("2", null, "Спорт & Туризм", "Sports & Tourism"));
		categories.add(new CategoryEntity("2.1", "2", "АвтоМото", "Autos"));
		categories.add(new CategoryEntity("2.2", "2", "Авиаспорт", "Air sports"));
		categories.add(new CategoryEntity("2.2.1", "2.2", "Воздухоплавание", "Aerostation"));
		categories.add(new CategoryEntity("2.2.2", "2.2", "Парашютизм", "Parachutism"));
		categories.add(new CategoryEntity("2.2.3", "2.2", "Планеризм", "Gliding"));
		categories.add(new CategoryEntity("2.2.4", "2.2", "Парапланеризм", "Paraplanerism"));
		categories.add(new CategoryEntity("2.2.5", "2.2", "Дельтапланеризм", "Hang gliding"));
		categories.add(new CategoryEntity("2.2.6", "2.2", "Самолеты & Вертолеты", "Planes & Helicopters"));
		categories.add(new CategoryEntity("2.3", "2", "Велотуризм", "Biking"));
		categories.add(new CategoryEntity("2.4", "2", "Водные виды", "Watersports"));
		categories.add(new CategoryEntity("2.4.1", "2.4", "Сплав", "River rafting"));
		categories.add(new CategoryEntity("2.4.2", "2.4", "Рафтинг", "Rafting"));
		categories.add(new CategoryEntity("2.4.3", "2.4", "Парусный спорт", "Sailing"));
		categories.add(new CategoryEntity("2.4.4", "2.4", "Каякинг", "Kayaking"));
		categories.add(new CategoryEntity("2.4.5", "2.4", "Дайвинг", "Diving"));
		categories.add(new CategoryEntity("2.4.6", "2.4", "Серфинг", "Surfing"));
		categories.add(new CategoryEntity("2.4.7", "2.4", "Яхтинг", "Yachting"));
		categories.add(new CategoryEntity("2.5", "2", "Горный туризм", "Mountain tourism"));
		categories.add(new CategoryEntity("2.6", "2", "Спелеология", "Speleology"));
		categories.add(new CategoryEntity("2.7", "2", "Пешеходный", "Hiking"));
		categories.add(new CategoryEntity("2.8", "2", "Лыжный", "Skiing"));
		categories.add(new CategoryEntity("2.9", "2", "Горнолыжный", "Downhill skiing"));
		categories.add(new CategoryEntity("2.10", "2", "Конный туризм", "Horse riding"));
		categories.add(new CategoryEntity("2.11", "2", "Охота & Рыбалка", "Hunting & Fishing"));
		categories.add(new CategoryEntity("2.12", "2", "Спортивное ориентирование", "Orienteering"));
		categories.add(new CategoryEntity("3", null, "Круизы", "Cruises"));
		categories.add(new CategoryEntity("3.1", "3", "Морские круизы", "Sea cruises"));
		categories.add(new CategoryEntity("3.2", "3", "Речные круизы", "River cruises"));
		categories.add(new CategoryEntity("4", null, "Пляжный отдых", "Beach"));
		categories.add(new CategoryEntity("5", null, "Шопинг", "Shopping"));
		categories.add(new CategoryEntity("6", null, "Оздоровительные туры", "Health tours"));
		categories.add(new CategoryEntity("7", null, "Автостоп", "hitch-hiking"));
		categories.add(new CategoryEntity("8", null, "Обучающие туры", "Study tours"));
		categories.add(new CategoryEntity("9", null, "Событийные туры", "Event tours"));
		categories.add(new CategoryEntity("10", null, "Паломнические туры", "Pilgrimage tours"));
		categories.add(new CategoryEntity("11", null, "Самостоятельная поездка", "Self drive"));
	}

	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	@Ignore
	public void test1RemoveAll() {
		categoryRepository.removeAll();
	}

	@Test
	@Ignore
	public void test2SaveAll() {
		categoryRepository.save(categories);
	}

	@Test
	public void test3FindAll() {
		List<CategoryEntity> categories = categoryRepository.findAll();

		// compare list without order
		Set<CategoryEntity> expecteds = new HashSet<>();
		Set<CategoryEntity> actuals = new HashSet<>();

		expecteds.addAll(this.categories);
		actuals.addAll(categories);

		Assert.assertEquals(expecteds, actuals);
	}
}
