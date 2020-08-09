package jhr.j.radar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ingenieria.jhr.bluetoothjhr.BluetoothJhr
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val blue = BluetoothJhr(this,dispositivosList,MainActivity2::class.java)

        blue.onBluetooth()

        dispositivosList.setOnItemClickListener { adapterView, view, i, l ->
            blue.bluetoothSeleccion(i)
        }


    }
}