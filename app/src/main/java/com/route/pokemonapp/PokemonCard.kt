package com.route.pokemonapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.route.pokemonapp.model.Pokemon
import com.route.pokemonapp.model.PokemonType

@Composable
fun PokemonCard(pokemon: Pokemon, icon: Int = pokemon.iconResId) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = pokemon.type.backgroundColor
        ), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (pokemonImage, pokemonName, pokemonType, pokemonAttack, pokemonDefense) = createRefs()

            Image(
                painter = painterResource(id = icon),
                contentDescription = "${pokemon.name} icon",
                modifier = Modifier
                    .background(color = Color.White.copy(.3f), CircleShape)
                    .padding(8.dp)
                    .size(110.dp)
                    .constrainAs(pokemonImage) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
            )

            Text(
                text = pokemon.name,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.White,
                modifier = Modifier.constrainAs(pokemonName) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                }
            )
            Box(
                modifier = Modifier
                    .background(
                        pokemon.type.badgeColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .constrainAs(pokemonType) {
                        top.linkTo(pokemonName.bottom, margin = 12.dp)
                        start.linkTo(pokemonName.start)
                        bottom.linkTo(pokemonDefense.bottom)
                    }
            ) {
                Text(
                    text = pokemon.type.displayName,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = stringResource(R.string.attack) + pokemon.spAttack,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(pokemonAttack) {
                    top.linkTo(pokemonName.bottom, margin = 12.dp)
                    start.linkTo(pokemonType.end, margin = 16.dp)
                }
            )
            Text(
                text = stringResource(R.string.defense) + pokemon.spDefense,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(pokemonDefense) {
                    top.linkTo(pokemonAttack.bottom, margin = 8.dp)
                    start.linkTo(pokemonType.end, margin = 16.dp)
                }

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PokemonCardPreview() {
    val samplePokemon =
        Pokemon(name = "Venusaur", type = PokemonType.FIRE, spAttack = 122, spDefense = 120)
    PokemonCard(pokemon = samplePokemon, icon = R.drawable.venusaur_icon)
}