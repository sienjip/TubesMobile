package com.e.tubesmobile.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.e.tubesmobile.model.JenisKomputer
import com.e.tubesmobile.ui.theme.Purple700
import com.e.tubesmobile.ui.theme.Teal200
import kotlinx.coroutines.launch

@Composable
fun FormPencatatanKomputer(navController : NavHostController, id: String? = null, modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<PengelolaanKomputerViewModel>()
    val merk = remember { mutableStateOf(TextFieldValue("")) }
    val jenis = remember { mutableStateOf(JenisKomputer.Laptop) }
    val harga = remember { mutableStateOf(TextFieldValue("")) }
    val dapatDiUpgrade = remember { mutableStateOf(false) }
    val spesifikasi = remember { mutableStateOf(TextFieldValue("")) }
    val isLoading = remember { mutableStateOf(false) }
    val buttonLabel = if (isLoading.value) "Mohon tunggu..." else "Simpan"
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()) {
        OutlinedTextField(
            label = { Text(text = "Merk") },
            value = merk.value,
            onValueChange = {
                merk.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            placeholder = { Text(text = "Merk Komputer") }
        )

        OutlinedTextField(
            label = { Text(text = "Jenis") },
            value = jenis.value.toString(),
            onValueChange = {},
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            enabled = false
        )

        var expanded by remember { mutableStateOf(false) }
        val items = JenisKomputer.values()

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        jenis.value = item
                        expanded = false
                    }
                ) {
                    Text(text = item.toString())
                }
            }
        }

        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Open Dropdown")
        }

        OutlinedTextField(
            label = { Text(text = "Harga Komputer") },
            value = harga.value,
            onValueChange = {
                harga.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { Text(text = "Harga Komputer") }
        )

        Row(modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()) {
            Checkbox(
                checked = dapatDiUpgrade.value,
                onCheckedChange = {
                    dapatDiUpgrade.value = it
                },
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = "Dapat Diupgrade")
        }

        OutlinedTextField(
            label = { Text(text = "Spesifikasi") },
            value = spesifikasi.value,
            onValueChange = {
                spesifikasi.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            placeholder = { Text(text = "Spesifikasi Komputer") }
        )

        val loginButtonColors = ButtonDefaults.buttonColors(
            backgroundColor = Purple700,
            contentColor = Teal200
        )
        val resetButtonColors = ButtonDefaults.buttonColors(
            backgroundColor = Teal200,
            contentColor = Purple700
        )

        Row(modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()) {
            Button(
                modifier = Modifier.weight(5f),
                onClick = {
                    if (id == null){
                        scope.launch {
                            viewModel.insert(
                                merk = merk.value.text,
                                jenis = jenis.value,
                                harga = harga.value.text.toIntOrNull() ?: 0,
                                dapatDiUpgrade.value,
                                spesifikasi = spesifikasi.value.text)
                        }
                    } else {
                        scope.launch {
                            viewModel.update(
                                id,
                                merk = merk.value.text,
                                jenis = jenis.value,
                                harga = harga.value.text.toIntOrNull() ?: 0,
                                dapatDiUpgrade = dapatDiUpgrade.value,
                                spesifikasi = spesifikasi.value.text
                            )
                        }
                    }
                    navController.navigate("pengelolaan-komputer")
                },
                colors = loginButtonColors
            ) {
                Text(
                    text = buttonLabel,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            }

            Button(
                modifier = Modifier.weight(5f),
                onClick = {
                    merk.value = TextFieldValue("")
                    jenis.value = JenisKomputer.Laptop
                    harga.value = TextFieldValue("")
                    dapatDiUpgrade.value = false
                    spesifikasi.value = TextFieldValue("")
                },
                colors = resetButtonColors
            ) {
                Text(
                    text = "Reset",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
    viewModel.isLoading.observe(LocalLifecycleOwner.current) {
        isLoading.value = it
    }

    if (id != null) {
        LaunchedEffect(key1 = id) {
            viewModel.loadItem(id) { komputer ->
                komputer?.let {
                    merk.value = TextFieldValue(komputer.merk)
                    jenis.value = JenisKomputer.valueOf(komputer.jenis)
                    harga.value = TextFieldValue(komputer.harga.toString())
                    dapatDiUpgrade.value = komputer.dapatDiupgarade
                    spesifikasi.value = TextFieldValue(komputer.spesifikasi)
                }
            }
        }
    }

}