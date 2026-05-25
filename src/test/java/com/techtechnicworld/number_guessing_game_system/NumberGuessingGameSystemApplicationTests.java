package com.techtechnicworld.number_guessing_game_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.mock.web.MockHttpSession;

import com.techtechnicworld.number_guessing_game_system.controller.GameController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NumberGuessingGameSystemApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void homePageStartsGame() throws Exception {
		GameController controller = new GameController();
		MockHttpSession session = new MockHttpSession();
		ExtendedModelMap model = new ExtendedModelMap();

		String viewName = controller.index(session, model);

		assertThat(viewName).isEqualTo("index");
		assertThat(model.get("attemptsRemaining")).isEqualTo(10);
		assertThat(model.get("score")).isEqualTo(0);
		assertThat(model.get("roundsWon")).isEqualTo(0);
		assertThat(model.get("roundNumber")).isEqualTo(1);
		assertThat(model.get("gameOver")).isEqualTo(false);
	}

	@Test
	void invalidGuessReturnsValidationFeedback() throws Exception {
		GameController controller = new GameController();
		MockHttpSession session = new MockHttpSession();
		ExtendedModelMap model = new ExtendedModelMap();

		String viewName = controller.guess(101, session, model);

		assertThat(viewName).isEqualTo("index");
		assertThat(model.get("feedback")).isEqualTo("Please enter a number between 1 and 100.");
		assertThat(model.get("feedbackType")).isEqualTo("highlow");
		assertThat(model.get("attemptsRemaining")).isEqualTo(10);
	}

	@Test
	void correctGuessEndsRoundAndUpdatesScore() throws Exception {
		GameController controller = new GameController();
		MockHttpSession session = new MockHttpSession();
		ExtendedModelMap model = new ExtendedModelMap();
		session.setAttribute("randomNumber", 42);
		session.setAttribute("attemptsRemaining", 10);
		session.setAttribute("score", 0);
		session.setAttribute("roundsWon", 0);
		session.setAttribute("roundNumber", 1);
		session.setAttribute("gameOver", false);

		String viewName = controller.guess(42, session, model);

		assertThat(viewName).isEqualTo("index");
		assertThat(model.get("feedback")).isEqualTo("Correct! You guessed the number.");
		assertThat(model.get("feedbackType")).isEqualTo("correct");
		assertThat(model.get("score")).isEqualTo(90);
		assertThat(model.get("roundsWon")).isEqualTo(1);
		assertThat(model.get("gameOver")).isEqualTo(true);
	}

	@Test
	void playAgainWorksEvenWithoutExistingSession() throws Exception {
		GameController controller = new GameController();
		MockHttpSession session = new MockHttpSession();
		ExtendedModelMap model = new ExtendedModelMap();

		String viewName = controller.playAgain(session, model);

		assertThat(viewName).isEqualTo("index");
		assertThat(model.get("attemptsRemaining")).isEqualTo(10);
		assertThat(model.get("score")).isEqualTo(0);
		assertThat(model.get("roundsWon")).isEqualTo(0);
		assertThat(model.get("roundNumber")).isEqualTo(1);
		assertThat(model.get("gameOver")).isEqualTo(false);
	}

}
