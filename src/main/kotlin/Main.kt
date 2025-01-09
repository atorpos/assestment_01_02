package org.fxexchange

fun main() {

    val employees = listOf(
        Employee(1, "John", "IT", 95000.0, listOf(Project("A", 50000.0, Status.ACTIVE), Project("C", 30000.0, Status.COMPLETE))),
        Employee(2, "Mary", "HR", 85000.0, listOf(Project("B", 50000.0, Status.ACTIVE))),
        Employee(3, "Peter", "IT", 75000.0, listOf(Project("C", 20000.0, Status.ON_HOLD))),
    )

    val average_sal = mutableMapOf<String, MutableList<Double>>()
    val activeProjectBudgets = mutableMapOf<String, Double>()

    for (employee in employees) {
        val active_projects = employee.projects.filter { it.status == Status.ACTIVE }
        if(!average_sal.containsKey(employee.department)) {
            average_sal[employee.department] = mutableListOf()
        }
        average_sal[employee.department]?.add(employee.salary)

        for (project in active_projects) {
            activeProjectBudgets[employee.department] = activeProjectBudgets.getOrDefault(employee.department, 0.0) + project.budget
        }
    }

    var highavg_sal = mutableListOf<String>()
    for ((department, sals) in average_sal) {
        val average_sal = sals.average() / sals.size
        if (average_sal >= 80000) {
            highavg_sal.add(department)
        }
    }

    println("1: Departments with high average salary over 80000: $highavg_sal")


    val active_project_sal = mutableListOf<Project>()
    for (employee in employees) {
        for (project in employee.projects) {
            if (project.status == Status.ACTIVE) {
                active_project_sal.add(project)
            }
        }
    }

    val departmentWithHighBudget = activeProjectBudgets.filter { it.value > 100000 }

    println("2: Active projects: $active_project_sal")

    println("3: Total budget for active projects:")
    for ((department, budget) in activeProjectBudgets) {
        println("$department: $budget")
    }

    println("4: Department with total active project budget exceeding 100000:")
    for ((deparmnet, totalBudget) in departmentWithHighBudget) {
        println("$deparmnet: $totalBudget")
    }

}

data class Employee(
    val id: Int,
    val name: String,
    val department: String,
    val salary: Double,
    val projects: List<Project>
)

data class Project(
    val id: String,
    val budget: Double,
    val status: Status
)

enum class Status {ACTIVE, COMPLETE, ON_HOLD}