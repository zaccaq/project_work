package com.example.project_work
import com.example.project_work.model.Meal
import com.example.project_work.model.MealResponse
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project_work.ui.theme.Project_WorkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.airbnb.lottie.LottieCompositionFactory
import com.example.project_work.model.MealPreview
import com.example.project_work.model.MealPreviewResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.net.URL

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Project_WorkTheme {
                val navController = rememberNavController()
                val coroutineScope = rememberCoroutineScope()
                var showBottomBar by remember { mutableStateOf(false) }

                // Osserva la destinazione attuale per nascondere la Navbar nella schermata di start
                LaunchedEffect(navController) {
                    navController.addOnDestinationChangedListener { _, destination, _ ->
                        showBottomBar = destination.route != "startScreen"
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar {
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            Icons.Filled.Home,
                                            contentDescription = "Menu principale"
                                        )
                                    },
                                    label = { Text("Menu") },
                                    selected = navController.currentDestination?.route == "screen1",
                                    onClick = { navController.navigate("screen1") }
                                )

                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            Icons.Filled.Search,
                                            contentDescription = "Cerca"
                                        )
                                    },
                                    label = { Text("Cerca") },
                                    selected = navController.currentDestination?.route == "screen2",
                                    onClick = { navController.navigate("screen2") }
                                )

                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            Icons.Filled.Menu,
                                            contentDescription = "Ricette"
                                        )
                                    },
                                    label = { Text("Ricette") },
                                    selected = navController.currentDestination?.route == "screen3",
                                    onClick = { navController.navigate("screen3") }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = "startScreen",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(
                                "startScreen",
                                enterTransition = { fadeIn(animationSpec = tween(700)) },
                                exitTransition = { fadeOut(animationSpec = tween(700)) }
                            ) {
                                StartScreen(navController)
                            }
                            composable(
                                "screen1",
                                enterTransition = { fadeIn(animationSpec = tween(700)) + slideInHorizontally(animationSpec = tween(700), initialOffsetX = { it / 2 }) },
                                exitTransition = { fadeOut(animationSpec = tween(700)) + slideOutHorizontally(animationSpec = tween(700), targetOffsetX = { -it / 2 }) }
                            ) {
                                Screen1(navController)
                            }
                            composable(
                                "screen2",
                                enterTransition = { fadeIn(animationSpec = tween(700)) + slideInHorizontally(animationSpec = tween(700), initialOffsetX = { it / 2 }) },
                                exitTransition = { fadeOut(animationSpec = tween(700)) + slideOutHorizontally(animationSpec = tween(700), targetOffsetX = { -it / 2 }) }
                            ) {
                                Screen2(navController)
                            }
                            composable(
                                "screen3",
                                enterTransition = { fadeIn(animationSpec = tween(700)) + slideInHorizontally(animationSpec = tween(700), initialOffsetX = { it / 2 }) },
                                exitTransition = { fadeOut(animationSpec = tween(700)) + slideOutHorizontally(animationSpec = tween(700), targetOffsetX = { -it / 2 }) }
                            ) {
                                Screen3(navController)
                            }
                            composable(
                                "MealDetailScreen/{mealId}",
                                enterTransition = { fadeIn(animationSpec = tween(700)) + slideInVertically(animationSpec = tween(700), initialOffsetY = { it / 2 }) },
                                exitTransition = { fadeOut(animationSpec = tween(700)) + slideOutVertically(animationSpec = tween(700), targetOffsetY = { -it / 2 }) }
                            ) { backStackEntry ->
                                val mealId = backStackEntry.arguments?.getString("mealId")
                                MealDetailScreen(navController, mealId)
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun StartScreen(navController: NavController) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFF7043),
            Color(0xFFE64A19),
            Color(0xFFBF360C)
        ),
        startY = 0f,
        endY = Float.POSITIVE_INFINITY
    )

    val buttonScale = remember { Animatable(1f) }
    LaunchedEffect(Unit) {
        while (true) {
            buttonScale.animateTo(1.05f, animationSpec = tween(700, easing = FastOutSlowInEasing))
            buttonScale.animateTo(1f, animationSpec = tween(700, easing = FastOutSlowInEasing))
            delay(1000)
        }
    }

    val rotationAngle = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        while (true) {
            rotationAngle.animateTo(10f, animationSpec = tween(1000, easing = FastOutSlowInEasing))
            rotationAngle.animateTo(-10f, animationSpec = tween(1000, easing = FastOutSlowInEasing))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var titleVisible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                delay(300)
                titleVisible = true
            }

            AnimatedVisibility(
                visible = titleVisible,
                enter = fadeIn(animationSpec = tween(1000)) + expandVertically(animationSpec = tween(1000, easing = EaseOutBack))
            ) {
                Text(
                    text = "GustoMagico 🍽️",
                    style = MaterialTheme.typography.displayMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.6f),
                            offset = Offset(4f, 4f),
                            blurRadius = 10f
                        )
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            var visibleCharacters by remember { mutableStateOf(0) }
            val fullText = "Scopri le migliori ricette da tutto il mondo!"

            LaunchedEffect(titleVisible) {
                if (titleVisible) {
                    delay(500)
                    for (i in 1..fullText.length) {
                        visibleCharacters = i
                        delay(30)
                    }
                }
            }

            Text(
                text = fullText.take(visibleCharacters),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 18.sp
                ),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Food Icon",
                tint = Color.White,
                modifier = Modifier
                    .size(120.dp)
                    .graphicsLayer {
                        rotationZ = rotationAngle.value
                    }
                    .padding(bottom = 32.dp)
            )

            ElevatedButton(
                onClick = {
                    navController.navigate("screen1") {
                        popUpTo("startScreen") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .graphicsLayer {
                        scaleX = buttonScale.value
                        scaleY = buttonScale.value
                    }
                    .shadow(10.dp, RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFFE64A19)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Inizia",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "INIZIA L'ESPERIENZA",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Fatti ispirare dalla magia del gusto",
                style = TextStyle(
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "v1.0  •  © 2025 GustoMagico",
                style = TextStyle(
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 12.sp
                )
            )
        }
    }
}

@Composable
fun Screen1(navController: NavHostController) {
    val meals = remember { mutableStateOf<List<Meal>>(emptyList()) }

    LaunchedEffect(Unit) {
        meals.value = fetchMeals()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF6A1B9A), Color(0xFFFF7043))))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🍽️ I migliori piatti",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(meals.value) { meal ->
                    MealCard(meal, navController)
                }
            }
        }
    }
}

@Composable
fun MealCard(meal: Meal, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .shadow(10.dp, RoundedCornerShape(16.dp))
            .clickable { navController.navigate("MealDetailScreen/${meal.idMeal}") }
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = meal.strMeal,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6A1B9A),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            AsyncImage(
                model = meal.strMealThumb,
                contentDescription = meal.strMeal,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(3.dp, Color(0xFFFF7043), RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.navigate("MealDetailScreen/${meal.idMeal}") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
            ) {
                Text("Scopri di più", color = Color.White)
            }
        }
    }
}

@Composable
fun MealItem(meal: Meal) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = meal.strMeal, style = MaterialTheme.typography.headlineMedium)
            AsyncImage(
                model = meal.strMealThumb,
                contentDescription = meal.strMeal,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
suspend fun fetchMeals(): List<Meal> {
    return withContext(Dispatchers.IO) {
        try {
            val response = URL("https://www.themealdb.com/api/json/v1/1/search.php?s=").readText()
            Log.d("API_RESPONSE", "Risposta: $response") // 🔍 Log della risposta

            val mealResponse = kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            }.decodeFromString<MealResponse>(response)


            mealResponse.meals ?: emptyList()
        } catch (e: Exception) {
            Log.e("API_ERROR", "Errore durante il recupero dei dati: ${e.message}")
            emptyList()
        }
    }
}

@Composable
fun Screen2(navController: NavHostController) {
    val searchQuery = remember { mutableStateOf("") }
    val mealResult = remember { mutableStateOf<Meal?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFFCE4EC), Color(0xFFFFF3E0))))
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "🔍 Cerca un piatto",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF880E4F),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                label = { Text("Nome del piatto") },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Icona Cerca") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF7043),
                    unfocusedBorderColor = Color.Gray
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        val meal = fetchMealByName(searchQuery.value)
                        mealResult.value = meal
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7043)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Icona Cerca")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerca", color = Color.White)
            }

            mealResult.value?.let { meal ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { navController.navigate("MealDetailScreen/${meal.idMeal}") },
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            meal.strMeal,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF880E4F)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        AsyncImage(
                            model = meal.strMealThumb,
                            contentDescription = meal.strMeal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .border(2.dp, Color(0xFFFF7043), RoundedCornerShape(12.dp))
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { navController.navigate("MealDetailScreen/${meal.idMeal}") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF880E4F))
                        ) {
                            Text("Vedi dettagli", color = Color.White)
                        }
                    }
                }
            } ?: Text("Nessun piatto trovato", color = Color.Red)
        }
    }
}


suspend fun fetchMealByName(name: String): Meal? {
    return withContext(Dispatchers.IO) {
        try {
            val response = URL("https://www.themealdb.com/api/json/v1/1/search.php?s=$name").readText()
            val mealResponse = kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            }.decodeFromString<MealResponse>(response)
            mealResponse.meals?.firstOrNull()
        } catch (e: Exception) {
            Log.e("API_ERROR", "Errore durante la ricerca del piatto: ${e.message}")
            null
        }
    }
}
@Composable
fun MealDetailScreen(navController: NavHostController, mealId: String?) {
    val meal = remember { mutableStateOf<Meal?>(null) }
    val context = LocalContext.current

    LaunchedEffect(mealId) {
        mealId?.let {
            meal.value = fetchMealDetails(it)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF6A1B9A), Color(0xFFFF7043))))
            .padding(16.dp)
    ) {
        meal.value?.let { mealData ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = mealData.strMeal,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .shadow(10.dp, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = mealData.strMealThumb,
                            contentDescription = mealData.strMeal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .border(4.dp, Color(0xFFFF7043), RoundedCornerShape(16.dp))
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "🍽️ Ingredienti",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6A1B9A)
                        )

                        Column(
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            mealData.getIngredientsList().forEach { ingredient ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFFFF7043))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(ingredient, fontSize = 16.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "📜 Preparazione",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6A1B9A)
                        )

                        Text(
                            text = mealData.strInstructions ?: "Nessuna istruzione disponibile",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        mealData.strYoutube?.let { youtubeLink ->
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
                                    context.startActivity(intent)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7043))
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Guarda su YouTube", color = Color.White)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { navController.navigate("screen2") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
                ) {
                    Text("🔙 Torna Indietro", color = Color.White)
                }
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    }
}





suspend fun fetchMealDetails(mealId: String?): Meal? {
    return withContext(Dispatchers.IO) {
        try {
            val response = URL("https://www.themealdb.com/api/json/v1/1/lookup.php?i=$mealId").readText()
            val mealResponse = Json { ignoreUnknownKeys = true }.decodeFromString<MealResponse>(response)
            mealResponse.meals?.firstOrNull()
        } catch (e: Exception) {
            Log.e("API_ERROR", "Errore nel caricamento dettagli: ${e.message}")
            null
        }
    }
}


@Composable
fun Screen3(navController: NavHostController) {
    val countryFlags = mapOf(
        "American" to "🇺🇸", "British" to "🇬🇧", "Canadian" to "🇨🇦", "Chinese" to "🇨🇳",
        "Croatian" to "🇭🇷", "Dutch" to "🇳🇱", "Egyptian" to "🇪🇬", "Filipino" to "🇵🇭",
        "French" to "🇫🇷", "Greek" to "🇬🇷", "Indian" to "🇮🇳", "Irish" to "🇮🇪",
        "Italian" to "🇮🇹", "Jamaican" to "🇯🇲", "Japanese" to "🇯🇵", "Kenyan" to "🇰🇪",
        "Malaysian" to "🇲🇾", "Mexican" to "🇲🇽", "Moroccan" to "🇲🇦", "Polish" to "🇵🇱",
        "Portuguese" to "🇵🇹", "Russian" to "🇷🇺", "Spanish" to "🇪🇸", "Thai" to "🇹🇭",
        "Tunisian" to "🇹🇳", "Turkish" to "🇹🇷", "Vietnamese" to "🇻🇳"
    )
    val countryList = countryFlags.keys.toList()
    val selectedCountry = remember { mutableStateOf(countryList.first()) }
    val expanded = remember { mutableStateOf(false) }
    val mealsList = remember { mutableStateOf<List<MealPreview>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFFCE4EC), Color(0xFFFFF3E0))))
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "🌎 Seleziona una nazione",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF880E4F),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Box {
                Button(
                    onClick = { expanded.value = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF880E4F)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.Place, contentDescription = "Icona Nazione")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("${countryFlags[selectedCountry.value]} ${selectedCountry.value}", color = Color.White)
                }

                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    countryList.forEach { country ->
                        DropdownMenuItem(
                            text = { Text("${countryFlags[country]} $country", fontWeight = FontWeight.SemiBold) },
                            onClick = {
                                selectedCountry.value = country
                                expanded.value = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        val meals = fetchMealsByCountry(selectedCountry.value)
                        mealsList.value = meals
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7043)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Icona Cerca")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerca", color = Color.White)
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(mealsList.value) { meal ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { navController.navigate("MealDetailScreen/${meal.idMeal}") },
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = meal.strMealThumb,
                                contentDescription = meal.strMeal,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color(0xFFFF7043), CircleShape)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = meal.strMeal,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF880E4F)
                                )
                                Text(
                                    text = "Clicca per dettagli",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}




suspend fun fetchMealsByCountry(country: String): List<MealPreview> {
    return withContext(Dispatchers.IO) {
        try {
            val response = URL("https://www.themealdb.com/api/json/v1/1/filter.php?a=$country").readText()
            val mealResponse = Json { ignoreUnknownKeys = true }.decodeFromString<MealPreviewResponse>(response)
            mealResponse.meals ?: emptyList()
        } catch (e: Exception) {
            Log.e("API_ERROR", "Errore nella ricerca per nazione: ${e.message}")
            emptyList()
        }
    }
}

@Composable
fun MealItem(meal: MealPreview, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = meal.strMealThumb,
                contentDescription = meal.strMeal,
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
            )

            Text(
                text = meal.strMeal,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Project_WorkTheme {
        val navController = rememberNavController()
        Screen1(navController)
        }
    }
}