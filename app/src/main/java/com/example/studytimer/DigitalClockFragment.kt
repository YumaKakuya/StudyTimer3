package com.example.studytimer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class DigitalClockFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_digital_clock, container, false)

        // タイマー時間選択ボタンの処理
        view.findViewById<Button>(R.id.btnTimer5).setOnClickListener {
            Toast.makeText(context, "5分タイマーを選択", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<Button>(R.id.btnTimer10).setOnClickListener {
            Toast.makeText(context, "10分タイマーを選択", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<Button>(R.id.btnTimer15).setOnClickListener {
            Toast.makeText(context, "15分タイマーを選択", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<Button>(R.id.btnTimer25).setOnClickListener {
            Toast.makeText(context, "25分タイマーを選択", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<Button>(R.id.btnTimer50).setOnClickListener {
            Toast.makeText(context, "50分タイマーを選択", Toast.LENGTH_SHORT).show()
        }

        // タイマー制御ボタンの処理
        view.findViewById<Button>(R.id.btnStart).setOnClickListener {
            Toast.makeText(context, "タイマー開始", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<Button>(R.id.btnStop).setOnClickListener {
            Toast.makeText(context, "タイマー停止", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<Button>(R.id.btnReset).setOnClickListener {
            Toast.makeText(context, "タイマーリセット", Toast.LENGTH_SHORT).show()
        }
        
        return view
    }
}