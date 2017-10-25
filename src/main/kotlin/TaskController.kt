package todolist

import com.fasterxml.jackson.databind.ObjectMapper
import spark.Route
import spark.Spark.halt

class TaskController(private val objectMapper: ObjectMapper,
                     private val taskRepository: TaskRepository) {

    fun index(): Route = Route { req, res ->
        taskRepository.findAll()
    }

    fun create(): Route = Route { req, res ->
        val request: TaskCreateRequest =
                objectMapper.readValue(req.bodyAsBytes()) ?: throw halt(400) // 型：TaskCreateRequestを推論する
        val task = taskRepository.create(request.content)
        res.status(201)
        task
    }

}