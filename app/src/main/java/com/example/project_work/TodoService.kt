import com.example.project_work.Todo
import retrofit2.http.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface TodoApiService {
    @GET("todo")
    suspend fun getTodos(): List<Todo>

    @POST("todo")
    suspend fun addTodo(@Body todo: Todo)

    @PUT("todo/{id}")
    suspend fun updateTodo(@Path("id") id: String, @Body todo: Todo)

    @DELETE("todo/{id}")
    suspend fun deleteTodo(@Path("id") id: String)
}
// Retrofit instance
object RetrofitInstance {
    // Sostituiscilo con il tuo API KEY
    private const val BASE_URL = "https://crudcrud.com/api/6e34981496784caeb51cb11e405a9182/"

    val api: TodoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApiService::class.java)
    }
}
