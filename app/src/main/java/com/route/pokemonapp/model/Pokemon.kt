package com.route.pokemonapp.model

import androidx.compose.ui.graphics.Color
import com.route.pokemonapp.R
import com.route.pokemonapp.ui.theme.BrownishOrange
import com.route.pokemonapp.ui.theme.DenimBlue
import com.route.pokemonapp.ui.theme.GreenishCyan
import com.route.pokemonapp.ui.theme.Pistachio
import com.route.pokemonapp.ui.theme.RedCoral
import com.route.pokemonapp.ui.theme.SilkBlue

data class Pokemon(
    val name: String,
    val type: PokemonType,
    val spAttack: Int,
    val spDefense: Int,
    val iconResId: Int = icons[name] ?: R.drawable.ivysaur_icon
)

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

enum class PokemonType(val displayName: String, val badgeColor: Color, val backgroundColor: Color) {
    GRASS("grass", Pistachio, GreenishCyan),
    FIRE("fire", BrownishOrange, RedCoral),
    WATER("water", SilkBlue, DenimBlue),
}
