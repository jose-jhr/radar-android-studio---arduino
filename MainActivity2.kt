package jhr.j.radar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ingenieria.jhr.bluetoothjhr.BluetoothJhr
import jhr.mylibrary2.RadarUltraJhr
import kotlinx.android.synthetic.main.activity_main2.*
import kotlin.concurrent.thread

class MainActivity2 : AppCompatActivity() {

    lateinit var blue:BluetoothJhr
    var initHilo = false
    var gradosGlobales = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        blue = BluetoothJhr(this,MainActivity::class.java)



        //distancia en metros
        radar.maxDistancia(1200f)
        //velocidad radar animacion
        radar.velAnimacion(40)
        //tamaño de las letras del mensaje (grados,distancia)
        radar.tamLetras(20f)
        //radio del objeto captado
        radar.radioObj(0.03f)
        //color circulo objeto captado
        radar.colorCircle(0,255,0)
        //color radar
        radar.colorRadar(0,255,0)
        //color linea radar
        radar.colorLinea(0,255,0)
        //el objeto es visible o no
        radar.obj1Visible(true)

        radar.initAnimation(true)

        enviarGrados.setOnClickListener {
            // en java -> String grados = editGrados.getText().toString
            var grados = editGrados.text.toString().toInt()
            gradosGlobales = grados
            blue.mTx("g"+grados)
        }


    }


    fun hilo(){
        if (initHilo){
            thread(start = true){
                println("----------------------------------")
                println("------------inicia hilo ----------")
                println("----------------------------------")
                Thread.sleep(1000)
                while (initHilo){
                    blue.mTx("d")
                    Thread.sleep(1000)
                    var distancia = blue.mRx() //2
                    if (distancia!="" && distancia.isNotEmpty() && initHilo){
                        runOnUiThread(Runnable {
                            radar.detecto(gradosGlobales,distancia = distancia.toFloat())
                            consola.text = distancia
                        })
                        blue.mensajeReset()
                    }
                }
                println("----------------------------------")
                println("------------fin hilo -------------")
                println("----------------------------------")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initHilo = blue.conectaBluetooth()
        hilo()
    }

    override fun onPause() {
        super.onPause()
        initHilo = false
        blue.exitConexion()
    }

    override fun onDestroy() {
        super.onDestroy()
        initHilo = false
        blue.exitConexion()
    }

}