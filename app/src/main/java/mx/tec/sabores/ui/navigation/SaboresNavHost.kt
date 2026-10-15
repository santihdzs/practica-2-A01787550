package mx.tec.sabores.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mx.tec.sabores.data.RestaurantRepository
import mx.tec.sabores.domain.RatingSummary
import mx.tec.sabores.ui.screens.RestaurantDetailScreen
import mx.tec.sabores.ui.screens.RestaurantListScreen

@Composable
fun SaboresApp() {
    val nav = rememberNavController()
    val repository = remember { RestaurantRepository() }

    NavHost(navController = nav, startDestination = Route.HOME) {

        composable(Route.HOME) {
            RestaurantListScreen(
                restaurants = repository.getAll(),
                summaryOf = { RatingSummary(0.0, 0) },        // provisional
                onRestaurantClick = { id -> nav.navigate(Route.detail(id)) }
            )
        }

        composable(
            route = Route.DETAIL,
            arguments = listOf(navArgument(Route.ARG_RESTAURANT_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(Route.ARG_RESTAURANT_ID) ?: return@composable
            val restaurant = repository.getById(id) ?: return@composable

            RestaurantDetailScreen(
                restaurant = restaurant,
                summary = RatingSummary(0.0, 0),               // provisional
                onWriteReviewClick = { nav.navigate(Route.newReview(id)) },
                onBack = { nav.popBackStack() }
            )
        }
    }
}
