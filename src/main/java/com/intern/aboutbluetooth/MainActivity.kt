package com.intern.aboutbluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object{
        const val REQUEST_ENABLE_BTN=0
        const val REQUEST_DISCOVERED_BTN=1
    }
    private val mBlueAdapter by lazy { BluetoothAdapter.getDefaultAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(mBlueAdapter==null){
            tv_bluetooth.text=getString(R.string.not_available)
        }else tv_bluetooth.text=getString(R.string.available)
        if(mBlueAdapter.isEnabled){
            iv_bluetooth.setImageResource(R.drawable.ic_bluetooth_on)
        }else iv_bluetooth.setImageResource(R.drawable.ic_bluetooth_off)
        btn_turn_on.setOnClickListener {
            if(!mBlueAdapter.isEnabled){
                showToast("turning on bluetooth")
                val intent=Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, REQUEST_ENABLE_BTN)
            }else{
                showToast("Bluetooth is already on")
            }
        }
        btn_turn_off.setOnClickListener {
            if(mBlueAdapter.isEnabled){
                mBlueAdapter.disable()
                showToast("turning Bluetooth off")
                iv_bluetooth.setImageResource(R.drawable.ic_bluetooth_off)
            }else{
                showToast("Bluetooth is already off")
            }
        }
        btn_discoverable.setOnClickListener {
            if(!mBlueAdapter.isDiscovering){
                showToast("making your device discoverable")
                val intent=Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
                startActivityForResult(intent, REQUEST_DISCOVERED_BTN)
            }
        }
        btn_get_paired.setOnClickListener {
                if(mBlueAdapter.isEnabled){
                    tv_pair.text=getString(R.string.paired_device)
                    val devices:Set<BluetoothDevice> = mBlueAdapter.bondedDevices
                    for(i in devices){
                        tv_pair.append("\nDevice :"+i.name+" "+i)
                    }
                }
            else showToast("turn on bluetooth to get paired device")
        }
    }
    private fun showToast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            REQUEST_ENABLE_BTN->{
                if(resultCode== RESULT_OK){
                    iv_bluetooth.setImageResource(R.drawable.ic_bluetooth_on)
                    showToast("Bluetooth on")
                }else showToast("couldn't on Bluetooth")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}