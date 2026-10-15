package mx.tec.sabores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import mx.tec.sabores.data.RestaurantRepository
import mx.tec.sabores.domain.RatingSummary
import mx.tec.sabores.ui.screens.RestaurantListScreen
import mx.tec.sabores.ui.theme.SaboresTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SaboresTheme {
                val repository = remember { RestaurantRepository() }
                RestaurantListScreen(
                    restaurants = repository.getAll(),
                    summaryOf = { RatingSummary(0.0, 0) },
                    onRestaurantClick = { }
                )
            }
        }
    }
}
