package com.example.quizgame

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.quizgame.databinding.ActivityMainBinding
import com.example.quizgame.databinding.ActivityStatsBinding

class MainActivity : AppCompatActivity() {

    // Initialize the view binding variable
    private lateinit var binding: ActivityMainBinding

    // Arrays to store questions, answer options, correct answers, and money prizes amount
    private val questions = arrayOf(
        "Which planet is known as the Red Planet?",
        "In computing, what does CPU stand for?",
        "Which famous ship sank on its first voyage in 1912?",
        "Which planet is closest to the sun?",
        "Which language is spoken in Brazil?",
        "Who was the first president of the United States?",
        "Which country is home to the ancient city of Machu Picchu?"
    )
    private val options = arrayOf(
        arrayOf("Earth", "Mars", "Venus", "PlanetX"),
        arrayOf("Central Process Unit", "Computer Personal Unit", "Central Processor Unit", "Central Processing Unit"),
        arrayOf("Titanic", "Lusitania", "Endeavour", "Santa Maria"),
        arrayOf("Venus", "Mars", "Earth", "Mercury"),
        arrayOf("Spanish", "Portuguese", "French", "English"),
        arrayOf("Thomas Jefferson", "Benjamin Franklin", "Abraham Lincoln", "George Washington"),
        arrayOf("Brazil", "Chile", "Peru", "Bolivia")
    )
    private val correctAnswers = arrayOf(1, 3, 0, 3, 1, 3, 2)
    private val moneyPrize = intArrayOf(100, 150, 250, 500, 1000, 2000, 5000)

    // Variables to keep track of the current question, score, and money earned
    private var currentQuestionIndex = 0
    private var score = 0
    private var money = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the view binding and set the content view to the root layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Display the initial question
        displayQuestion()

        // Set click listeners for answer buttons
        binding.optionButton.setOnClickListener {
            checkAnswer(0)
        }
        binding.option2Button.setOnClickListener {
            checkAnswer(1)
        }
        binding.option3Button.setOnClickListener {
            checkAnswer(2)
        }
        binding.option4Button.setOnClickListener {
            checkAnswer(3)
        }

        // Set a click listener for the "Next/Confirm" button
        binding.confirm.setOnClickListener {
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                displayQuestion()
                resetButtonColor()
                binding.optionsGroup.clearCheck()
            } else {
                showStats()
            }
        }
    }

    // Functions to handle button colors
    private fun correctButtonColor(buttonIndex: Int) {
        // Change the background color of the correct answer button to green
        when (buttonIndex) {
            0 -> binding.optionButton.setBackgroundColor(Color.GREEN)
            1 -> binding.option2Button.setBackgroundColor(Color.GREEN)
            2 -> binding.option3Button.setBackgroundColor(Color.GREEN)
            3 -> binding.option4Button.setBackgroundColor(Color.GREEN)
        }
    }

    private fun wrongButtonColor(buttonIndex: Int) {
        // Change the background color of the selected wrong answer button to red
        when (buttonIndex) {
            0 -> binding.optionButton.setBackgroundColor(Color.RED)
            1 -> binding.option2Button.setBackgroundColor(Color.RED)
            2 -> binding.option3Button.setBackgroundColor(Color.RED)
            3 -> binding.option4Button.setBackgroundColor(Color.RED)
        }
    }

    private fun resetButtonColor() {
        // Reset the background color of all answer buttons to transparent after each question
        binding.optionButton.setBackgroundColor(Color.TRANSPARENT)
        binding.option2Button.setBackgroundColor(Color.TRANSPARENT)
        binding.option3Button.setBackgroundColor(Color.TRANSPARENT)
        binding.option4Button.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun answerToast(amountWon: Int) {
        // Display a toast message indicating a correct answer and the amount won
        val message = "This is the CORRECT answer You won $$amountWon"
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun displayQuestion() {
        // Display the current question and answer options
        binding.questionText.text = questions[currentQuestionIndex]
        binding.optionButton.text = options[currentQuestionIndex][0]
        binding.option2Button.text = options[currentQuestionIndex][1]
        binding.option3Button.text = options[currentQuestionIndex][2]
        binding.option4Button.text = options[currentQuestionIndex][3]
    }

    private fun checkAnswer(selectedAnswerIndex: Int) {
        val correctAnswerIndex = correctAnswers[currentQuestionIndex]

        if (selectedAnswerIndex == correctAnswerIndex) {
            // If the selected answer is correct, update the score and money earned
            score++
            val amountWon = moneyPrize[currentQuestionIndex]
            money += moneyPrize[currentQuestionIndex]
            correctButtonColor(selectedAnswerIndex)
            answerToast(amountWon)
        } else {
            // If the selected answer is wrong, color the selected answer red and the correct answer green
            wrongButtonColor(selectedAnswerIndex)
            correctButtonColor(correctAnswerIndex)
        }
        updateMoneyText()
    }

    private fun updateMoneyText() {
        // Update the money earned text view
        binding.moneyText.text = "You Earned: $$money"
    }

    private fun showStats() {
        // Display the final score and total money earned in a separate activity using data binding
        val finalScore = score
        val totalMoneyEarned = money
        val statsBinding = ActivityStatsBinding.inflate(layoutInflater)

        // Set the content view to display the stats UI using data binding
        setContentView(statsBinding.root)

        // Update the TextViews in the stats UI layout using data binding
        statsBinding.finalScoreText.text = "You got $score questions correct"
        statsBinding.totalMoneyText.text = "You won $$money"
    }
}