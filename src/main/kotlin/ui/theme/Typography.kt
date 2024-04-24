package ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp

val montserrat = FontFamily(
    Font(resource = "fonts/montserrat_black.ttf", FontWeight.Black),
    Font(resource = "fonts/montserrat_blackitalic.ttf", FontWeight.Black, FontStyle.Italic),
    Font(resource = "fonts/montserrat_bold.ttf", FontWeight.Bold),
    Font(resource = "fonts/montserrat_bolditalic.ttf", FontWeight.Bold, FontStyle.Italic),
    Font(resource = "fonts/montserrat_extrabold.ttf", FontWeight.ExtraBold),
    Font(resource = "fonts/montserrat_extrabolditalic.ttf", FontWeight.ExtraBold, FontStyle.Italic),
    Font(resource = "fonts/montserrat_extralight.ttf", FontWeight.ExtraLight),
    Font(resource = "fonts/montserrat_extralightitalic.ttf", FontWeight.ExtraLight, FontStyle.Italic),
    Font(resource = "fonts/montserrat_italic.ttf", FontWeight.Normal, FontStyle.Italic),
    Font(resource = "fonts/montserrat_light.ttf", FontWeight.Light),
    Font(resource = "fonts/montserrat_lightitalic.ttf", FontWeight.Light, FontStyle.Italic),
    Font(resource = "fonts/montserrat_medium.ttf", FontWeight.Medium),
    Font(resource = "fonts/montserrat_mediumitalic.ttf", FontWeight.Medium, FontStyle.Italic),
    Font(resource = "fonts/montserrat_regular.ttf", FontWeight.Normal),
    Font(resource = "fonts/montserrat_semibold.ttf", FontWeight.SemiBold),
    Font(resource = "fonts/montserrat_semibolditalic.ttf", FontWeight.SemiBold, FontStyle.Italic),
    Font(resource = "fonts/montserrat_thin.ttf", FontWeight.Thin),
    Font(resource = "fonts/montserrat_thinitalic.ttf", FontWeight.Thin, FontStyle.Italic)
)

val typography = Typography(
    body1 = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 25.sp
    ),
    caption = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    h4 = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp
    ),
    h1 = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 50.sp
    ),
    h6 = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp
    ),
    body2 = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)