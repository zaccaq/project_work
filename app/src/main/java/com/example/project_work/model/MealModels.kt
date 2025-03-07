package com.example.project_work.model  // Assicurati che il package sia corretto
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealResponse(
    val meals: List<Meal>? // Lista di pasti (può essere null se l'API non trova risultati)
)

@Serializable
@Entity(tableName = "meal_table")
data class Meal(
    @PrimaryKey val idMeal: String, // ✅ Chiave primaria aggiunta
    val strMeal: String,
    val strMealThumb: String,
    val strArea: String?,
    val strInstructions: String?,
    var isFavorite: Boolean = false,
    @SerialName("strIngredient1") val ingredient1: String? = null,
    @SerialName("strIngredient2") val ingredient2: String? = null,
    @SerialName("strIngredient3") val ingredient3: String? = null,
    @SerialName("strIngredient4") val ingredient4: String? = null,
    @SerialName("strIngredient5") val ingredient5: String? = null,
    @SerialName("strIngredient6") val ingredient6: String? = null,
    @SerialName("strIngredient7") val ingredient7: String? = null,
    @SerialName("strIngredient8") val ingredient8: String? = null,
    @SerialName("strIngredient9") val ingredient9: String? = null,
    @SerialName("strIngredient10") val ingredient10: String? = null,
    @SerialName("strIngredient11") val ingredient11: String? = null,
    @SerialName("strIngredient12") val ingredient12: String? = null,
    @SerialName("strIngredient13") val ingredient13: String? = null,
    @SerialName("strIngredient14") val ingredient14: String? = null,
    @SerialName("strIngredient15") val ingredient15: String? = null,
    @SerialName("strIngredient16") val ingredient16: String? = null,
    @SerialName("strIngredient17") val ingredient17: String? = null,
    @SerialName("strIngredient18") val ingredient18: String? = null,
    @SerialName("strIngredient19") val ingredient19: String? = null,
    @SerialName("strIngredient20") val ingredient20: String? = null,
    @SerialName("strMeasure1") val measure1: String? = null,
    @SerialName("strMeasure2") val measure2: String? = null,
    @SerialName("strMeasure3") val measure3: String? = null,
    @SerialName("strMeasure4") val measure4: String? = null,
    @SerialName("strMeasure5") val measure5: String? = null,
    @SerialName("strYoutube") val strYoutube: String? = null
) {
    fun getIngredientsList(): List<String> {
        return listOfNotNull(
            ingredient1?.takeIf { it.isNotBlank() },
            ingredient2?.takeIf { it.isNotBlank() },
            ingredient3?.takeIf { it.isNotBlank() },
            ingredient4?.takeIf { it.isNotBlank() },
            ingredient5?.takeIf { it.isNotBlank() },
            ingredient6?.takeIf { it.isNotBlank() },
            ingredient7?.takeIf { it.isNotBlank() },
            ingredient8?.takeIf { it.isNotBlank() },
            ingredient9?.takeIf { it.isNotBlank() },
            ingredient10?.takeIf { it.isNotBlank() },
            ingredient11?.takeIf { it.isNotBlank() },
            ingredient12?.takeIf { it.isNotBlank() },
            ingredient13?.takeIf { it.isNotBlank() },
            ingredient14?.takeIf { it.isNotBlank() },
            ingredient15?.takeIf { it.isNotBlank() },
            ingredient16?.takeIf { it.isNotBlank() },
            ingredient17?.takeIf { it.isNotBlank() },
            ingredient18?.takeIf { it.isNotBlank() },
            ingredient19?.takeIf { it.isNotBlank() },
            ingredient20?.takeIf { it.isNotBlank() }
        )
    }
    fun getMeasuresList(): List<String> {
        return listOfNotNull(
            measure1?.takeIf { it.isNotBlank() },
            measure2?.takeIf { it.isNotBlank() },
            measure3?.takeIf { it.isNotBlank() },
            measure4?.takeIf { it.isNotBlank() },
            measure5?.takeIf { it.isNotBlank() }
        )
    }
}
@Serializable
data class MealPreview(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)

@Serializable
data class MealPreviewResponse(
    val meals: List<MealPreview>?
)
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val email: String,
    val password: String, // In un'app reale, dovresti memorizzare le password hashate
    val nome: String = "",
    val cognome: String = ""
)
