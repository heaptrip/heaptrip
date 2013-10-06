package com.heaptrip.service.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.category.Category;
import com.heaptrip.domain.repository.category.CategoryRepository;
import com.heaptrip.domain.service.category.CategoryService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class CategoryServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryService categoryService;

	private List<Category> categories = new ArrayList<Category>();

	@BeforeClass
	public void init() {
		categories.add(new Category("1", null, null, "Познавательные туры", "Informative tours"));
		categories.add(new Category("1.1", "1", new String[] { "1" }, "Осмотр достопримечательностей", "Sightseeing"));
		categories.add(new Category("1.2", "1", new String[] { "1" }, "Экзотические туры", "Exotic tours"));
		categories.add(new Category("1.3", "1", new String[] { "1" }, "Экотуризм", "Ecotourism"));
		categories.add(new Category("2", null, null, "Спорт & Туризм", "Sports & Tourism"));
		categories.add(new Category("2.1", "2", new String[] { "2" }, "АвтоМото", "Autos"));
		categories.add(new Category("2.2", "2", new String[] { "2" }, "Авиаспорт", "Air sports"));
		categories.add(new Category("2.2.1", "2.2", new String[] { "2", "2.2" }, "Воздухоплавание", "Aerostation"));
		categories.add(new Category("2.2.2", "2.2", new String[] { "2", "2.2" }, "Парашютизм", "Parachutism"));
		categories.add(new Category("2.2.3", "2.2", new String[] { "2", "2.2" }, "Планеризм", "Gliding"));
		categories.add(new Category("2.2.4", "2.2", new String[] { "2", "2.2" }, "Парапланеризм", "Paraplanerism"));
		categories.add(new Category("2.2.5", "2.2", new String[] { "2", "2.2" }, "Дельтапланеризм", "Hang gliding"));
		categories.add(new Category("2.2.6", "2.2", new String[] { "2", "2.2" }, "Самолеты & Вертолеты",
				"Planes & Helicopters"));
		categories.add(new Category("2.3", "2", new String[] { "2" }, "Велотуризм", "Biking"));
		categories.add(new Category("2.4", "2", new String[] { "2" }, "Водные виды", "Watersports"));
		categories.add(new Category("2.4.1", "2.4", new String[] { "2", "2.4" }, "Сплав", "River rafting"));
		categories.add(new Category("2.4.2", "2.4", new String[] { "2", "2.4" }, "Рафтинг", "Rafting"));
		categories.add(new Category("2.4.3", "2.4", new String[] { "2", "2.4" }, "Парусный спорт", "Sailing"));
		categories.add(new Category("2.4.4", "2.4", new String[] { "2", "2.4" }, "Каякинг", "Kayaking"));
		categories.add(new Category("2.4.5", "2.4", new String[] { "2", "2.4" }, "Дайвинг", "Diving"));
		categories.add(new Category("2.4.6", "2.4", new String[] { "2", "2.4" }, "Серфинг", "Surfing"));
		categories.add(new Category("2.4.7", "2.4", new String[] { "2", "2.4" }, "Яхтинг", "Yachting"));
		categories.add(new Category("2.5", "2", new String[] { "2" }, "Горный туризм", "Mountain tourism"));
		categories.add(new Category("2.6", "2", new String[] { "2" }, "Спелеология", "Speleology"));
		categories.add(new Category("2.7", "2", new String[] { "2" }, "Пешеходный", "Hiking"));
		categories.add(new Category("2.8", "2", new String[] { "2" }, "Лыжный", "Skiing"));
		categories.add(new Category("2.9", "2", new String[] { "2" }, "Горнолыжный", "Downhill skiing"));
		categories.add(new Category("2.10", "2", new String[] { "2" }, "Конный туризм", "Horse riding"));
		categories.add(new Category("2.11", "2", new String[] { "2" }, "Охота & Рыбалка", "Hunting & Fishing"));
		categories.add(new Category("2.12", "2", new String[] { "2" }, "Спортивное ориентирование", "Orienteering"));
		categories.add(new Category("3", null, null, "Круизы", "Cruises"));
		categories.add(new Category("3.1", "3", new String[] { "3" }, "Морские круизы", "Sea cruises"));
		categories.add(new Category("3.2", "3", new String[] { "3" }, "Речные круизы", "River cruises"));
		categories.add(new Category("4", null, null, "Пляжный отдых", "Beach"));
		categories.add(new Category("5", null, null, "Шопинг", "Shopping"));
		categories.add(new Category("6", null, null, "Оздоровительные туры", "Health tours"));
		categories.add(new Category("7", null, null, "Автостоп", "hitch-hiking"));
		categories.add(new Category("8", null, null, "Обучающие туры", "Study tours"));
		categories.add(new Category("9", null, null, "Событийные туры", "Event tours"));
		categories.add(new Category("10", null, null, "Паломнические туры", "Pilgrimage tours"));
		categories.add(new Category("11", null, null, "Самостоятельная поездка", "Self drive"));
	}

	@Test(priority = 0)
	public void removeAll() {
		categoryRepository.removeAll();
	}

	@Test(priority = 1)
	public void save() {
		categoryRepository.save(categories);
	}

	@Test(priority = 2)
	public void findById() {
		String id = "2.4.7";
		Category category = categoryRepository.findOne(id);
		Assert.assertNotNull(category);
		Assert.assertEquals(category.getId(), id);
	}

	@Test(priority = 3)
	public void findAll() {
		List<Category> categories = categoryService.getCategories(Locale.ENGLISH);
		Assert.assertEqualsNoOrder(categories.toArray(), this.categories.toArray());
	}

	@Test(priority = 4)
	public void getParents() {
		List<String> parentIds = categoryService.getParentsByCategoryId("2.2.6");
		Assert.assertNotNull(parentIds);
		Assert.assertEquals(parentIds.size(), 2);
		Assert.assertTrue(parentIds.contains("2"));
		Assert.assertTrue(parentIds.contains("2.2"));
	}
}
