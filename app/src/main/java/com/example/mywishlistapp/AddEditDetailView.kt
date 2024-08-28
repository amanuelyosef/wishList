package com.example.mywishlistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mywishlistapp.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AppEditDetailView(
    id:Long,
    viewModel: WishViewModel,
    navController: NavController
){
    val snackMessage= remember { mutableStateOf("") }
    val scope= rememberCoroutineScope()
    val scaffoldState= rememberScaffoldState()

    if(id!=0L){
        val wish=viewModel.getWishById(id).collectAsState(initial = Wish(0L,"",""))
        viewModel.wishTitleState=wish.value.title
        viewModel.wishDescriptionState=wish.value.description
    }else{
        viewModel.wishTitleState=""
        viewModel.wishDescriptionState=""
    }

    Scaffold(
        topBar = { AppBarView(title =
                if (id==0L) stringResource(id = R.string.add_wish)
                else stringResource(id = R.string.update_wish)
            ){ navController.navigateUp()}
        },
        scaffoldState=scaffoldState

    ) {
        Column(modifier = Modifier
            .padding(it)
            .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {

            Spacer(modifier = Modifier.height(10.dp))

            WishTextField(value = viewModel.wishTitleState, label = "Title", onValueChanged =  {
                viewModel.onWishTitleChanged(it)
            })

            WishTextField(value = viewModel.wishDescriptionState, label = "Description", onValueChanged =  {
                viewModel.onWishDescriptionChanged(it)
            })

            Spacer(modifier = Modifier.height(10.dp))

            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp), onClick = {
                    if(viewModel.wishTitleState.isNotEmpty() && viewModel.wishDescriptionState.isNotEmpty()){
                        if(id==0L){
                            // ADD
                            viewModel.addWish(
                                Wish(
                                    title = viewModel.wishTitleState.trim(),
                                    description = viewModel.wishDescriptionState.trim()
                                )
                            )
                            snackMessage.value="Wish has been created"
                        }else{
                            // TODO Update
                        }
                    }else{
                        snackMessage.value="Enter value to the fields"
                    }
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                    navController.navigateUp()
                }
                }) {
                Text(text = if (id==0L) stringResource(id = R.string.add_wish)
                else stringResource(id = R.string.update_wish), fontWeight=FontWeight.ExtraBold
                )
            }

        }
    }
}

@Composable
fun WishTextField(
    value:String,
    label:String,
    onValueChanged:(String)->Unit
){

    OutlinedTextField(value = value, 
        onValueChange = {onValueChanged(it)},
        label= {Text(text = label, color= Color.Black)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors= TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,

            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            cursorColor = Color.Black
        )
    )
}

@Preview(showBackground = true)
@Composable
fun WishTextFieldPrev(){
    WishTextField(value = "text", label = "text") {}
}

