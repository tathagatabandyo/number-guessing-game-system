package com.techtechnicworld.number_guessing_game_system.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
public class GameController {

    private static final int MAX_ATTEMPTS = 10;
    private static final int MAX_NUMBER = 100;
    private static final int MIN_NUMBER = 1;

    private final Random random = new Random();

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        initGame(session);
        addAttributesToModel(session, model);
        return "index";
    }

    @PostMapping("/guess")
    public String guess(@RequestParam(value = "guess", required = false) Integer guess, HttpSession session, Model model) {
        initGame(session);

        Integer randomNumber = (Integer) session.getAttribute("randomNumber");
        Integer attemptsRemaining = (Integer) session.getAttribute("attemptsRemaining");
        Boolean gameOver = (Boolean) session.getAttribute("gameOver");

        if (Boolean.TRUE.equals(gameOver)) {
            model.addAttribute("feedback", "This round is over. Click Play Again to start a new round.");
            model.addAttribute("feedbackType", "gameover");
            model.addAttribute("revealedNumber", randomNumber);
            addAttributesToModel(session, model);
            return "index";
        }

        if (guess == null || guess < MIN_NUMBER || guess > MAX_NUMBER) {
            model.addAttribute("feedback", "Please enter a number between " + MIN_NUMBER + " and " + MAX_NUMBER + ".");
            model.addAttribute("feedbackType", "highlow");
            addAttributesToModel(session, model);
            return "index";
        }

        String feedback;
        String feedbackType;

        if (guess == randomNumber) {
            int attemptsUsed = MAX_ATTEMPTS - attemptsRemaining + 1;
            int currentScore = (Integer) session.getAttribute("score");
            int roundsWon = (Integer) session.getAttribute("roundsWon");

            currentScore += (MAX_ATTEMPTS - attemptsUsed) * 10;
            roundsWon++;

            session.setAttribute("score", currentScore);
            session.setAttribute("roundsWon", roundsWon);
            session.setAttribute("gameOver", true);

            feedback = "Correct! You guessed the number.";
            feedbackType = "correct";

            // Reveal the number
            model.addAttribute("revealedNumber", randomNumber);
        } else if (guess > randomNumber) {
            attemptsRemaining--;
            session.setAttribute("attemptsRemaining", attemptsRemaining);
            feedback = "Too high!";
            feedbackType = "highlow";

            if (attemptsRemaining == 0) {
                session.setAttribute("gameOver", true);
                feedback = "Game Over. The number was " + randomNumber;
                feedbackType = "gameover";
                model.addAttribute("revealedNumber", randomNumber);
            }
        } else {
            attemptsRemaining--;
            session.setAttribute("attemptsRemaining", attemptsRemaining);
            feedback = "Too low!";
            feedbackType = "highlow";

            if (attemptsRemaining == 0) {
                session.setAttribute("gameOver", true);
                feedback = "Game Over. The number was " + randomNumber;
                feedbackType = "gameover";
                model.addAttribute("revealedNumber", randomNumber);
            }
        }

        model.addAttribute("feedback", feedback);
        model.addAttribute("feedbackType", feedbackType);
        addAttributesToModel(session, model);
        return "index";
    }

    @PostMapping("/play-again")
    public String playAgain(HttpSession session, Model model) {
        boolean existingGame = session.getAttribute("randomNumber") != null;
        initGame(session);

        if (existingGame) {
            startNewRound(session);
            int roundNumber = (Integer) session.getAttribute("roundNumber");
            session.setAttribute("roundNumber", roundNumber + 1);
        }

        model.addAttribute("feedback", "");
        model.addAttribute("feedbackType", "");
        addAttributesToModel(session, model);
        return "index";
    }

    @PostMapping("/restart")
    public String restart(HttpServletRequest request, Model model) {
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        HttpSession newSession = request.getSession(true);
        initGame(newSession);
        model.addAttribute("feedback", "");
        model.addAttribute("feedbackType", "");
        addAttributesToModel(newSession, model);
        return "index";
    }

    private void initGame(HttpSession session) {
        if (session.getAttribute("randomNumber") == null) {
            startNewRound(session);
            session.setAttribute("score", 0);
            session.setAttribute("roundsWon", 0);
            session.setAttribute("roundNumber", 1);
        }
    }

    private void startNewRound(HttpSession session) {
        int newNumber = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
        session.setAttribute("randomNumber", newNumber);
        session.setAttribute("attemptsRemaining", MAX_ATTEMPTS);
        session.setAttribute("gameOver", false);
    }

    private void addAttributesToModel(HttpSession session, Model model) {
        model.addAttribute("attemptsRemaining", session.getAttribute("attemptsRemaining"));
        model.addAttribute("score", session.getAttribute("score"));
        model.addAttribute("roundsWon", session.getAttribute("roundsWon"));
        model.addAttribute("roundNumber", session.getAttribute("roundNumber"));
        model.addAttribute("gameOver", session.getAttribute("gameOver"));
    }
}
