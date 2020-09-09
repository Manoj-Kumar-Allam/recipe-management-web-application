package com.ma.recipeapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ma.recipeapp.model.UnitOfMeasure;
import com.ma.recipeapp.repository.UnitOfMeasureRepository;

/**
 * The class <code>UnitOfMeasureRepositoryTest</code> contains tests for the class <code>{@link UnitOfMeasureRepository}</code>.
 *
 * @generatedBy CodePro at 9/7/20 7:40 PM
 * @author manojkumara
 * @version $Revision: 1.0 $
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryITTest {
	
	@Autowired
	UnitOfMeasureRepository unitOfMeasureRepository;
	
	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 9/7/20 7:40 PM
	 */
	@Before
	public void setUp()
		throws Exception {
	}
	
	@Test
	public void testUOMRepository() {
		Optional<UnitOfMeasure> findByDescription = this.unitOfMeasureRepository.findByDescription("Teaspoon");
		assertEquals("Teaspoon", findByDescription.get().getDescription());
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 9/7/20 7:40 PM
	 */
	@After
	public void tearDown()
		throws Exception {
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 9/7/20 7:40 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(UnitOfMeasureRepositoryITTest.class);
	}
}