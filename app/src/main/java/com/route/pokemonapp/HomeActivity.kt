package com.route.pokemonapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.route.pokemonapp.ui.theme.BrownishOrange
import com.route.pokemonapp.ui.theme.DenimBlue
import com.route.pokemonapp.ui.theme.GreenishCyan
import com.route.pokemonapp.ui.theme.LightDenimBlue
import com.route.pokemonapp.ui.theme.LightGreenishCyan
import com.route.pokemonapp.ui.theme.LightRedCoral
import com.route.pokemonapp.ui.theme.Pistachio
import com.route.pokemonapp.ui.theme.RedCoral
import com.route.pokemonapp.ui.theme.SilkBlue
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {

    private val icons = mapOf(
        "Bulbasaur" to R.drawable.bulbasaur_icon,
        "Ivysaur" to R.drawable.ivysaur_icon,
        "Venusaur" to R.drawable.venusaur_icon,
        "Charmander" to R.drawable.charmander_icon,
        "Charmeleon" to R.drawable.charmeleon_icon,
        "Charizard" to R.drawable.charizard_icon,
        "Squirtle" to R.drawable.squirtle_icon,
        "Wartortle" to R.drawable.wartortle_icon,
        "Blastoise" to R.drawable.blastoise_icon,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val pokemonList by remember { mutableStateOf(readCSV(context)) }

            PokemonList(pokemonList = pokemonList, icons = icons)
            }
        }
    }

@Composable
fun PokemonList(pokemonList: List<Pokemon>, icons: Map<String, Int>) {
    val repeatedPokemonList = List(5) { pokemonList }.flatten()     // The flatten() function in Kotlin is used to convert a list of lists (a nested list) into a single list. It "flattens" the structure by removing the inner list boundaries and combining all the elements into one continuous list.
    LazyColumn {
        items(repeatedPokemonList) { pokemon ->
            val icon = icons[pokemon.name] ?: R.drawable.ivysaur_icon
            PokemonCard(pokemon = pokemon, icon = icon)
        }
    }
}

fun readCSV(context: Context): List<Pokemon> {
    val pokemonList = mutableListOf<Pokemon>()
    val inputStream = context.assets.open("pokemon_data.csv")
    val reader = BufferedReader(InputStreamReader(inputStream))

    reader.readLine()

    reader.useLines { lines ->
        lines.drop(1).forEach { line ->
            val tokens = line.split(",")
            val name = tokens[0]
            val type = tokens[1]
            val spAttack = tokens[2].toInt()
            val spDefense = tokens[3].toInt()

            pokemonList.add(Pokemon(name, type, spAttack, spDefense))
        }
    }
    return pokemonList
}

@Composable
fun PokemonCard(pokemon: Pokemon, icon: Int) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (pokemon.type) {
                "grass" -> GreenishCyan
                "fire" -> RedCoral
                else -> DenimBlue
            }
        ), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
       ConstraintLayout(modifier = Modifier
           .fillMaxWidth()
           .padding(16.dp)) {
           val (imageRef, nameRef, typeRef, attackRef, defenseRef) = createRefs()

           Box(modifier = Modifier
               .size(110.dp)
               .background(
                   color = when (pokemon.type) {
                       "grass" -> LightGreenishCyan
                       "fire" -> LightRedCoral
                       else -> LightDenimBlue
                   },
                   shape = CircleShape
               )
               .constrainAs(imageRef) {
                   top.linkTo(parent.top)
                   end.linkTo(parent.end)
               }
           ) {
               Image(
                   painter = painterResource(id = icon),
                   contentDescription = "${pokemon.name} icon",

                   modifier = Modifier
                       .padding(8.dp)
                       .size(110.dp)
                       .align(Alignment.Center)
               )
           }
           Text(
               text = pokemon.name,
               fontWeight = FontWeight.Bold,
               fontSize = 22.sp,
               color = Color.White,
               modifier = Modifier.constrainAs(nameRef) {
                   top.linkTo(parent.top, margin = 8.dp)
                   start.linkTo(parent.start, margin = 8.dp)
               }
               )
            Box(
                modifier = Modifier
                    .background(
                        when (pokemon.type) {
                            "grass" -> Pistachio
                            "fire" -> BrownishOrange
                            else -> SilkBlue
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .constrainAs(typeRef) {
                        top.linkTo(nameRef.bottom, margin = 8.dp)
                        start.linkTo(nameRef.start)
                    }
                    ) {
                Text(
                    text = pokemon.type,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                }
                Text(
                    text = "Attack: ${pokemon.spAttack}",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.constrainAs(attackRef) {
                        top.linkTo(nameRef.bottom, margin = 8.dp)
                        start.linkTo(typeRef.end, margin = 16.dp)
                    }
                )
                Text(
                    text = "Defense: ${pokemon.spDefense}",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.constrainAs(defenseRef) {
                        top.linkTo(attackRef.bottom, margin = 8.dp)
                        start.linkTo(typeRef.end, margin = 16.dp)
                    }

                )
            }
       }
    }

@Preview(showBackground = true)
@Composable
fun CardPreview() {
    val samplePokemon = Pokemon(name = "Venusaur", type = "fire", spAttack = 122, spDefense = 120)
    PokemonCard(pokemon = samplePokemon, icon = R.drawable.venusaur_icon)
}