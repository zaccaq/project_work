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
import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.EaseOutElastic
import androidx.compose.animation.core.EaseOutQuint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Casino
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.project_work.model.MealPreview
import com.example.project_work.model.MealPreviewResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.net.URL
import kotlin.math.sin
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Project_WorkTheme {
                val navController = rememberNavController()
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
                                    onClick = {
                                        navController.navigate("screen1") {
                                            popUpTo(navController.graph.startDestinationId)
                                            launchSingleTop = true
                                        }
                                    }
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
                                    onClick = {
                                        navController.navigate("screen2") {
                                            popUpTo(navController.graph.startDestinationId)
                                            launchSingleTop = true
                                        }
                                    }
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
                                    onClick = {
                                        navController.navigate("screen3") {
                                            popUpTo(navController.graph.startDestinationId)
                                            launchSingleTop = true
                                        }
                                    }
                                )


                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Outlined.Casino,
                                            contentDescription = "Ricetta Casuale"
                                        )
                                    },
                                    label = { Text("Casuale") },
                                    selected = navController.currentDestination?.route == "RandomMealScreen",
                                    onClick = {
                                        navController.navigate("RandomMealScreen") {
                                            popUpTo(navController.graph.startDestinationId)
                                            launchSingleTop = true
                                        }
                                    }
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
                            composable(
                                "RandomMealScreen",
                                enterTransition = {
                                    fadeIn(animationSpec = tween(700)) + scaleIn(
                                        animationSpec = tween(700),
                                        initialScale = 0.9f
                                    ) + slideInHorizontally(
                                        animationSpec = tween(700),
                                        initialOffsetX = { it / 2 }
                                    )
                                },
                                exitTransition = {
                                    fadeOut(animationSpec = tween(700)) + scaleOut(
                                        animationSpec = tween(700),
                                        targetScale = 0.9f
                                    ) + slideOutHorizontally(
                                        animationSpec = tween(700),
                                        targetOffsetX = { -it / 2 }
                                    )
                                }
                            ) {
                                RandomMealScreen(navController)
                            }
                        }
                    }
                }
            }
        }
    }
    @Composable
    fun StartScreen(navController: NavController) {
        // Gradiente più ricco con più colori per un effetto più profondo
        val gradient = Brush.verticalGradient(
            colors = listOf(
                Color(0xFFFF9800),
                Color(0xFFFF7043),
                Color(0xFFE64A19),
                Color(0xFFD84315),
                Color(0xFFBF360C)
            ),
            startY = 0f,
            endY = Float.POSITIVE_INFINITY
        )


        val particleCount = 30
        val particles = remember {
            List(particleCount) {
                androidx.compose.runtime.mutableStateOf(
                    Particle(
                        x = Random.nextFloat() * 1000f,
                        y = Random.nextFloat() * 2000f,
                        size = Random.nextFloat() * 20f + 5f,
                        speed = Random.nextFloat() * 2f + 1f,
                        alpha = Random.nextFloat() * 0.5f + 0.1f
                    )
                )
            }
        }

        // Animazione del pulsante più elaborata con pulsazione
        val buttonScale = remember { Animatable(1f) }
        LaunchedEffect(Unit) {
            while (true) {
                buttonScale.animateTo(1.08f, animationSpec = tween(600, easing = EaseInOutQuart))
                buttonScale.animateTo(0.98f, animationSpec = tween(500, easing = EaseInOutQuart))
                buttonScale.animateTo(1.03f, animationSpec = tween(400, easing = EaseInOutQuart))
                buttonScale.animateTo(1f, animationSpec = tween(300, easing = EaseInOutQuart))
                delay(1500)
            }
        }


        val rotationAngle = remember { Animatable(0f) }
        LaunchedEffect(Unit) {
            while (true) {
                // Movimento più organico con effetto di rimbalzo
                rotationAngle.animateTo(12f, animationSpec = tween(800, easing = EaseOutBounce))
                rotationAngle.animateTo(-12f, animationSpec = tween(800, easing = EaseOutBounce))
            }
        }


        val scrollState = rememberScrollState()
        val parallaxOffset = scrollState.value * 0.15f


        LaunchedEffect(Unit) {
            while (true) {
                particles.forEach { particle ->
                    particle.value = particle.value.copy(
                        y = (particle.value.y - particle.value.speed) % 2000f,
                        x = particle.value.x + sin(particle.value.y * 0.01f) * 2f
                    )
                }
                delay(16) // Circa 60fps
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
        ) {

            particles.forEach { particle ->
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    drawCircle(
                        color = Color.White.copy(alpha = particle.value.alpha),
                        radius = particle.value.size,
                        center = Offset(particle.value.x, particle.value.y)
                    )
                }
            }


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFFF5722).copy(alpha = 0.0f),
                                Color(0xFFFF5722).copy(alpha = 0.2f)
                            ),
                            radius = 1200f
                        )
                    )
            )

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
                    enter = fadeIn(animationSpec = tween(1200)) +
                            expandVertically(animationSpec = tween(1200, easing = EaseOutElastic)) +
                            scaleIn(initialScale = 0.7f, animationSpec = tween(1500, easing = EaseOutBack))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Gusto",
                            style = MaterialTheme.typography.displayMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 2.sp,
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.7f),
                                    offset = Offset(5f, 5f),
                                    blurRadius = 15f
                                )
                            )
                        )
                        Text(
                            text = "Magico",
                            style = MaterialTheme.typography.displayMedium.copy(
                                color = Color(0xFFFFC107),
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 2.sp,
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.7f),
                                    offset = Offset(5f, 5f),
                                    blurRadius = 15f
                                )
                            )
                        )
                        Text(
                            text = " 🍽️",
                            style = MaterialTheme.typography.displayMedium.copy(
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.7f),
                                    offset = Offset(3f, 3f),
                                    blurRadius = 10f
                                )
                            ),
                            modifier = Modifier.graphicsLayer {
                                scaleX = 1.2f
                                scaleY = 1.2f
                            }
                        )
                    }
                }


                var visibleCharacters by remember { mutableStateOf(0) }
                val fullText = "Scopri le migliori ricette da tutto il mondo!"
                val showCursor by remember { derivedStateOf { visibleCharacters < fullText.length } }

                LaunchedEffect(titleVisible) {
                    if (titleVisible) {
                        delay(700)
                        for (i in 1..fullText.length) {
                            visibleCharacters = i
                            delay(35)
                        }
                    }
                }

                Box(modifier = Modifier.padding(bottom = 40.dp, top = 16.dp)) {
                    Text(
                        text = fullText.take(visibleCharacters),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.5.sp,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.5f),
                                offset = Offset(2f, 2f),
                                blurRadius = 4f
                            )
                        )
                    )


                    if (showCursor) {
                        val cursorVisible = remember { mutableStateOf(true) }
                        LaunchedEffect(Unit) {
                            while (true) {
                                cursorVisible.value = !cursorVisible.value
                                delay(530)
                            }
                        }

                        if (cursorVisible.value) {
                            Box(
                                modifier = Modifier
                                    .height(24.dp)
                                    .width(2.dp)
                                    .background(Color.White)
                                    .offset(x = with(LocalDensity.current) {
                                        (fullText.take(visibleCharacters).width(
                                            MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp)
                                        ) + 2.dp).toPx().dp
                                    })
                            )
                        }
                    }
                }


                Box(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .size(160.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFFFA726).copy(alpha = 0.7f),
                                    Color(0xFFE65100).copy(alpha = 0.0f)
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.RestaurantMenu, // Icona più pertinente al cibo
                        contentDescription = "Food Icon",
                        tint = Color.White,
                        modifier = Modifier
                            .size(100.dp)
                            .graphicsLayer {
                                rotationZ = rotationAngle.value
                                shadowElevation = 15f
                            }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .graphicsLayer {
                            scaleX = buttonScale.value
                            scaleY = buttonScale.value
                        }
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(y = 4.dp)
                            .shadow(20.dp, RoundedCornerShape(24.dp))
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFFF9800).copy(alpha = 0.7f),
                                        Color(0xFFFF5722).copy(alpha = 0.4f)
                                    )
                                ),
                                shape = RoundedCornerShape(24.dp)
                            )
                    )


                    Button(
                        onClick = {
                            navController.navigate("screen1") {
                                popUpTo("startScreen") { inclusive = true }
                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .shadow(8.dp, RoundedCornerShape(24.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFFE64A19)
                        ),
                        shape = RoundedCornerShape(24.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 6.dp,
                            pressedElevation = 2.dp
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.RestaurantMenu,
                                contentDescription = "Inizia",
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "INIZIA L'ESPERIENZA",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 1.sp,
                                    fontSize = 18.sp
                                )
                            )
                        }
                    }
                }


                var subtitleVisible by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) {
                    delay(1800)
                    subtitleVisible = true
                }

                Spacer(modifier = Modifier.height(40.dp))

                AnimatedVisibility(
                    visible = subtitleVisible,
                    enter = fadeIn(animationSpec = tween(1500)) +
                            slideInVertically(animationSpec = tween(1000, easing = EaseOutQuint))
                ) {
                    Text(
                        text = "Fatti ispirare dalla magia del gusto",
                        style = TextStyle(
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Italic,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.5f),
                                offset = Offset(1f, 1f),
                                blurRadius = 2f
                            ),
                            letterSpacing = 0.5.sp
                        )
                    )
                }
            }


            var footerVisible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                delay(2000)
                footerVisible = true
            }

            AnimatedVisibility(
                visible = footerVisible,
                enter = fadeIn(animationSpec = tween(1000)) +
                        slideInVertically(
                            initialOffsetY = { it / 2 },
                            animationSpec = tween(800, easing = EaseOutCubic)
                        ),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Divider(
                        color = Color.White.copy(alpha = 0.3f),
                        thickness = 1.dp,
                        modifier = Modifier
                            .width(100.dp)
                            .padding(bottom = 8.dp)
                    )
                    Text(
                        text = "v1.0  •  © 2025 GustoMagico",
                        style = TextStyle(
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp,
                            letterSpacing = 0.5.sp
                        )
                    )
                }
            }
        }
    }


    private data class Particle(
        val x: Float,
        val y: Float,
        val size: Float,
        val speed: Float,
        val alpha: Float
    )


    @Composable
    private fun String.width(style: TextStyle): Dp {
        val density = LocalDensity.current
        val textMeasurer = rememberTextMeasurer()
        return with(density) {
            textMeasurer
                .measure(
                    text = AnnotatedString(this@width),
                    style = style
                )
                .size
                .width
                .toDp()
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
        val mealResults = remember { mutableStateOf<List<Meal>>(emptyList()) }
        val coroutineScope = rememberCoroutineScope()
        val debounceJob = remember { mutableStateOf<Job?>(null) }

        LaunchedEffect(searchQuery.value) {
            debounceJob.value?.cancel()
            debounceJob.value = coroutineScope.launch {
                delay(500) // Debounce di 500ms
                if (searchQuery.value.isNotEmpty()) {
                    mealResults.value = fetchMealsByFirstLetter(searchQuery.value)
                } else {
                    mealResults.value = emptyList()
                }
            }
        }

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
                    label = { Text("Inserisci una lettera") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Icona Cerca") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFF7043),
                        unfocusedBorderColor = Color.Gray
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(mealResults.value) { meal ->
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
                    }
                }
            }
        }
    }

    suspend fun fetchMealsByFirstLetter(letter: String): List<Meal> {
        return withContext(Dispatchers.IO) {
            try {
                val response = URL("https://www.themealdb.com/api/json/v1/1/search.php?s=$letter").readText()
                val mealResponse = kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                }.decodeFromString<MealResponse>(response)
                mealResponse.meals ?: emptyList()
            } catch (e: Exception) {
                Log.e("API_ERROR", "Errore durante la ricerca del piatto: ${e.message}")
                emptyList()
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
            .background(Brush.verticalGradient(listOf(Color(0xFF4A148C), Color(0xFFFF7043))))
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
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .shadow(12.dp, RoundedCornerShape(24.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = mealData.strMealThumb,
                            contentDescription = mealData.strMeal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                                .border(5.dp, Color(0xFFFF7043), RoundedCornerShape(20.dp))
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "🍽️ Ingredienti",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4A148C)
                            )
                        )

                        Column(
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            mealData.getIngredientsList().forEach { ingredient ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(6.dp)
                                        .background(Color(0xFFF3E5F5), shape = RoundedCornerShape(12.dp))
                                        .padding(8.dp)
                                ) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFFFF7043))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(ingredient, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "📜 Preparazione",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4A148C)
                            )
                        )

                        Text(
                            text = mealData.strInstructions ?: "Nessuna istruzione disponibile",
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .background(Color(0xFFFFF3E0), shape = RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        )

                        mealData.strYoutube?.let { youtubeLink ->
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
                                    context.startActivity(intent)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7043)),
                                modifier = Modifier.clip(RoundedCornerShape(12.dp))
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
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A148C)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("🔙 Torna Indietro", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White, strokeWidth = 5.dp)
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
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Project_WorkTheme {
        val navController = rememberNavController()
        Screen1(navController)
        }
    }
}
@Composable
fun RandomMealScreen(navController: NavHostController) {
    var meal by remember { mutableStateOf<Meal?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Funzione per caricare una ricetta casuale
    fun loadRandomMeal() {
        coroutineScope.launch {
            isLoading = true
            meal = fetchRandomMeal()
            isLoading = false
        }
    }

    // Carica una ricetta casuale all'avvio
    LaunchedEffect(Unit) {
        loadRandomMeal()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFFCE4EC), Color(0xFFFFF3E0))))
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Titolo
            Text(
                text = "🍴 Ricetta Casuale",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF880E4F),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Card della ricetta con animazione
            AnimatedVisibility(
                visible = meal != null && !isLoading,
                enter = fadeIn() + slideInVertically { it / 2 },
                exit = fadeOut() + slideOutVertically { -it / 2 }
            ) {
                meal?.let { meal ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
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
                                color = Color(0xFF880E4F),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            AsyncImage(
                                model = meal.strMealThumb,
                                contentDescription = meal.strMeal,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(2.dp, Color(0xFFFF7043), RoundedCornerShape(12.dp))
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { navController.navigate("MealDetailScreen/${meal.idMeal}") },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF880E4F)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Vedi dettagli", color = Color.White)
                            }
                        }
                    }
                }
            }

            // Pulsante per generare una nuova ricetta
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { loadRandomMeal() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7043)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text(
                        text = "Scopri una nuova ricetta 🎲",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
suspend fun fetchRandomMeal(): Meal? {
    return withContext(Dispatchers.IO) {
        try {
            val response = URL("https://www.themealdb.com/api/json/v1/1/random.php").readText()
            val mealResponse = kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            }.decodeFromString<MealResponse>(response)
            mealResponse.meals?.firstOrNull()
        } catch (e: Exception) {
            Log.e("API_ERROR", "Errore durante il recupero della ricetta casuale: ${e.message}")
            null
        }
    }
}