package com.route.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.route.pokemonapp.model.Pokemon
import com.route.pokemonapp.model.PokemonType
import com.route.pokemonapp.ui.theme.PokemonAppTheme

class MainActivity : ComponentActivity() {
    private val pokemonList = mutableListOf<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPokemonListFromCSV()
        setContent {
            PokemonAppTheme {
                LazyColumn {
                    items(List(5) { pokemonList }.flatten()) {
                        PokemonCard(pokemon = it)
                    }
                }
            }
        }
    }

    // The flatten() function in Kotlin is used to convert a list of lists (a nested list) into a single list. It "flattens" the structure by removing the inner list boundaries and combining all the elements into one continuous list.

    private fun initPokemonListFromCSV() {
        assets.open("pokemon_data.csv").bufferedReader().use { reader ->
            reader.forEachLine { line ->
                val row = line.split(",")
                val pokemonType = when (row[1]) {
                    "grass" -> PokemonType.GRASS
                    "fire" -> PokemonType.FIRE
                    else -> PokemonType.WATER
                }
                pokemonList.add(
                    Pokemon(
                        row[0],
                        pokemonType,
                        row[2].toInt(),
                        row[3].toInt(),
                    )
                )
            }
        }
    }
}