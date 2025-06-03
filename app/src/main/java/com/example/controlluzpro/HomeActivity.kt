package com.example.controlluzpro

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database

class HomeActivity : ComponentActivity() {
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Firebase.database
        dbRef = FirebaseDatabase.getInstance().getReference("luz")

        setContent {
            MaterialTheme {
                HomeScreen(dbRef = dbRef)
            }
        }
    }
}

@Composable
fun HomeScreen(dbRef: DatabaseReference) {
    val user = FirebaseAuth.getInstance().currentUser
    val context = LocalContext.current

    var luzEstado by remember { mutableStateOf("apagada") }
    var lastChangedBy by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }

            LaunchedEffect(Unit) {
                dbRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Log.d("HomeScreen", "onDataChange triggered: " + snapshot.value)
                        luzEstado = snapshot.child("estado").getValue(String::class.java) ?: "apagada"
                        lastChangedBy = snapshot.child("lastChangedBy").getValue(String::class.java) ?: ""
                        loading = false
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.e("HomeScreen", "onCancelled: ${error.message}")
                        loading = false
                    }
                })
            }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("¡Bienvenido!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Usuario: ${user?.email ?: "Desconocido"}")
        Spacer(modifier = Modifier.height(24.dp))

        if (luzEstado == "encendida") {
            Icon(
                painter = painterResource(id = R.drawable.ic_bulb_on),
                contentDescription = "Luz encendida",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(100.dp)
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.ic_bulb_off),
                contentDescription = "Luz apagada",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(100.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "La luz está: ${luzEstado.uppercase()}",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (lastChangedBy.isNotEmpty()) {
            Text("Último cambio por: $lastChangedBy", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (!loading) {
            Button(onClick = {
                val nuevoEstado = if (luzEstado == "encendida") "apagada" else "encendida"
                dbRef.setValue(
                    mapOf(
                        "estado" to nuevoEstado,
                        "lastChangedBy" to (user?.email ?: "Desconocido")
                    )
                )
            }) {
                Text(if (luzEstado == "encendida") "Apagar luz" else "Encender luz")
            }
        } else {
            CircularProgressIndicator()
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            FirebaseAuth.getInstance().signOut()
            context.startActivity(Intent(context, MainActivity::class.java))
            if (context is android.app.Activity)
                context.finish()
        }) {
            Text("Cerrar sesión")
        }
    }
}


