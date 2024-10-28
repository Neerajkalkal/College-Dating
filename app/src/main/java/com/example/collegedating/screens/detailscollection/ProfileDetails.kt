package com.example.collegedating.screens.detailscollection

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.collegedating.R
import com.example.collegedating.model.CollegeList.CollegeList
import com.example.collegedating.model.CollegeList.CollegeListItem
import com.example.collegedating.model.DataOrException
import com.example.collegedating.model.adduser.BasicProfileDetails
import com.example.collegedating.navigation.Screen
import com.example.collegedating.networkrepository.NetworkCalls.bitmapToByteArray
import com.example.collegedating.screens.CustomButton
import com.example.collegedating.screens.loadingscreen.LoadingScreen
import com.example.collegedating.tokenmanagement.TokenManagement
import com.example.collegedating.tokenmanagement.TokenOperation
import com.example.collegedating.ui.theme.primary
import com.example.collegedating.viewmodel.MainViewModel
import com.yalantis.ucrop.UCrop
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("UnrememberedMutableInteractionSource")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDetails(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    tokenManagement: TokenManagement
) {
    val localConfig = LocalConfiguration.current
    val screenHeight = localConfig.screenHeightDp
    val screenWidth = localConfig.screenWidthDp

    val firstname = remember {
        mutableStateOf("")
    }

    val loading = remember { mutableStateOf(false) }

    // error
    val error = remember { mutableStateOf("") }

    // state
    val state = mainViewModel.basicUserDetailsState.collectAsState()
    state.value.data?.let {
        if (it == "0") {
            TokenOperation.saveSteps(
                tokenManagement, step = 2
            )
            navController.navigate(Screen.GenderSelection.name)
        } else {
            loading.value = false
            error.value = it
        }
    }

    state.value.e?.let {
        loading.value  = false
        error.value = it.message.toString()
    }


    val lastname = remember {
        mutableStateOf("")
    }

    val collegeName = remember {
        mutableStateOf("Select The Institution")
    }

    val selectedCollege = remember {
        mutableStateOf<CollegeListItem?>(
            null
        )
    }

    val showDropDown = remember {
        mutableStateOf(false)
    }

    val datePickerState = remember {

        DatePickerState(locale = Locale.ENGLISH)
    }


    val focusRequester = FocusRequester()
    val focusRequester1 = FocusRequester()

    var croppedImage by remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current

    val isVisible = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { isVisible.value = true }

    val offset by animateDpAsState(
        targetValue = if (isVisible.value) 0.dp else (-300).dp,
        animationSpec = tween(durationMillis = 300),
        label = ""
    )

    val uCropLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                val uri = UCrop.getOutput(intent)
                uri?.let { croppedUri ->
                    context.contentResolver.openInputStream(croppedUri)?.let { inputStream ->
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        croppedImage = bitmap
                    }
                }
            }
        }
    }
    // Launcher to pick an image
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Create UCrop intent to crop the image
            val destinationUri = Uri.fromFile(File(context.cacheDir, "croppedImage.jpg"))
            val uCropIntent =
                UCrop.of(it, destinationUri).withAspectRatio(1f, 1f).withMaxResultSize(450, 450)
                    .getIntent(context)

            // Launch UCrop using the uCropLauncher
            uCropLauncher.launch(uCropIntent)
        }
    }


    val showPickerState = remember {
        mutableStateOf(false)
    }

    // state of data
    val stateOfCollegeList = mainViewModel.getCollegeList.collectAsState()





    Scaffold { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .offset(x = offset)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = (screenWidth / 10).dp, end = (screenWidth / 10).dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height((screenHeight / 32).dp))

                Text(
                    text = "Profile Details",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height((screenHeight / 15).dp))

                Box(modifier = Modifier
                    .size(if (croppedImage == null) 95.dp else 115.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable { imagePickerLauncher.launch("image/*") }) {
                    if (croppedImage != null) {
                        Image(
                            painter = rememberImagePainter(data = croppedImage, builder = {
                                crossfade(true)
                            }),
                            contentDescription = null,
                            modifier = Modifier
                                .size(110.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )
                    } else {

                        Image(
                            painter = painterResource(id = R.drawable.user_con),
                            contentDescription = null,
                            modifier = Modifier
                                .size(85.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )

                    }
                    Image(
                        painter = painterResource(id = R.drawable.camera),
                        contentDescription = "Add profile picture",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height((screenHeight / 18).dp))

                if (error.value.isNotEmpty()) {
                    Text(
                        error.value,
                        color = Color.Red,
                        modifier = Modifier.padding((screenHeight / 88).dp)
                    )
                }

                CustomOutlineFields(
                    state = firstname,
                    label = "First Name",
                    focusRequester = focusRequester,
                    imeAction = ImeAction.Next
                ) {}

                Spacer(modifier = Modifier.height((screenHeight / 58).dp))
                CustomOutlineFields(
                    state = lastname,
                    label = "Last Name",
                    focusRequester = focusRequester1,
                    imeAction = ImeAction.Next
                ) {
                    showPickerState.value = true
                }

                Spacer(modifier = Modifier.height((screenHeight / 58).dp))


                //dialog button
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { showDropDown.value = true },
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(width = 1.dp, color = Color.LightGray)
                ) {
                    Text(
                        text = collegeName.value,
                        modifier = Modifier.padding(16.dp)
                    )
                }


                if (showDropDown.value) {
                    CustomDropDown(
                        selectedCollege,
                        collegeName,
                        showDropDown,
                        stateOfCollegeList,
                        mainViewModel
                    )
                }


                Spacer(modifier = Modifier.height((screenHeight / 38).dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                showPickerState.value = true
                            },
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        )
                        .padding(8.dp),
                    color = primary.copy(alpha = 0.06f),
                    shape = RoundedCornerShape(20.dp),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange, contentDescription = "",
                            tint = primary
                        )
                        val formattedDate = if (datePickerState.selectedDateMillis != null) {
                            val date = Date(datePickerState.selectedDateMillis!!)
                            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            dateFormat.format(date)
                        } else {
                            "Choose birthday date"
                        }
                        Text(
                            text = formattedDate,
                            modifier = Modifier.padding(15.dp),
                            color = primary
                        )

                    }
                }




                Spacer(modifier = Modifier.height((screenHeight / 10).dp))

                if(!loading.value){
                CustomButton(
                    color = primary,
                    textColor = Color.White,
                    roundedCornerShape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth(),
                    text = "confirm",
                    textModifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                ) {
                    if (firstname.value.length > 1) {
                        if (selectedCollege.value != null) {
                            if (datePickerState.selectedDateMillis != null) {
                                val basicProfileDetails =
                                    croppedImage?.let { bitmapToByteArray(it) }?.let {
                                        BasicProfileDetails(
                                            image = it,
                                            firstName = firstname.value,
                                            lastName = lastname.value,
                                            collegeId = selectedCollege.value!!.id,
                                            collegeName = selectedCollege.value!!.collegeName,
                                            dob = datePickerState.selectedDateMillis.toString()
                                        )
                                    }
                                if (basicProfileDetails != null) {
                                    loading.value = true
                                    mainViewModel.sendBasicUserDetails(
                                        basicProfileDetails = basicProfileDetails
                                    )
                                }
                            } else {
                                mainViewModel.reset()
                                error.value = "Please fill your D.O.B to proceed"
                            }
                        } else {
                            mainViewModel.reset()
                            error.value = "Select Your Institution to proceed"
                        }

                    } else {

                        mainViewModel.reset()
                        error.value = "First name length should be greater than 1"
                    }
                }

            }else{
                LoadingScreen(Modifier.align(Alignment.CenterHorizontally))
            }



                if (showPickerState.value) {
                    CustomDatePicker(
                        datePickerState = datePickerState,
                        showPickerState
                    )
                }


            }
        }
    }
}

@Composable
fun CustomDropDown(
    selectedCollege: MutableState<CollegeListItem?>,
    collegeName: MutableState<String>,
    showDropDown: MutableState<Boolean>,
    stateOfCollegeList: State<DataOrException<CollegeList, Exception>>,
    mainViewModel: MainViewModel
) {
    val search = remember {
        mutableStateOf("")
    }

    Dialog(onDismissRequest = { showDropDown.value = false }) {


        Surface(shape = RoundedCornerShape(10.dp)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)
            ) {

                Text(text = "Search For College")

                OutlinedTextField(
                    value = search.value,
                    onValueChange = {
                        search.value = it

                        if (search.value.length > 1) {
                            mainViewModel.fetchCollegeList(search.value)
                        }
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    label = { Text(text = "College Name") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.LightGray,
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedLabelColor = Color.LightGray,
                        focusedLabelColor = Color.LightGray,
                        cursorColor = Color.Black
                    ), shape = RoundedCornerShape(20.dp),
                    maxLines = 1,
                    singleLine = true,

                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onAny = { }
                    ))


                val list = stateOfCollegeList.value.data ?: emptyList()
                if (stateOfCollegeList.value.loading) {
                    LoadingScreen(modifier = Modifier.padding(top = 50.dp, bottom = 50.dp))
                } else
                    LazyColumn(modifier = Modifier.heightIn(max = 400.dp)) {
                        items(list) {
                            Surface(
                                modifier = Modifier
                                    .clickable {
                                        selectedCollege.value = it
                                        collegeName.value = it.collegeName
                                        showDropDown.value = false
                                        mainViewModel.reset()
                                    }
                                    .fillMaxWidth()
                                    .padding(5.dp), border = BorderStroke(
                                    color = Color.LightGray, width = 1.dp,
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = it.collegeName,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }


            }


        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(datePickerState: DatePickerState, showPickerState: MutableState<Boolean>) {
    ModalBottomSheet(onDismissRequest = {
        showPickerState.value = false
    }) {
        DatePicker(
            state = datePickerState,
            title = {

            },
            headline = {
                Text(
                    text = " Select Birthday Date ",
                    modifier = Modifier.padding(start = 20.dp, bottom = 40.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    color = primary
                )
            },
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                containerColor = Color.White,
                yearContentColor = primary,
                selectedYearContentColor = Color.White,
                selectedYearContainerColor = primary,
                todayDateBorderColor = primary,
                selectedDayContentColor = Color.White,
                selectedDayContainerColor = primary,
                weekdayContentColor = primary,
            )

        )
    }
}

@Composable
fun CustomOutlineFields(
    state: MutableState<String>,
    label: String,
    focusRequester: FocusRequester,
    imeAction: ImeAction,
    onClick: () -> Unit
) {
    OutlinedTextField(
        value = state.value,
        onValueChange = { state.value = it },
        label = { Text(text = label) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.LightGray,
            focusedIndicatorColor = Color.LightGray,
            unfocusedContainerColor = Color.Transparent,
            unfocusedLabelColor = Color.LightGray,
            focusedLabelColor = Color.LightGray,
            cursorColor = Color.Black
        ), shape = RoundedCornerShape(20.dp),
        maxLines = 1,
        singleLine = true,
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onAny = { onClick() }
        ))

}
