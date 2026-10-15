package mx.tec.sabores.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mx.tec.sabores.ui.screens.NewReviewScreen
import mx.tec.sabores.ui.screens.RestaurantDetailScreen
import mx.tec.sabores.ui.screens.RestaurantListScreen
import mx.tec.sabores.ui.state.NewReviewViewModel
import mx.tec.sabores.ui.state.SaboresViewModel

@Composable
fun SaboresApp() {
    val nav = rememberNavController()
    val viewModel: SaboresViewModel = viewModel()   // androidx.lifecycle.viewmodel.compose.viewModel

    NavHost(navController = nav, startDestination = Route.HOME) {

        composable(Route.HOME) {
            RestaurantListScreen(
                restaurants = viewModel.restaurants,
                summaryOf = { id -> viewModel.summaryOf(id) },
                onRestaurantClick = { id -> nav.navigate(Route.detail(id)) }
            )
        }

        composable(
            route = Route.DETAIL,
            arguments = listOf(navArgument(Route.ARG_RESTAURANT_ID) { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt(Route.ARG_RESTAURANT_ID) ?: return@composable
            val restaurant = viewModel.restaurantById(id) ?: return@composable

            RestaurantDetailScreen(
                restaurant = restaurant,
                summary = viewModel.summaryOf(id),
                reviews = viewModel.reviewsOf(id),
                onWriteReviewClick = { nav.navigate(Route.newReview(id)) },
                onBack = { nav.popBackStack() }
            )
        }

        composable(
            route = Route.NEW_REVIEW,
            arguments = listOf(navArgument(Route.ARG_RESTAURANT_ID) { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt(Route.ARG_RESTAURANT_ID) ?: return@composable
            val restaurant = viewModel.restaurantById(id) ?: return@composable

            // OJO: este viewModel() vive DENTRO del composable → su dueño es este destino.
            val formViewModel: NewReviewViewModel = viewModel()

            NewReviewScreen(
                restaurant = restaurant,
                uiState = formViewModel.uiState,
                onStarsChange = formViewModel::onStarsChange,
                onCommentChange = formViewModel::onCommentChange,
                onSave = {
                    viewModel.addReview(                       // ← el VM COMPARTIDO recibe el dato
                        restaurantId = id,
                        stars = formViewModel.uiState.stars,
                        comment = formViewModel.uiState.comment
                    )
                    nav.popBackStack()                         // ← la NAVEGACIÓN la decide la UI
                },
                onCancel = { nav.popBackStack() }
            )
        }
    }
}
