package com.example.knowledge.compose.jetreaderapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.knowledge.compose.jetreaderapp.model.MBook
import com.example.knowledge.compose.jetreaderapp.navigation.ReaderScreens
import com.example.knowledge.compose.jetreaderapp.screens.home.RoundedButton
import com.example.knowledge.compose.theme.ColorPrimary
import com.example.knowledge.compose.theme.ColorPrimaryDark
import com.example.knowledge.compose.theme.ColorSecondary
import com.example.knowledge.compose.theme.ColorSecondaryLight
import com.example.knowledge.compose.theme.Typography
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ReaderLogo(modifier: Modifier = Modifier) {
    Text(
        text = "A. Reader",
        modifier = modifier.padding(bottom = 16.dp),
        style = Typography.h3,
        color = ColorSecondary
    )
}

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    labelId: String = "Email",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        modifier = modifier,
        valueState = emailState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction
    )
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = labelId) },
        singleLine = isSingleLine,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = ColorSecondaryLight
        ),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = ColorSecondaryLight
        ),
        keyboardActions = onAction
    )
}

@Composable
fun PasswordInput(
    modifier: Modifier,
    passwordState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    passwordVisibility: MutableState<Boolean>,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    val visualTransformation =
        if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {
            passwordState.value = it
        },
        label = {
            Text(text = labelId)
        },
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = ColorSecondaryLight
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(
                bottom = 10.dp,
                start = 10.dp,
                end = 10.dp

            ),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        visualTransformation = visualTransformation,
        trailingIcon = { PasswordVisibility(passwordVisibility) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = ColorSecondaryLight
        ),
        keyboardActions = onAction
    )
}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    IconButton(onClick = {
        passwordVisibility.value = !visible
    }) {
        Icon(
            imageVector = if (visible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
            contentDescription = "Visibility",
            tint = ColorSecondaryLight
        )
    }
}

@Composable
fun ReaderAppBar(
    title: String,
    icon: ImageVector? = null,
    showProfile: Boolean = true,
    navController: NavController,
    onBackArrowClicked: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showProfile) {
                    Image(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Loco Icon",
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .scale(0.9f),
                        colorFilter = ColorFilter.tint(ColorSecondary)
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                }
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Arrow back",
                        tint = ColorSecondary.copy(alpha = 0.7f),
                        modifier = Modifier.clickable {
                            onBackArrowClicked.invoke()
                        }
                    )
                }
                Spacer(modifier = Modifier.width(40.dp))
                Text(
                    text = title,
                    color = ColorSecondary.copy(alpha = 0.7f),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    FirebaseAuth.getInstance().signOut().run {
                        navController.navigate(ReaderScreens.LoginScreen.name)
                    }
                }
            ) {
                if (showProfile) {
                    Row {
                        Icon(
                            imageVector = Icons.Filled.Logout,
                            contentDescription = "Logout",
                            tint = ColorSecondary
                        )
                    }
                } else {
                    Box(modifier = Modifier)
                }
            }
        },
        backgroundColor = ColorPrimary,
        elevation = 0.dp
    )
}

@Composable
fun TitleSection(modifier: Modifier = Modifier, label: String) {
    Surface(modifier = modifier.padding(start = 5.dp, top = 1.dp)) {
        Column {
            Text(
                text = label,
                fontSize = 19.sp,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Left,
                color = ColorSecondaryLight
            )
        }
    }
}

@Composable
fun FABContent(onTap: () -> Unit) {
    FloatingActionButton(
        onClick = { onTap() },
        shape = RoundedCornerShape(50.dp),
        backgroundColor = ColorPrimary,
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add a Book",
            tint = ColorSecondary
        )
    }
}

@Composable
fun BookRating(score: Double = 4.5) {
    Surface(
        modifier = Modifier
            .height(80.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(56.dp),
        color = ColorPrimaryDark,
        elevation = 6.dp
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.StarBorder, contentDescription = "Star",
                modifier = Modifier.padding(3.dp),
                tint = ColorSecondary
            )
            Text(
                text = score.toString(),
                style = Typography.subtitle1,
                modifier = Modifier.padding(3.dp)
            )
        }
    }
}

@Preview
@Composable
fun ListCard(
    book: MBook = MBook(
        id = "123",
        title = "Running",
        authors = "Me and you",
        notes = "Hello Wolrd"
    ), onPressDetails: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val screenWith = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 10.dp

    Card(shape = RoundedCornerShape(29.dp),
        elevation = 6.dp,
        modifier = Modifier
            .padding(16.dp)
            .height(242.dp)
            .width(202.dp)
            .clickable {
                onPressDetails(book.title.toString())
            }
    ) {
        Column(
            modifier = Modifier
                .width(screenWith.dp - (spacing * 2))
                .background(ColorSecondaryLight),
            horizontalAlignment = Alignment.Start
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = rememberImagePainter(data = "https://images.unsplash.com/photo-1541963463532-d68292c34b19?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=80&q=80"),
                    contentDescription = "Book Image",
                    modifier = Modifier
                        .height(140.dp)
                        .width(100.dp)
                        .padding(vertical = 8.dp, horizontal = 4.dp),
                )
                Spacer(modifier = Modifier.width(50.dp))
                Column(
                    modifier = Modifier.padding(top = 25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "Fav Icon",
                        modifier = Modifier.padding(bottom = 1.dp)
                    )
                    BookRating(score = 3.5)
                }
            }
            Text(
                text = book.title.toString(),
                modifier = Modifier.padding(4.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = ColorPrimaryDark
            )
            Text(
                text = book.authors.toString(),
                modifier = Modifier.padding(4.dp),
                style = Typography.caption,
                color = ColorPrimaryDark
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            RoundedButton(label = "Reading", radius = 70)

        }
    }
}