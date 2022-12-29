/*
 * Copyright (c) 2022 James Carman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package adventofcode.day21

import adventofcode.util.isNumeric

class MonkeyMath(private val expressions: Map<String, String>) {

    companion object {
        private val mathRegex = Regex("[+\\-*/]")

        private fun String.isMathematical() = contains(mathRegex)
    }

    private fun lookup(expression: String): String =
        expressions[expression] ?: throw IllegalArgumentException("Unknown expression $expression!")

    private fun solveMultiplication(left: String, right: String, product: Long): Long {
        return if (canEvaluate(left)) {
            val leftValue = evaluate(left)
            solve(right, product / leftValue)
        } else {
            val rightValue = evaluate(right)
            solve(left, product / rightValue)
        }
    }

    private fun solveAddition(left: String, right: String, sum: Long): Long {
        return if (canEvaluate(left)) {
            val leftValue = evaluate(left)
            solve(right, sum - leftValue)
        } else {
            val rightValue = evaluate(right)
            solve(left, sum - rightValue)
        }
    }

    private fun solveSubtraction(left: String, right: String, difference: Long): Long {
        return if (canEvaluate(left)) {
            val leftValue = evaluate(left)
            solve(right, leftValue - difference)
        } else {
            val rightValue = evaluate(right)
            solve(left, difference + rightValue)
        }
    }

    private fun solveDivision(numerator: String, denominator: String, quotient: Long): Long {
        return if (canEvaluate(numerator)) {
            val numeratorValue = evaluate(numerator)
            solve(denominator, numeratorValue / quotient)
        } else {
            val denominatorValue = evaluate(denominator)
            solve(numerator, quotient * denominatorValue)
        }
    }

    fun solveEquation(equation: String): Long {
        val splits = equation.split(' ')
        val left = splits[0]
        val right = splits[2]
        return if (canEvaluate(left)) {
            val leftValue = evaluate(left)
            solve(right, leftValue)
        } else {
            val rightValue = evaluate(right)
            solve(left, rightValue)
        }
    }

    private fun solveMathExpression(expression: String, value: Long): Long {
        val splits = expression.split(' ')
        val left = splits[0]
        val right = splits[2]
        return when (splits[1]) {
            "+" -> solveAddition(left, right, value)
            "-" -> solveSubtraction(left, right, value)
            "*" -> solveMultiplication(left, right, value)
            "/" -> solveDivision(left, right, value)
            else -> throw IllegalArgumentException("Invalid math expression $expression!")
        }
    }

    private fun solve(expression: String, value: Long): Long {
        if (expression.isMathematical()) {
            return solveMathExpression(expression, value)
        }
        return when (val e = expressions[expression]) {
            null -> return value
            else -> solve(e, value)
        }
    }

    fun evaluate(expression: String): Long {
        if (expression.isNumeric()) {
            return expression.toLong()
        }
        return if (expression.isMathematical()) {
            evaluateMathExpression(expression)
        } else {
            evaluate(lookup(expression))
        }
    }

    private fun evaluateMathExpression(expression: String): Long {
        val splits = expression.split(' ')
        val left = evaluate(lookup(splits[0]))
        val right = evaluate(lookup(splits[2]))
        return when (splits[1]) {
            "+" -> left + right
            "-" -> left - right
            "*" -> left * right
            "/" -> left / right
            else -> throw IllegalArgumentException("Expression $expression invalid!")
        }
    }

    private fun canEvaluate(expression: String): Boolean {
        if (expression.isNumeric()) {
            return true
        }
        return if (expression.isMathematical()) {
            canEvaluateMathExpression(expression)
        } else {
            canEvaluateSimpleExpression(expression)
        }
    }

    private fun canEvaluateSimpleExpression(expression: String): Boolean {
        return when (val e = expressions[expression]) {
            null -> return false
            else -> canEvaluate(e)
        }
    }

    private fun canEvaluateMathExpression(expression: String): Boolean {
        val splits = expression.split(' ')
        return canEvaluate(splits[0]) && canEvaluate(splits[2])
    }
}