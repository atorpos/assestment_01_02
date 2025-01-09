package org.fxexchange

import java.time.LocalDateTime

fun main() {


}


fun analyzeTransactions(transactions: List<Transaction>): List<UserStats> {
    val transactionByUser = transactions.groupBy { it.userId }

    val userStats = transactionByUser.mapNotNull { (userId, transactions) ->
        val completedTransactions = transactions.filter { it.status == TransactionStatus.COMPLETED }
        val totalAmount = completedTransactions.sumOf { it.amount }
        val avgAmount = totalAmount / completedTransactions.size
        val topCategory = completedTransactions.groupingBy { it.category }
            .eachCount()
            .maxByOrNull { it.value } // Get the category with the highest count
            ?.key ?: "N/A"
        val recentTransactionsCount = completedTransactions.count { it.date.isAfter(LocalDateTime.now().minusDays(30)) }

        UserStats(userId = userId, totalAmount = totalAmount, avgAmount = avgAmount, topCategory = topCategory, recentTransactionsCount = recentTransactionsCount)
    }

    return userStats.sortedByDescending { it.totalAmount }

}

data class Transaction(
    val id: String,
    val userId: Int,
    val category: String,
    val amount: Double,
    val date: LocalDateTime,
    val status: TransactionStatus
)

enum class TransactionStatus { COMPLETED, PENDING, FAILED }

data class UserStats(
    val userId: Int,
    val totalAmount: Double,
    val avgAmount: Double,
    val topCategory: String,
    val recentTransactionsCount: Int
)
